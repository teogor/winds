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

package dev.teogor.winds.gradle.docs.factory

import dev.teogor.winds.api.model.ModuleDescriptor
import dev.teogor.winds.gradle.docs.BomDocsModuleHandler
import dev.teogor.winds.gradle.docs.DocsModuleHandler
import dev.teogor.winds.gradle.docs.StandardDocsModuleHandler
import java.io.File

class DocsModuleHandlerFactory {
  fun createHandler(
    projectDir: File,
    bundle: ModuleDescriptor,
    dependencies: List<ModuleDescriptor>,
    outputDir: File,
    buildOutputDir: File,
    forceDateUpdate: Boolean,
  ): DocsModuleHandler {
    return when (bundle.isBom) {
      true -> BomDocsModuleHandler(
        bundle = bundle,
        dependencies = dependencies,
        projectDir = projectDir,
        outputDir = outputDir,
        buildOutputDir = buildOutputDir,
        forceDateUpdate = forceDateUpdate,
      )

      false -> StandardDocsModuleHandler(
        bundle = bundle,
        dependencies = dependencies,
        projectDir = projectDir,
        outputDir = outputDir,
        buildOutputDir = buildOutputDir,
        forceDateUpdate = forceDateUpdate,
      )
    }
  }
}
