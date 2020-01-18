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

import com.google.gson.reflect.TypeToken
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test

class JsonTest {

	@Test
	fun `convert primitive object FROM json`() {
		assertThat("1".parse(Int::class.java), `is`(1))
	}

	@Test
	fun `convert complex list FROM json`() {
		val list = arrayListOf(
			"test" to 1,
			"carrot" to 485,
			"fridge" to 5
		)
		val json = list.json()
		assertThat(json.parse(object : TypeToken<ArrayList<Pair<String, Int>>>() {}.type), `is`(list))
	}
}