import org.springframework.boot.gradle.tasks.bundling.BootJar

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

plugins {
	id("org.springframework.boot") version "2.2.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

group = "dev.dcas.utilities"
val projectVersion: String by project
version = projectVersion

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// swagger
	implementation("io.springfox:springfox-swagger2:2.9.2")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")


	implementation(project(":core"))

	testImplementation(project(":spring-test"))
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	// rest assured
	val restAssuredVersion = "4.2.0"
	testImplementation("io.rest-assured:json-path:$restAssuredVersion")
	testImplementation("io.rest-assured:xml-path:$restAssuredVersion")
	testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
	testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")
	testImplementation("io.rest-assured:spring-mock-mvc:$restAssuredVersion")
}

tasks {
	withType<BootJar> {
		enabled = false
	}
	withType<Jar> {
		enabled = true
	}
}