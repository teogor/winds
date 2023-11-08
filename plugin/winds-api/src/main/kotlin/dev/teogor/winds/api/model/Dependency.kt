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

import kotlinx.serialization.Serializable

@Serializable
sealed interface DependencyDefinition

@Serializable
data class Dependency(
  val implementationType: String,
  val group: String,
  val artifact: String,
  val version: String,
) : DependencyDefinition

@Serializable
data class LocalProjectDependency(
  val implementationType: String,
  val projectName: String,
  val modulePath: String,
) : DependencyDefinition
