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

package dev.dcas.util.spring.responses

import dev.dcas.util.spring.test.BaseSpringBootTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ResponsesTest: BaseSpringBootTest() {

	@ParameterizedTest
	@ValueSource(ints = [400, 401, 403, 404, 409, 429, 500])
	fun `when error is thrown, correct status code is returned`(code: Int) {
		When {
			get("/$code")
		} Then {
			statusCode(code)
		}
	}
}