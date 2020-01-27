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

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

class JavaCryptoProviderTest {

	@Test
	fun `onevalue always returns same value`() {
		val provider = JavaOneValueCrypto()
		assertThat(provider.get(), `is`(provider.get()))
	}

	@RepeatedTest(100)
	fun `multivalue always returns different value`() {
		val provider = JavaMultiValueCrypto()
		assertThat(provider.get(), not(provider.get()))
	}
}