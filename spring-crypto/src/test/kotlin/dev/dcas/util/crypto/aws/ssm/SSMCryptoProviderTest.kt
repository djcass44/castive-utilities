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

import com.amazonaws.http.SdkHttpMetadata
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult
import com.amazonaws.services.simplesystemsmanagement.model.Parameter
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import kotlin.test.assertEquals

class SSMCryptoProviderTest {
	private val client = mock(AWSSimpleSystemsManagement::class.java)

	@Test
	fun `test validating a valid parameter`() {
		val metadata = mock(SdkHttpMetadata::class.java)
		Mockito.`when`(metadata.httpStatusCode).thenReturn(200)

		val provider = ParameterStoreOneValueCrypto(client, "foo")
		assertEquals("password", provider.validateParameter(
			GetParameterResult().withParameter(
				Parameter().withValue("password")
			).apply {
				sdkHttpMetadata = metadata
			}
		))
	}
	@Test
	fun `test validating a bad request from a parameter`() {
		val metadata = mock(SdkHttpMetadata::class.java)
		Mockito.`when`(metadata.httpStatusCode).thenReturn(400)

		val provider = ParameterStoreOneValueCrypto(client, "foo")
		assertEquals(null, provider.validateParameter(
			GetParameterResult().withParameter(
				Parameter().withValue("password")
			).apply {
				sdkHttpMetadata = metadata
			}
		))
	}
	@Test
	fun `test validating an invalid parameter`() {
		val metadata = mock(SdkHttpMetadata::class.java)
		Mockito.`when`(metadata.httpStatusCode).thenReturn(200)

		val param = mock(GetParameterResult::class.java)
		Mockito.`when`(param.parameter).thenReturn(null)

		val provider = ParameterStoreOneValueCrypto(client, "foo")
		assertEquals(null, provider.validateParameter(
			param.apply {
				sdkHttpMetadata = metadata
			}.withParameter(
				Parameter().withValue("password")
			)
		))
	}
}