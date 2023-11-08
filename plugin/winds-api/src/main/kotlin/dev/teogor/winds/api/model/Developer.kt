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

/**
 * A developer involved in the project.
 */
interface Developer {

  /**
   * The unique ID of this developer in the SCM.
   */
  val id: String

  /**
   * The name of this developer.
   */
  val name: String

  /**
   * The email of this developer.
   */
  val email: String

  /**
   * The URL of this developer.
   */
  val url: String

  /**
   * The roles of this developer.
   */
  val roles: List<String>

  /**
   * The timezone of this developer.
   */
  val timezone: String

  /**
   * The organization name of this developer.
   */
  val organization: String

  /**
   * The organization's URL of this developer.
   */
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
