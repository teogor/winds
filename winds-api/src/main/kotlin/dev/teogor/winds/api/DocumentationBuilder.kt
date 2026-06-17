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

package dev.teogor.winds.api

import dev.teogor.winds.api.model.DependencyType
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

interface DocumentationBuilder {

  // Output Configuration
  val htmlPath: Property<String>
  val mkdocsEnabled: Property<Boolean>

  // Content Generation
  val createApiReference: Property<Boolean>
  val generateReleaseTable: Property<Boolean>

  // Release Filtering
  val includeReleaseCandidate: Property<Boolean>
  val includeBetaRelease: Property<Boolean>
  val includeAlphaRelease: Property<Boolean>

  // Dependency Management
  val alertOnDependentModules: Property<Boolean>
  val dependencyGatheringType: Property<DependencyType>

  // Project Properties
  val isCompiler: Property<Boolean>
  val isOptional: Property<Boolean>

  // Plugin Configuration (Optional)
  val pluginIds: ListProperty<String>

  // Newline Separator (Optional)
  val markdownNewlineSeparator: Property<String>

  fun copy(from: DocumentationBuilder): DocumentationBuilder
}
