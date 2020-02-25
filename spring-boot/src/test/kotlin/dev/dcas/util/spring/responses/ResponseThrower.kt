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

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Starts a Spring Web application
 * Disables JPA-related AutoConfiguration
 */
@SpringBootApplication(exclude = [
	DataSourceAutoConfiguration::class,
	DataSourceTransactionManagerAutoConfiguration::class,
	HibernateJpaAutoConfiguration::class
])
@RestController
open class ResponseThrower {

	@GetMapping("/400")
	fun badRequest(): Unit = throw BadRequestResponse()

	@GetMapping("/401")
	fun unauthorized(): Unit = throw UnauthorizedResponse()

	@GetMapping("/403")
	fun forbidden(): Unit = throw ForbiddenResponse()

	@GetMapping("/404")
	fun notFound(): Unit = throw NotFoundResponse()

	@GetMapping("/409")
	fun conflict(): Unit = throw ConflictResponse()

	@GetMapping("/429")
	fun toManyRequests(): Unit = throw RateLimitResponse()

	@GetMapping("/500")
	fun internalError(): Unit = throw InternalErrorResponse()
}