/*
 *    Copyright 2020 Django Cass
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package dev.dcas.util.crypto.aws.ssm

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult
import com.amazonaws.services.simplesystemsmanagement.model.ParameterType
import com.amazonaws.services.simplesystemsmanagement.model.PutParameterRequest
import dev.castive.log2.loge
import dev.castive.log2.logok
import dev.castive.log2.logv
import dev.dcas.util.crypto.jdk.JavaMultiValueCrypto

abstract class AbstractSSMCryptoProvider(
	private val client: AWSSimpleSystemsManagement = AWSSimpleSystemsManagementClientBuilder.defaultClient()
) {

	/**
	 * Use to generate a random value for storage in AWS
	 */
	private val keyGen = JavaMultiValueCrypto()

	/**
	 * Attempt to load a value from AWS ParameterStore
	 */
	fun getParameter(name: String, throwIfNotFound: Boolean = true): String {
		val param = runCatching {
			client.getParameter(GetParameterRequest().withName(name).withWithDecryption(true))
		}.onFailure {
			"Failed to get parameter".loge(javaClass, it)
		}.getOrNull()
		val keyResult = validateParameter(param) ?: run {
			if(throwIfNotFound)
				error("Failed to get parameter with name: $name")
			else
				return@run createKey(name)
		}
		"We appear to have successfully retrieved the encryption key".logok(javaClass)
		return keyResult
	}

	fun validateParameter(param: GetParameterResult?): String? {
		if(param == null) {
			"Returned parameter is null".logv(javaClass)
			return null
		}
		if(param.sdkHttpMetadata.httpStatusCode != 200) {
			"GetParameter returned non-OK status code (${param.sdkHttpMetadata.httpStatusCode})".loge(javaClass)
			return null
		}
		return kotlin.runCatching {
			param.parameter.value
		}.onFailure {
			"Failed to load parameter".loge(javaClass, it)
		}.getOrNull()
	}

	/**
	 * Generate a key and attempt to store it in AWS ParameterStore
	 */
	private fun createKey(name: String): String {
		"Generating content for new parameter: $name".logv(javaClass)
		val key = keyGen.get()
		val r = client.putParameter(PutParameterRequest().withName(name).withType(ParameterType.SecureString).withValue(key))
		if(r.sdkHttpMetadata.httpStatusCode != 200) {
			"Failed to write parameter, this may cause issues".loge(javaClass)
		}
		return key
	}
}