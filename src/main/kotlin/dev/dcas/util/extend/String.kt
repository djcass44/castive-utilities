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

import dev.dcas.util.Environment
import dev.dcas.util.crypto.HashUtil
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
fun String.base64(): String = this.toByteArray().base64()

fun ByteArray.base64(): String = Base64.getEncoder().encodeToString(this)

/**
 * Encode a String as Base64
 * URL safe
 */
fun String.base64Url(): String = this.toByteArray().base64Url()

fun ByteArray.base64Url(): String = Base64.getUrlEncoder().encodeToString(this)

/**
 * Convert a Base64 String into its original form
 * Not url safe
 */
fun String.decodeBase64(): String = String(Base64.getDecoder().decode(this))

/**
 * Convert a Base64 String into its original form
 * URL safe
 */
fun String.decodeBase64Url(): String = String(Base64.getUrlDecoder().decode(this))

/**
 * Create a hash of a string using PBKDF2 + SHA256
 */
fun String.hash(): String = HashUtil.getHash(this).create()

fun String.ellipsize(after: Int = length): String = "${substring(0, after)}..."