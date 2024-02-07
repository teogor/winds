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

package dev.teogor.winds.gradle.tasks.impl

import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.model.BomInfo
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.codegen.implementationMarkdownContent
import dev.teogor.winds.codegen.releaseChangelogMarkdownContent
import dev.teogor.winds.common.ErrorId
import dev.teogor.winds.gradle.tasks.BaseGeneratorTask
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.provideDelegate
import java.io.File
import java.time.Instant
import java.time.ZoneOffset

abstract class DocsGeneratorTask : BaseGeneratorTask(
  description = "Generates documentation for a project.",
) {

  private lateinit var docsGenerator: DocsGenerator
  private lateinit var projectDir: File

  private val libraries = mutableListOf<ModuleInfo>()

  private val docsFolder = root directory "docs"

  private val resFolder = root directory ".winds/resources"

  private val bomFolder = docsFolder directory "bom"

  private val bomResFolder = resFolder directory "bom"

  private val releasesDir = docsFolder directory "releases"

  private val changelogDir = releasesDir directory "changelog"

  private val alphaEmoji = "\uD83E\uDDEA"
  private val betaEmoji = "\uD83D\uDEE0\uFE0F"
  private val deprecatedEmoji = "\uD83D\uDEA7"

  private fun MutableList<ModuleInfo>.bom() = firstOrNull { it.isBoM }

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

  private val bomLibrary: ModuleInfo
    get() = libraries.bom() ?: bomLibraryError()

  private val hasBoM: Boolean
    get() = libraries.bom() != null

  // todo be able to use version without calling toString()
  private val bomResLibraryInfo: File
    get() = bomResFolder directory bomLibrary.version.toString()

  // todo be able to use version without calling toString()
  private val bomLibraryInfo: File
    get() = bomFolder directory bomLibrary.version.toString()

  private fun getBomVersions(): List<BomInfo> {
    val filePath = "versions.json"
    val bomVersionsFile = File("$bomResFolder\\$filePath")
    return if (bomVersionsFile.exists()) {
      val jsonString = bomVersionsFile.readText()
      val bomInfoList = Json.decodeFromString<List<BomInfo>>(jsonString)
      bomInfoList.sortedByDescending { it.date }
    } else {
      emptyList()
    }
  }

  private val previousBomVersion by lazy {
    val bomInfoList = getBomVersions()
    bomInfoList.firstOrNull()
  }

  @TaskAction
  override fun action() {
    libraries.sortWith { library1, library2 -> library1.path.compareTo(library2.path) }

    storeBoMVersions()
    writeBoMVariants()
    writeReleases()
  }

  private fun writeReleases() {
    writeReleasesImplementation()
    writeReleaseChangelog()
    updateMkDocs()
  }

  private fun updateMkDocs(
    mkDocsPath: String = "mkdocs.yml",
    section: String = "Changelog",
  ) {
    val mkDocsFile = File("$projectDir/$mkDocsPath")
    if (!mkDocsFile.exists()) {
      return
    }

    val mkDocsFileContent = mkDocsFile.readText()
    val mkDocsFileLines = mkDocsFileContent.split("\n").toMutableList()
    val changelogIndex = mkDocsFileLines.indexOfFirst { it.trimStart().startsWith("- $section:") }

    if (changelogIndex == -1) {
      return
    }

    val categoryIndent = mkDocsFileLines[changelogIndex].takeWhile { it.isWhitespace() }.length
    var hasFoundDifferentIndent = false
    val filteredChangelogEntries = mkDocsFileLines
      .subList(changelogIndex + 1, mkDocsFileLines.size)
      .takeWhile { line ->
        val lineWhitespaceLength = line.takeWhile { it.isWhitespace() }.length
        if (lineWhitespaceLength <= categoryIndent || hasFoundDifferentIndent) {
          hasFoundDifferentIndent = true
          false
        } else {
          true
        }
      }
      .map { it.substringAfter("-").substringBefore(":").trim() }

    val bomVersion = bomLibrary.version.toString()
    if (!filteredChangelogEntries.any { it == bomVersion }) {
      val padding = buildString {
        repeat(categoryIndent) {
          append(" ")
        }
        append("  ")
      }
      val newChangelogEntry = "$padding- $bomVersion: releases/changelog/$bomVersion.md"
      mkDocsFileLines.add(changelogIndex + 1, newChangelogEntry)
      val updatedMkDocsFileContent = mkDocsFileLines.joinToString("\n")
      mkDocsFile.writeText(updatedMkDocsFileContent)
    }
  }

  private fun writeReleasesImplementation() {
    val projectIdentifier = docsGenerator.identifier
    val versionsTable = getBomVersions().joinToString(separator = "\n") {
      "| ${it.version} | [changelog \uD83D\uDD17](changelog/${it.version}.md) | ${it.dateFormatted} |"
    }

    val versionCatalogDefaultVersions = libraries
      .filter { it.canBePublished && !it.isBoM }
      .map { library ->
        val versionRefName = library.names.dropLast(1).joinToString(separator = "-").lowercase()
        "$versionRefName = \"${library.version}\""
      }
      .distinct()
      .joinToString(separator = "\n    ")
    val versionCatalogDefaultLibraries = libraries
      .filter { it.canBePublished && !it.isBoM }
      .joinToString(separator = "\n    ") { library ->
        val name = library.names.joinToString(separator = "-").lowercase().replace(" ", "-")
        val versionRefName = library.names.dropLast(1).joinToString(separator = "-").lowercase()
        "$name = { group = \"${library.groupId}\", name = \"${library.artifactId}\", version.ref = \"$versionRefName\" }"
      }
    val versionCatalogDefault = """[versions]
    $versionCatalogDefaultVersions

    [libraries]
    $versionCatalogDefaultLibraries
    """.trimIndent()

    val bomLibArtifactName = "${
      bomLibrary.names.joinToString(
        separator = "-",
      ).lowercase()
    } = \"${bomLibrary.version}\""

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
            "      implementation(libs.$name)",
          )
        }
    }.trimEnd()

    releasesDir file "implementation.md" write {
      buildString {
        val content = implementationMarkdownContent
          .replace("&&version&&", "${bomLibrary.version}")
          .replace("&&projectName&&", docsGenerator.name)
          .replace("&&versionsTable&&", versionsTable)
          .replace("&&versionCatalogDefault&&", versionCatalogDefault)
          .replace("&&versionCatalogBom&&", versionCatalogBom)
          .replace("&&dependenciesImplementationKotlin&&", dependenciesImplementationKotlin)
          .replace("&&dependenciesImplementationGroovy&&", dependenciesImplementationGroovy)

        append(content)
      }.also { write(it) }
    }
  }

  private fun writeReleaseChangelog() {
    val sdkVersions = libraries
      .filter { !it.isBoM && it.canBePublished }
      .joinToString(separator = "\n") { library ->
        val emoji = when {
          library.version.isDeprecated -> deprecatedEmoji
          library.version.isAlphaRelease -> alphaEmoji
          library.version.isBetaRelease -> betaEmoji
          else -> ""
        }
        "| $emoji | [${
          library.names.joinToString(
            separator = " ",
          )
        }](../../../reference${library.localPath}) | ${library.gradleDependency} | ${library.version} |"
      }

    changelogDir file "${bomLibrary.version}.md" write {
      buildString {
        val content = releaseChangelogMarkdownContent
          .replace("&&version&&", "${bomLibrary.version}")
          .replace("&&sdkVersions&&", sdkVersions)

        append(content)
      }.also { write(it) }
    }
  }

  @OptIn(ExperimentalSerializationApi::class)
  private fun storeBoMVersions() {
    val bomInfoList = getBomVersions().toMutableList()
    val hasVersion = bomInfoList.firstOrNull {
      it.version.toString() == bomLibrary.version.toString()
    } != null

    if (!hasVersion) {
      val currentUtcTime = Instant.now().atZone(ZoneOffset.UTC)
      bomInfoList.add(
        BomInfo(
          version = bomLibrary.version,
          date = currentUtcTime.toEpochSecond(),
        ),
      )

      val json = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
      }
      val jsonString = json.encodeToString(bomInfoList.sortedBy { it.date })

      val filePath = "versions.json"
      val bomVersionsFile = bomResFolder file filePath
      bomVersionsFile.writeText(jsonString)
    }
  }

  private fun writeBoMVariants() {
    writeBoMVariantsJson()
  }

  @OptIn(ExperimentalSerializationApi::class)
  private fun writeBoMVariantsJson() {
    val filePath = "dependencies-${bomLibrary.version}.json"
    val bomMappingFile = bomResLibraryInfo file filePath

    val libraryInfoList = libraries.filter { !it.isBoM && it.canBePublished }

    val json = Json {
      prettyPrint = true
      prettyPrintIndent = "  "
    }
    val jsonString = json.encodeToString(libraryInfoList)

    bomMappingFile.writeText(jsonString)
  }


  /**
   * Adds a library to the internal collection, ensuring uniqueness based on Gradle
   * dependency.
   *
   * @param data The [ModuleInfo] representing the library to be added.
   *
   * @see provideDocsGenerator
   * @see provideProjectDir
   */
  fun addLibrary(data: ModuleInfo) {
    // Check if a library with the same Gradle dependency already exists.
    if (libraries.firstOrNull { it.gradleDependency == data.gradleDependency } == null) {
      // If not, add the new library to the collection.
      libraries.add(data)
    }
  }

  /**
   * Injects an instance of the [DocsGenerator] for documentation generation purposes.
   *
   * @param docsGenerator The [DocsGenerator] instance to be used.
   *
   * @see addLibrary
   * @see provideProjectDir
   */
  fun provideDocsGenerator(docsGenerator: DocsGenerator) {
    this.docsGenerator = docsGenerator
  }

  /**
   * Sets the project directory for this object.
   *
   * This function allows you to explicitly set the project directory for this object.
   * This can be useful when you need to work with the project directory outside of the
   * usual context, such as within tests or during custom processing.
   *
   * @param projectDir The directory representing the project root.
   *
   * @see addLibrary
   * @see provideDocsGenerator
   */
  fun provideProjectDir(projectDir: File) {
    this.projectDir = projectDir
  }
}
