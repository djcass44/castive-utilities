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

import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "2.2.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

group = "dev.dcas.utilities"
val projectVersion: String by project
version = projectVersion

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("com.github.djcass44:log2:4.1")
	implementation(project(":core"))

	// aws parameter store crypto provider
	implementation("com.amazonaws:aws-java-sdk-ssm:1.11.708")
}

tasks {
	withType<BootJar> {
		enabled = false
	}
	withType<Jar> {
		enabled = true
	}
}