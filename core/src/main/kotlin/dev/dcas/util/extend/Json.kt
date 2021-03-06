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

import com.google.gson.GsonBuilder
import java.lang.reflect.Type

private val gson = GsonBuilder().setPrettyPrinting().create()

/**
 * Convert an arbitrary object into JSON using GSON
 */
fun Any.json(): String = gson.toJson(this)

/**
 * Convert a JSON string into a Java class instance using GSON
 */
fun <T> String.parse(classType: Class<T>): T = gson.fromJson(this, classType)

/**
 * Convert a JSON string into a Java class instance using GSON
 */
fun <T> String.parse(type: Type): T = gson.fromJson(this, type)