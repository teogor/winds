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

package dev.teogor.winds.api.util

import dev.teogor.winds.api.model.Contributor
import dev.teogor.winds.api.model.Developer

@Deprecated("")
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

/**
 * An implementation of the `Contributor` interface using String properties.
 *
 * This class provides concrete implementations for all properties of the [Contributor]
 * interface using String types.
 *
 * It is a convenient way to represent a contributor with basic information.
 */
@Deprecated("")
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
