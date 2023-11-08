/*
 * Copyright 2023 teogor (Teodor Grigor)
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

package dev.teogor.winds.api.model

interface Developer {
  val id: String
  val name: String
  val email: String
  val url: String
  val roles: List<String>
  val timezone: String
  val organization: String
  val organizationUrl: String
}

data class DeveloperImpl(
  override var id: String = "",
  override var name: String = "",
  override var email: String = "",
  override var url: String = "",
  override var roles: List<String> = emptyList(),
  override var timezone: String = "",
  override var organization: String = "",
  override var organizationUrl: String = "",
) : Developer
