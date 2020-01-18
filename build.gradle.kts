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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
	maven
}

group = "dev.dcas"
version = "4"

repositories {
    mavenCentral()
	jcenter()
	maven(url = "https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")

	// assists library
	implementation("com.github.djcass44:log2:4.1")

	// provides utilities
	implementation("com.google.code.gson:gson:2.8.6")
	implementation("com.amdelamar:jhash:2.1.0")

	// test
	val junitVersion = "5.5.2"
	testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
	testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
	testImplementation("org.jetbrains.kotlin:kotlin-test")

	testImplementation("org.hamcrest:hamcrest:2.2")
	testImplementation("org.mockito:mockito-core:3.2.4")
}

tasks {
	withType<KotlinCompile>().all {
		kotlinOptions.jvmTarget = "11"
	}
	withType<Test> {
		useJUnitPlatform()
	}
}