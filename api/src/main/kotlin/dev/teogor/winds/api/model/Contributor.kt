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

package dev.teogor.winds.api.model

import org.gradle.api.publish.maven.MavenPomContributor

/**
 * A contributor to a Maven publication.
 *
 * This interface represents a person or entity involved in the creation or development
 * of a Maven artifact. It provides properties for basic contributor information such as
 * name, email, URL, organization, roles, and timezone.
 *
 * Additionally, it allows specifying custom properties using a key-value map.
 *
 * @see MavenPomContributor for the original interface definition.
 */
interface Contributor {

  /**
   * The name of the contributor.
   */
  val name: String

  /**
   * The email address of the contributor.
   */
  val email: String

  /**
   * The URL of the contributor.
   */
  val url: String

  /**
   * The organization name of the contributor.
   */
  val organization: String

  /**
   * The URL of the contributor's organization.
   */
  val organizationUrl: String

  /**
   * The roles of the contributor in the project (e.g., "developer", "architect").
   */
  val roles: List<String>

  /**
   * The timezone of the contributor.
   */
  val timezone: String

  /**
   * Additional properties of the contributor represented as a key-value map.
   */
  val properties: Map<String, String>
}

/**
 * An implementation of the `Contributor` interface using String properties.
 *
 * This class provides concrete implementations for all properties of the [Contributor]
 * interface using String types.
 *
 * It is a convenient way to represent a contributor with basic information.
 */
data class ContributorImpl(
  override val name: String = "",
  override val email: String = "",
  override val url: String = "",
  override val organization: String = "",
  override val organizationUrl: String = "",
  override val roles: List<String> = emptyList(),
  override val timezone: String = "",
  override val properties: Map<String, String> = emptyMap(),
) : Contributor
