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
  override var htmlPath: String = ""
  override var createApiReference: Boolean = true
  override var generateReleaseTable: Boolean = true
  override var includeReleaseCandidate: Boolean = true
  override var includeBetaRelease: Boolean = true
  override var includeAlphaRelease: Boolean = true
  override var alertOnDependentModules: Boolean = true
  override var dependencyGatheringType: DependencyType = DependencyType.NONE
  override var mkdocsEnabled: Boolean = false
}
