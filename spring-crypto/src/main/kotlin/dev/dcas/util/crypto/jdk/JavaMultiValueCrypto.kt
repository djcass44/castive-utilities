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

package dev.dcas.util.crypto.jdk

import dev.dcas.util.crypto.MultiValueCryptoProvider
import dev.dcas.util.crypto.OneValueCryptoProvider
import dev.dcas.util.extend.base64Url
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component

@ConditionalOnMissingBean(MultiValueCryptoProvider::class)
@Component
class JavaMultiValueCrypto: AbstractJavaCryptoProvider(), OneValueCryptoProvider {
	override fun get(): String = generator.generateKey().encoded.base64Url()
}