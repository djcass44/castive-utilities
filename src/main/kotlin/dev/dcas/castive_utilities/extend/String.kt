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

package dev.dcas.castive_utilities.extend

import dev.dcas.castive_utilities.Environment
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * Load the corresponding environment variable from this key
 */
fun String.env(default: String = ""): String = Environment.get(this, default)

/**
 * Convert a URL to being URL-safe
 */
fun String.safe(): String = URLEncoder.encode(this, StandardCharsets.UTF_8)

/**
 * Checks whether a String is null, blank or only include ECMAScript (JS) nullable types
 */
fun String?.isESNullOrBlank(): Boolean = isNullOrBlank() || this == "null" || this == "undefined"

/**
 * Attempt to convert a String into a UUIDv4
 * @return UUID or null
 */
fun String.uuid(): UUID? = runCatching {
	UUID.fromString(this)
}.getOrNull()

/**
 * Attempt to convert a String into a URL
 * @return URL or null
 */
fun String.url(): URL? = runCatching {
	URL(this)
}.getOrNull()


/**
 * Encode a String as Base64
 * Not url safe
 */
fun String.base64(): String = Base64.getEncoder().encodeToString(this.toByteArray())

/**
 * Convert a Base64 String into its original form
 * Not url safe
 */
fun String.decodeBase64(): String = String(Base64.getDecoder().decode(this))