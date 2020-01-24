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

package dev.dcas.util.spring.data

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class UUIDConverterCompatTest {
	private val converter = UUIDConverterCompat()

	@Test
	fun `can convert valid UUID to string`() {
		val uuid = UUID.randomUUID()
		assertThat(converter.convertToDatabaseColumn(uuid), `is`(uuid.toString()))
	}

	@ParameterizedTest
	@ValueSource(strings = [
		"18e3de05-d339-478e-8abc-d142679d0b0b", // uuids generated using the java13 jshell
		"48cb9f24-5dbe-4635-8fb8-9855b8773307",
		"2bb7e27c-4596-4adb-bc80-42baf67260b3"
	])
	fun `can convert UUID string to java object`(value: String) {
		assertDoesNotThrow {
			converter.convertToEntityAttribute(value)
		}
	}

	@ParameterizedTest
	@ValueSource(strings = [
		"12345",
		"",
		"\n\t",
		"48cb9f24-5dbe-4635-8fb8"
	])
	fun `invalid uuid throws`(value: String) {
		assertThrows<IllegalStateException> {
			converter.convertToEntityAttribute(value)
		}
	}
}