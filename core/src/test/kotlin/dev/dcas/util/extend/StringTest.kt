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

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class StringTest {
	@ParameterizedTest
	@ValueSource(strings = [
		"",
		" ",
		"null",
		"undefined"
	])
	fun `strings that js would consider null return true`(text: String?) {
		assertThat(text.isESNullOrBlank(), `is`(true))
	}

	@ParameterizedTest
	@ValueSource(strings = [
		"test",
		" n ",
		"nully",
		"undefeated"
	])
	fun `valid strings return false`(text: String?) {
		assertThat(text.isESNullOrBlank(), `is`(false))
	}

	@Test
	fun `ellipsised strings dont throw`() {
		val text = "this is a test!"
		assertDoesNotThrow {
			text.ellipsize(24)
		}
	}

	@Test
	fun `empty strings return nothing`() {
		assertThat("".ellipsize(0), `is`(""))
	}

	@Test
	fun `strings are ellipsised correctly`() {
		val text = "this is a test!"
		assertThat(text.ellipsize(12), `is`("this is a te..."))
	}
}