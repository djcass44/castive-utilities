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

package dev.dcas.util.extend

import com.amdelamar.jhash.Hash
import com.amdelamar.jhash.algorithms.Type
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class StringHashTest {
	@Test
	fun `test hashing returns the correct response`() {
		val manual = Hash.password("this is a test!".toCharArray()).algorithm(Type.PBKDF2_SHA256)
		assertThat(manual.verify("this is a test!".hash()), `is`(true))
	}
}