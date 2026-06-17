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

package dev.teogor.winds.api.impl

import dev.teogor.winds.api.DocumentationBuilder
import dev.teogor.winds.api.model.DependencyType

class DocumentationBuilderImpl : DocumentationBuilder {
  // Output Configuration
  override var htmlPath: String = ""
  override var mkdocsEnabled: Boolean = false

  // Content Generation
  override var createApiReference: Boolean = true
  override var generateReleaseTable: Boolean = true

  // Release Filtering
  override var includeReleaseCandidate: Boolean = true
  override var includeBetaRelease: Boolean = true
  override var includeAlphaRelease: Boolean = true

  // Dependency Management
  override var alertOnDependentModules: Boolean = true
  override var dependencyGatheringType: DependencyType = DependencyType.NONE

  // Project Properties
  override var isCompiler: Boolean = false
  override var isOptional: Boolean = false

  // Plugin Configuration (Optional)
  override val pluginIds: MutableList<String> = mutableListOf()

  // Newline Separator (Optional)
  override var markdownNewlineSeparator: String = "  "

  override fun copy(from: DocumentationBuilder): DocumentationBuilder {
    // Output Configuration
    htmlPath = from.htmlPath
    mkdocsEnabled = from.mkdocsEnabled

    // Content Generation
    createApiReference = from.createApiReference
    generateReleaseTable = from.generateReleaseTable

    // Release Filtering
    includeReleaseCandidate = from.includeReleaseCandidate
    includeBetaRelease = from.includeBetaRelease
    includeAlphaRelease = from.includeAlphaRelease

    // Dependency Management
    alertOnDependentModules = from.alertOnDependentModules
    dependencyGatheringType = from.dependencyGatheringType

    // Newline Separator (Optional)
    markdownNewlineSeparator = from.markdownNewlineSeparator

    return this
  }
}
