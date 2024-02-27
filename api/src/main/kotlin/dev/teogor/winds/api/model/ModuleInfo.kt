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

import dev.teogor.winds.ktx.getEmoji
import kotlinx.serialization.Serializable

/**
 * Represents a module in a project.

 * @param name The name of the module.
 * @param displayName The display name of the module.
 * @param description The description of the module.
 * @param groupId The group ID of the module.
 * @param artifactId The artifact ID of the module.
 * @param version The version of the module.
 * @param path The path to the module directory.
 * @param dependencies The dependencies of the module.
 */
@Serializable
@Deprecated("")
data class ModuleInfo(
  val completeName: String,
  val name: String,
  val displayName: String,
  val description: String,
  val groupId: String,
  val artifactId: String,
  val version: Version,
  val path: String,
  val dependencies: List<DependencyDefinition>,
  val canBePublished: Boolean,
  val names: List<String>,
) {
  val module = "$groupId:$artifactId"

  @Deprecated("Use 'module' instead", ReplaceWith("module"))
  val gradleDependency: String
    get() = module

  val coordinates = "$groupId:$artifactId:$version"

  val localPath = path.replace(":", "/")
  val isBoM = artifactId.contains("bom")
  val emoji = version.getEmoji()
}
