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

package dev.dcas.util.spring.controller

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration
import javax.servlet.http.HttpServletResponse

@ConditionalOnBean(Swagger2DocumentationConfiguration::class)
@RestController
class SwaggerController {

	@GetMapping
	fun index(response: HttpServletResponse) {
		response.sendRedirect("/swagger-ui.html")
	}

	@GetMapping("/csrf")
	fun csrf() {
		// no op to appease swagger
	}
}