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

package dev.teogor.winds.gradle.docs

import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.model.ModuleDependencyRecord
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.codegen.implementationStandardMarkdownContent
import dev.teogor.winds.ktx.file
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * DocsModuleHandler implementation for projects without a BOM dependency.
 *
 * @param projectDir The root directory of the project.
 * @param docsGenerator The DocsGenerator instance to use for documentation
 * generation.
 * @param libraries A mutable list of ModuleInfo objects representing project
 * dependencies.
 */
class StandardDocsModuleHandler(
  projectDir: File,
  docsGenerator: DocsGenerator,
  private val libraries: MutableList<ModuleInfo>,
) : DocsModuleHandler(projectDir, docsGenerator) {

  private val nowString: String
    get() {
      val instant = Instant.ofEpochSecond(Instant.now().epochSecond)
      val zoneId = ZoneId.of("UTC")
      val zonedDateTime = instant.atZone(zoneId)
      val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
      return formatter.format(zonedDateTime)
    }

  private val modulesFile by lazy { resFolder file "modules.json" }

  private fun readBomVersions(
    file: File = modulesFile,
  ) = if (file.exists()) {
    val jsonString = file.readText()
    try {
      val bomInfoList = Json.decodeFromString<List<ModuleDependencyRecord>>(jsonString)
      bomInfoList.sortedByDescending { it.date }
    } catch (_: Exception) {
      emptyList()
    }
  } else {
    emptyList()
  }

  @OptIn(ExperimentalSerializationApi::class)
  private fun writeBomVersions(
    bomInfoList: List<ModuleDependencyRecord>,
    file: File = modulesFile,
  ) {
    val json = Json {
      prettyPrint = true
      prettyPrintIndent = "  "
    }
    val jsonString = json.encodeToString(bomInfoList.sortedBy { it.date })
    file.writeText(jsonString)
  }

  override fun manageDependencies() {
    val bomInfoList = readBomVersions()

    val librariesToUpdate = findOutdatedLibraries(bomInfoList, libraries)

    if (librariesToUpdate.isNotEmpty()) {
      writeBomVersions(
        bomInfoList = bomInfoList + librariesToUpdate,
      )
    }
  }

  private fun findOutdatedLibraries(
    bomInfoList: List<ModuleDependencyRecord>,
    libraries: MutableList<ModuleInfo>,
  ): List<ModuleDependencyRecord> {
    return libraries.filter { library ->
      !bomInfoList.any { bomInfo -> bomInfo.module == library.module } ||
        bomInfoList.any { bomInfo ->
          bomInfo.module == library.module && bomInfo.version < library.version
        }
    }.map {
      ModuleDependencyRecord(
        it.module,
        it.version,
        Instant.now().epochSecond,
      )
    }
  }

  override fun writeReleaseNotes() {
    generateReleaseDocumentation()
  }

  override fun updateMkDocs(mkDocsPath: String, section: String) {
    // writeMkDocs(mkDocsPath, section, "version")
  }

  private fun generateReleaseDocumentation() {
    val versionsTable = libraries.joinToString("\n") {
      "| ${it.version} | [changelog \uD83D\uDD17](changelog/${it.version}.md) | $nowString |"
    }

    val implementationMarkdown = generateImplementationMarkdown(
      versionsTable,
    )

    releasesDir.file("implementation.md").writeText(implementationMarkdown)
  }

  private fun generateImplementationMarkdown(
    versionsTable: String,
  ): String {
    val versionCatalogDefaultVersions = libraries
      .filter { it.canBePublished }
      .map { library ->
        val versionRefName =
          library.names.dropLast(1).joinToString(separator = "-").lowercase().replace(" ", "-")
        "$versionRefName = \"${library.version}\""
      }
      .distinct()
      .joinToString(separator = "\n    ")
    val versionCatalogDefaultLibraries = libraries
      .filter { it.canBePublished }
      .joinToString(separator = "\n    ") { library ->
        val name = library.names.joinToString(separator = "-").lowercase().replace(" ", "-")
        val versionRefName =
          library.names.dropLast(1).joinToString(separator = "-").lowercase().replace(" ", "-")
        "$name = { group = \"${library.groupId}\", name = \"${library.artifactId}\", version.ref = \"$versionRefName\" }"
      }
    val versionCatalogDefault = """[versions]
    $versionCatalogDefaultVersions

    [libraries]
    $versionCatalogDefaultLibraries
    """.trimIndent()

    val dependenciesImplementationKotlin = buildString {
      appendLine("      // ${docsGenerator.name} Libraries")
      libraries
        .filter { it.canBePublished }
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
      appendLine("      // ${docsGenerator.name} Libraries")
      libraries
        .filter { it.canBePublished }
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

    val content = implementationStandardMarkdownContent
      .replace("&&projectName&&", docsGenerator.name)
      .replace("&&versionsTable&&", versionsTable)
      .replace("&&versionCatalogDefault&&", versionCatalogDefault)
      .replace("&&dependenciesImplementationKotlin&&", dependenciesImplementationKotlin)
      .replace("&&dependenciesImplementationGroovy&&", dependenciesImplementationGroovy)

    return content
  }
}
