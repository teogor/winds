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

package dev.teogor.winds.api.docs.impl.factory

import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.docs.DocsModuleHandler
import dev.teogor.winds.api.docs.impl.BomDocsModuleHandler
import dev.teogor.winds.api.docs.impl.StandardDocsModuleHandler
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.ktx.bom
import dev.teogor.winds.ktx.sortByPath
import java.io.File

/**
 * Factory class responsible for creating appropriate DocsModuleHandler instances
 * based on project dependencies.
 */
class DocsModuleHandlerFactory {

  /**
   * Creates the suitable DocsModuleHandler based on the presence of a BOM
   * (Bill of Materials) dependency.
   *
   * @param projectDir The root directory of the project.
   * @param docsGenerator The DocsGenerator instance to use for documentation
   * generation.
   * @param libraries A mutable list of ModuleInfo objects representing project
   * dependencies.
   * @return The appropriate DocsModuleHandler instance, either BomDocsModuleHandler
   * or StandardDocsModuleHandler.
   */
  fun createHandler(
    projectDir: File,
    docsGenerator: DocsGenerator,
    libraries: MutableList<ModuleInfo>,
  ): DocsModuleHandler {
    return when (libraries.bom() != null) {
      true -> BomDocsModuleHandler(
        projectDir = projectDir,
        docsGenerator = docsGenerator,
        libraries = libraries.sortByPath(),
        bomModule = libraries.bom()!!,
      )

      false -> StandardDocsModuleHandler(
        projectDir = projectDir,
        docsGenerator = docsGenerator,
        libraries = libraries.sortByPath(),
      )
    }
  }
}
