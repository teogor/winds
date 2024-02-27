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

package dev.teogor.winds.api.docs.impl

import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.docs.DocsModuleHandler
import dev.teogor.winds.api.model.BillOfMaterialsRecord
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.codegen.implementationBomMarkdownContent
import dev.teogor.winds.codegen.releaseChangelogMarkdownContent
import dev.teogor.winds.common.ErrorId
import dev.teogor.winds.ktx.directory
import dev.teogor.winds.ktx.file
import dev.teogor.winds.ktx.write
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.Instant

/**
 * DocsModuleHandler implementation specifically handling projects with a
 * Bill of Materials (BOM) dependency.
 *
 * @param projectDir The root directory of the project.
 * @param docsGenerator The DocsGenerator instance to use for documentation
 * generation.
 * @param libraries A mutable list of ModuleInfo objects representing project
 * dependencies.
 * @param bomModule The ModuleInfo representing the BOM dependency.
 */
class BomDocsModuleHandler(
  projectDir: File,
  docsGenerator: DocsGenerator,
  private val libraries: MutableList<ModuleInfo>,
  private val bomModule: ModuleInfo,
) : DocsModuleHandler(projectDir, docsGenerator) {

  private fun bomLibraryError(): Nothing = error(
    """
    Failed to retrieve the Bill of Materials (BoM).
    The BoM is not available, which is necessary for this operation.
    Please [create an issue](https://github.com/teogor/winds) to assist in resolving this matter.
    Be sure to include the following error ID in your report to help us identify and address the issue:
    ${ErrorId.BomLibraryError.getErrorIdString()}
    Thank you for your contribution to improving Winds!
    """.trimIndent(),
  )

  private val bomFolder by lazy { docsFolder directory "bom" }

  private val bomResFolder by lazy { resFolder directory "bom" }

  // todo be able to use version without calling toString()
  private val bomResModuleFile by lazy { bomResFolder directory bomModule.version.toString() }

  // todo be able to use version without calling toString()
  private val bomModuleFile by lazy { bomFolder directory bomModule.version.toString() }

  private val bomVersionsFile by lazy { bomResFolder file "versions.json" }

  override fun manageDependencies() {
    val bomInfoList = readBomVersions()

    // Check if current version exists
    val hasVersion = bomInfoList.any { it.version == bomModule.version }

    if (!hasVersion) {
      val newBomInfo = BillOfMaterialsRecord(bomModule.version, Instant.now().epochSecond)
      writeBomVersions(
        bomInfoList = bomInfoList + newBomInfo,
      )
    }

    writeBoMVariantsJson()
  }

  override fun writeReleaseNotes() {
    generateReleaseDocumentation(bomModule.version.toString())
    generateChangelogDocumentation()
  }

  override fun updateMkDocs(mkDocsPath: String, section: String) {
    writeMkDocs(mkDocsPath, section, bomModule.version.toString())
  }

  private fun generateReleaseDocumentation(bomVersion: String) {
    val versionsTable = readBomVersions().joinToString("\n") {
      "| ${it.version} | [changelog \uD83D\uDD17](changelog/${it.version}.md) | ${it.dateFormatted} |"
    }

    val bomCatalogMarkdown = generateBomCatalogMarkdown()
    val implementationMarkdown =
      generateImplementationMarkdown(bomVersion, versionsTable, bomCatalogMarkdown)

    releasesDir.file("implementation.md").writeText(implementationMarkdown)
  }

  private fun generateImplementationMarkdown(
    bomVersion: String,
    versionsTable: String,
    bomCatalogMarkdown: String,
  ): String {
    val versionCatalogDefaultVersions = libraries
      .filter { it.canBePublished && !it.isBoM }
      .map { library ->
        val versionRefName = library.names.dropLast(
          1,
        ).joinToString(separator = "-").lowercase().replace(" ", "-")
        "$versionRefName = \"${library.version}\""
      }
      .distinct()
      .joinToString(separator = "\n    ")
    val versionCatalogDefaultLibraries = libraries
      .filter { it.canBePublished && !it.isBoM }
      .joinToString(separator = "\n    ") { library ->
        val name = library.names.joinToString(separator = "-").lowercase().replace(" ", "-")
        val versionRefName = library.names.dropLast(
          1,
        ).joinToString(separator = "-").lowercase().replace(" ", "-")
        "$name = { group = \"${library.groupId}\", name = \"${library.artifactId}\", version.ref = \"$versionRefName\" }"
      }
    val versionCatalogDefault = """[versions]
    $versionCatalogDefaultVersions

    [libraries]
    $versionCatalogDefaultLibraries
    """.trimIndent()

    val dependenciesImplementationKotlin = buildString {
      appendLine("      // When Using ${docsGenerator.name} BoM")
      libraries
        .filter { it.canBePublished && it.isBoM }
        .forEach { library ->
          val name = library.names.joinToString(separator = ".")
            .lowercase()
            .replace(" ", ".")
            .replace("-", ".")
          appendLine(
            "      implementation(platform(libs.$name))",
          )
        }
      appendLine()
      appendLine("      // ${docsGenerator.name} Libraries")
      libraries
        .filter { it.canBePublished && !it.isBoM }
        .forEach { library ->
          val name = library.names.joinToString(separator = ".")
            .lowercase()
            .replace(" ", ".")
            .replace("-", ".")
          appendLine(
            "      implementation(libs.$name)",
          )
        }
    }.trimEnd()

    val dependenciesImplementationGroovy = buildString {
      appendLine("      // When Using ${docsGenerator.name} BoM")
      libraries
        .filter { it.canBePublished && it.isBoM }
        .forEach { library ->
          val name = library.names.joinToString(separator = ".")
            .lowercase()
            .replace(" ", ".")
            .replace("-", ".")
          appendLine(
            "      implementation platform(libs.$name)",
          )
        }
      appendLine()
      appendLine("      // ${docsGenerator.name} Libraries")
      libraries
        .filter { it.canBePublished && !it.isBoM }
        .forEach { library ->
          val name = library.names.joinToString(separator = ".")
            .lowercase()
            .replace(" ", ".")
            .replace("-", ".")
          appendLine(
            "      implementation libs.$name",
          )
        }
    }.trimEnd()

    val content = implementationBomMarkdownContent
      .replace("&&version&&", bomVersion)
      .replace("&&projectName&&", docsGenerator.name)
      .replace("&&versionsTable&&", versionsTable)
      .replace("&&versionCatalogDefault&&", versionCatalogDefault)
      .replace("&&versionCatalogBom&&", bomCatalogMarkdown)
      .replace("&&dependenciesImplementationKotlin&&", dependenciesImplementationKotlin)
      .replace("&&dependenciesImplementationGroovy&&", dependenciesImplementationGroovy)

    return content
  }

  private fun generateBomCatalogMarkdown(): String {
    val bomLibArtifactName = "${
      bomModule.names.joinToString(
        separator = "-",
      ).lowercase()
    } = \"${bomModule.version}\""

    val versionCatalogBomLibrary = libraries
      .filter { it.canBePublished && it.isBoM }
      .joinToString(separator = "\n    ") { library ->
        val name = library.names.joinToString(separator = "-").lowercase().replace(" ", "-")
        val versionRefName = library.names.joinToString(separator = "-").lowercase()
        "$name = { group = \"${library.groupId}\", name = \"${library.artifactId}\", version.ref = \"$versionRefName\" }"
      }
    val versionCatalogBomLibraries = libraries
      .filter { it.canBePublished && !it.isBoM }
      .joinToString(separator = "\n    ") { library ->
        val name = library.names.joinToString(separator = "-").lowercase().replace(" ", "-")
        "$name = { group = \"${library.groupId}\", name = \"${library.artifactId}\" }"
      }
    val versionCatalogBom = """[versions]
    $bomLibArtifactName

    [libraries]
    $versionCatalogBomLibrary
    $versionCatalogBomLibraries
    """.trimIndent()

    return versionCatalogBom
  }

  private fun generateChangelogDocumentation() {
    val sdkVersions = libraries
      .filter { !it.isBoM && it.canBePublished }
      .joinToString(separator = "\n") { library ->
        val emoji = library.emoji
        "| $emoji | [${
          library.names.joinToString(
            separator = " ",
          )
        }](../../../reference${library.localPath}) | ${library.module} | ${library.version} |"
      }

    changelogDir file "${bomModule.version}.md" write {
      buildString {
        val content = releaseChangelogMarkdownContent
          .replace("&&version&&", "${bomModule.version}")
          .replace("&&sdkVersions&&", sdkVersions)

        append(content)
      }.also { write(it) }
    }
  }

  private fun readBomVersions(
    file: File = bomVersionsFile,
  ) = if (file.exists()) {
    val jsonString = file.readText()
    try {
      val bomInfoList = Json.decodeFromString<List<BillOfMaterialsRecord>>(jsonString)
      bomInfoList.sortedByDescending { it.date }
    } catch (_: Exception) {
      emptyList()
    }
  } else {
    emptyList()
  }

  @OptIn(ExperimentalSerializationApi::class)
  private fun writeBomVersions(
    bomInfoList: List<BillOfMaterialsRecord>,
    file: File = bomVersionsFile,
  ) {
    val json = Json {
      prettyPrint = true
      prettyPrintIndent = "  "
    }
    val jsonString = json.encodeToString(bomInfoList.sortedBy { it.date })
    file.writeText(jsonString)
  }

  @OptIn(ExperimentalSerializationApi::class)
  private fun writeBoMVariantsJson() {
    val bomMappingFile = bomResModuleFile file "dependencies-${bomModule.version}.json"
    val libraryInfoList = libraries.filter { !it.isBoM && it.canBePublished }

    val json = Json {
      prettyPrint = true
      prettyPrintIndent = "  "
    }
    val jsonString = json.encodeToString(libraryInfoList)
    bomMappingFile.writeText(jsonString)
  }
}
