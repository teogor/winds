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
import dev.teogor.winds.common.MarkdownTable
import dev.teogor.winds.common.markdownTable
import dev.teogor.winds.gradle.codegen.RegionTag
import dev.teogor.winds.gradle.codegen.bomMappingMdContent
import dev.teogor.winds.gradle.codegen.bomMdDefault
import dev.teogor.winds.gradle.codegen.bomVersionCatalogLibraries
import dev.teogor.winds.gradle.codegen.dependenciesBomVersionCatalog
import dev.teogor.winds.gradle.codegen.implementationPlatformBom
import dev.teogor.winds.gradle.codegen.importLibraryByVersionCoordinates
import dev.teogor.winds.gradle.codegen.importLibraryByVersionName
import dev.teogor.winds.gradle.codegen.importLibraryCoordinates
import dev.teogor.winds.gradle.codegen.libraryName
import dev.teogor.winds.gradle.codegen.replaceContentsWithinRegion
import dev.teogor.winds.gradle.ktx.replace
import dev.teogor.winds.gradle.utils.escapeAlias
import dev.teogor.winds.gradle.utils.isBomLibrary
import dev.teogor.winds.gradle.utils.isLibrary
import org.gradle.configurationcache.extensions.capitalized
import java.io.File
import kotlin.random.Random

fun asVersionCatalogImpl(
  library: ModuleDescriptor,
  versionRef: Boolean,
  owner: String?,
  includeOwner: Boolean = true,
  asModule: Boolean = false,
): String {
  val versionAlias = if (includeOwner && owner != null) {
    "$owner ${library.completeName}"
  } else {
    library.completeName
  }.escapeAlias("-")
  val libraryAlias = if (includeOwner && owner != null) {
    "$owner ${library.completeName}"
  } else {
    library.completeName
  }.escapeAlias("-")
  val components = mutableListOf<String>()
  if (asModule) {
    components.add("module = \"${library.artifact.module}\"")
  } else {
    components.add("group = \"${library.artifact.group}\"")
    components.add("name = \"${library.artifact.artifactId}\"")
  }
  if (versionRef) {
    components.add("version.ref = \"$versionAlias\"")
  }
  return "$libraryAlias = { ${components.joinToString(separator = ", ")} }"
}

fun createBomNotes(
  module: ModuleDescriptor,
  modules: List<ModuleDescriptor>,
  inputDir: File,
  outputDir: File,
  includeOwner: Boolean,
  asModule: Boolean,
) {
  inputDir.mkdirs()
  outputDir.mkdirs()

  val bomModule = modules.first { it.isBom }

  bomMd(
    inputDir = inputDir,
    bomModule = bomModule,
    modules = modules,
    includeOwner = includeOwner,
    asModule = asModule,
  )

  bomMappingMd(
    inputDir = inputDir,
    bomModule = bomModule,
    modules = modules,
  )

  createModuleNotes(
    module = bomModule,
    modules = modules.filter { it.isLibrary() && it.publish },
    inputDir = inputDir,
    outputDir = outputDir,
    includeOwner = true,
    asModule = false,
    fileName = "implementation",
    isBom = true,
  )
}

fun bomMd(
  inputDir: File,
  bomModule: ModuleDescriptor,
  modules: List<ModuleDescriptor>,
  includeOwner: Boolean,
  asModule: Boolean,
) {
  val file = File(inputDir, "index.md").also { file ->
    if (!file.exists()) {
      file.writeText(
        buildString {
          append(bomMdDefault)
        },
      )
    }
  }

  val scm = bomModule.scm
  val ticketSystem = bomModule.ticketSystem
  val libraryByVersion = modules.filterNot {
    it.isBomLibrary()
  }.filter {
    it.publish
  }.random(Random(0))
  val library = modules.filterNot {
    it.isBomLibrary()
  }.filter {
    it.publish
  }.random(Random(1))

  file.replaceContentsWithinRegion(
    region = RegionTag.DifferentLibraryVersionUsage,
    additionalReplacements = {
      replace(implementationPlatformBom, bomModule.artifact.coordinates)
      replace(importLibraryByVersionName, libraryByVersion.name.capitalized())
      replace(
        oldValue = importLibraryByVersionCoordinates,
        newValue = libraryByVersion.artifact.coordinates,
      )
      replace(importLibraryCoordinates, library.artifact.module)
      replace(libraryName, bomModule.name.capitalized())
    },
  ) {
    appendLine(
      """
      |### How do I use a different library version than what's designated in the BOM?
      |
      |In the `build.gradle` dependencies section, keep the import of the BOM platform. On the library
      |dependency import, specify the desired version. For example, here's how to declare dependencies if
      |you want to use a different version of $importLibraryByVersionName, no matter what version is designated
      |in the BOM:
      |
      |```groovy
      |dependencies {
      |  // Import the $libraryName BOM
      |  implementation platform('$implementationPlatformBom')
      |
      |  // Import $importLibraryByVersionName library
      |  implementation '$importLibraryByVersionCoordinates'
      |
      |  // Import other $libraryName libraries without version numbers
      |  // ..
      |  implementation '$importLibraryCoordinates'
      |}
      |```
      """.trimMargin(),
    )
    appendLine()
  }

  file.replaceContentsWithinRegion(
    region = RegionTag.BomWithVersionCatalog,
    additionalReplacements = {
      replace(bomVersionCatalogLibraries) {
        appendLine(
          asVersionCatalogImpl(
            library = bomModule,
            versionRef = true,
            owner = scm?.owner,
            includeOwner = includeOwner,
            asModule = asModule,
          ),
        )
        append(
          asVersionCatalogImpl(
            library = library,
            versionRef = false,
            owner = scm?.owner,
            includeOwner = includeOwner,
            asModule = asModule,
          ),
        )
      }
      replace(
        dependenciesBomVersionCatalog,
        buildString {
          appendLine("dependencies {")
          val indent = "    "
          val owner = scm?.owner
          val bomAlias = if (includeOwner && owner != null) {
            "$owner ${bomModule.completeName}"
          } else {
            bomModule.completeName
          }.escapeAlias(".")
          val bomAliasVar = bomAlias.split(".")
            .let { parts ->
              val firstPart = parts.first().lowercase()
              val remainingParts = parts.drop(1).joinToString(separator = "") { it.capitalized() }
              "$firstPart$remainingParts"
            }

          appendLine("${indent}val $bomAliasVar = platform(libs.$bomAlias)")
          appendLine("${indent}implementation($bomAliasVar)")
          appendLine("${indent}androidTestImplementation($bomAliasVar)")
          appendLine()
          appendLine("$indent// import $libraryName dependencies as usual")
          append("}")
        },
      )
      replace(libraryName, bomModule.name.capitalized())
    },
  ) {
    appendLine(
      """
      |### Does the BOM work with version catalogs?
      |
      |Yes. You can include the BOM itself in the version catalog, and omit the other $libraryName library versions:
      |
      |```toml
      |[libraries]
      |$bomVersionCatalogLibraries
      |```
      |
      |Don’t forget to import the BOM in your module’s `build.gradle`:
      |
      |```groovy
      |$dependenciesBomVersionCatalog
      |```
      """.trimMargin(),
    )
    appendLine()
  }

  if (ticketSystem != null) {
    file.replaceContentsWithinRegion(
      region = RegionTag.ReportIssueFeedback,
    ) {
      appendLine(
        """
        |### How do I report an issue or offer feedback on the BOM?
        |
        |You can file issues on our [issue tracker 🔗](${ticketSystem.url}).
        """.trimMargin(),
      )
      appendLine()
    }
  }
}

fun bomMappingMd(
  inputDir: File,
  bomModule: ModuleDescriptor,
  modules: List<ModuleDescriptor>,
) {
  val file = File(inputDir, "bom-mapping.md").also { file ->
    if (!file.exists()) {
      file.writeText(
        buildString {
          append(bomMappingMdContent)
        },
      )
    }
  }

  file.replaceContentsWithinRegion(
    region = RegionTag.BomToLibraryVersionMapping,
  ) {
    appendLine("**BOM version ${bomModule.artifact.version}**")
    appendLine()
    markdownTable {
      addColumn(
        title = "Library group",
        alignment = MarkdownTable.Align.Left,
      )
      addColumn(
        title = "BOM Versions",
        alignment = MarkdownTable.Align.Center,
      )

      modules.filterNot { it.isBomLibrary() }
        .filter { it.publish }
        .sortedWith(compareBy { it.artifact.module })
        .forEach { library ->
          row {
            addValues(
              library.artifact.module,
              library.artifact.version.toString(),
            )
          }
        }
    }.also {
      append(it.build())
    }
  }
}
