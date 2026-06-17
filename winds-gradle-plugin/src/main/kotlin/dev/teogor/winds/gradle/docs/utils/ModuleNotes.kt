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

package dev.teogor.winds.gradle.docs.utils

import dev.teogor.winds.api.model.ModuleDescriptor
import dev.teogor.winds.gradle.codegen.ModuleCodeGenWriter
import dev.teogor.winds.gradle.codegen.RegionTag
import dev.teogor.winds.gradle.codegen.indexModuleMarkdownContent
import dev.teogor.winds.gradle.codegen.libraryName
import dev.teogor.winds.gradle.codegen.replaceContentsWithinRegion
import org.gradle.configurationcache.extensions.capitalized
import java.io.File

fun createModuleNotes(
  module: ModuleDescriptor,
  modules: List<ModuleDescriptor>,
  inputDir: File,
  outputDir: File,
  includeOwner: Boolean,
  asModule: Boolean,
  fileName: String,
  isBom: Boolean = false,
) {
  val scm = module.scm

  inputDir.mkdirs()
  outputDir.mkdirs()

  val file = File(inputDir, "$fileName.md").also { file ->
    if (!file.exists()) {
      file.writeText(
        buildString {
          indexModuleMarkdownContent.let {
            var result = it

            result = result.replace(libraryName, module.name.capitalized())

            result
          }.also {
            append(it)
          }
        },
      )
    }
  }

  val codeGenWriter = ModuleCodeGenWriter(
    module = module,
    modules = modules.filter { it.publish },
  )
  file.replaceContentsWithinRegion(
    region = RegionTag.Dependencies,
  ) {
    append(
      codeGenWriter.generateDependencyDocumentation(
        scm = scm,
        includeOwner = includeOwner,
        asModule = asModule,
        isBom = isBom,
      ),
    )
  }
}
