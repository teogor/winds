/*
 * Copyright 2024 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.winds.api

import kotlin.reflect.KClass

sealed interface Person {
  val name: String
  val email: String
  val url: String
  val roles: List<String>
  val timezone: String
  val organization: String
  val organizationUrl: String
  val properties: Map<String, String>

  fun isContributor(): Boolean {
    return this !is Developer
  }

  fun isDeveloper(): Boolean {
    return this !is Contributor
  }

  data class Contributor(
    override var name: String = "",
    override var email: String = "",
    override var url: String = "",
    override var roles: List<String> = emptyList(),
    override var timezone: String = "",
    override var organization: String = "",
    override var organizationUrl: String = "",
    override var properties: Map<String, String> = emptyMap(),
  ) : Person

  data class Developer(
    var id: String = "",
    override var name: String = "",
    override var email: String = "",
    override var url: String = "",
    override var roles: List<String> = emptyList(),
    override var timezone: String = "",
    override var organization: String = "",
    override var organizationUrl: String = "",
    override var properties: Map<String, String> = emptyMap(),
  ) : Person

  data class DeveloperContributor(
    var id: String = "",
    override var name: String = "",
    override var email: String = "",
    override var url: String = "",
    override var roles: List<String> = emptyList(),
    override var timezone: String = "",
    override var organization: String = "",
    override var organizationUrl: String = "",
    override var properties: Map<String, String> = emptyMap(),
  ) : Person

  companion object {
    inline fun <reified T : Person> default() = default(T::class)

    fun default(type: KClass<out Person>): Person? {
      return when (type) {
        Developer::class -> Developer()
        Contributor::class -> Contributor()
        DeveloperContributor::class -> DeveloperContributor()
        else -> null
      }
    }
  }
}
