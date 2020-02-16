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
val projectVersion: String by project
version = projectVersion

allprojects {
	repositories {
		mavenCentral()
		maven(url = "https://jitpack.io")
	}
	tasks {
		withType<Wrapper> {
			gradleVersion = "6.1"
			distributionType = Wrapper.DistributionType.ALL
		}
		withType<KotlinCompile>().all {
			kotlinOptions.jvmTarget = "11"
		}
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "maven")
	dependencies {
		implementation(kotlin("stdlib-jdk8"))

		// test
		testImplementation("org.junit.jupiter:junit-jupiter-api")
		testImplementation("org.junit.jupiter:junit-jupiter-params")
		testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
		testImplementation("org.jetbrains.kotlin:kotlin-test:1.3.61")

		testImplementation("org.hamcrest:hamcrest:2.2")
		testImplementation("org.mockito:mockito-core:3.2.4")
	}
	tasks {
		withType<Test> {
			useJUnitPlatform()
		}
	}
}