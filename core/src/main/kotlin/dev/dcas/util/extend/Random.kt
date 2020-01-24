/*
 *    Copyright 2019 Django Cass
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

import java.security.SecureRandom
import kotlin.random.Random

private val random = SecureRandom()

/**
 * Generate a random String that is (this) characters long
 * @throws IllegalArgumentException if (this) is less than or equal to 0
 */
fun Int.randomString(): String {
	if(this <= 0)
		throw IllegalArgumentException("Cannot generate a random String of length <= 0")
	val string = StringBuilder()
	for (i in 0 until this) {
		string.append(Random.nextInt(33, 126).toChar())
	}
	return string.toString()
}