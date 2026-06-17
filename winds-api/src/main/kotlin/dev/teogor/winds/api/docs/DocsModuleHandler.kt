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

package dev.teogor.winds.api.docs

import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.ktx.directory
import dev.teogor.winds.ktx.file
import java.io.File

/**
 * Abstract class handling documentation module tasks.
 *
 * @param projectDir The root directory of the project.
 * @param docsGenerator A DocsGenerator instance for generating documentation.
 */
abstract class DocsModuleHandler(
  projectDir: File,
  protected val docsGenerator: DocsGenerator,
) {
  /**
   * Manages dependencies for the documentation module.
   * (Implementations should provide specific logic).
   */
  abstract fun manageDependencies()

  /**
   * Writes release notes for the current version.
   * (Implementations should provide specific logic).
   */
  abstract fun writeReleaseNotes()

  /**
   * Updates the MkDocs configuration file.
   * (Implementations should provide specific logic).
   *
   * @param mkDocsPath The path to the MkDocs configuration file (default: "mkdocs.yml").
   * @param section The section to update (default: "Changelog").
   */
  abstract fun updateMkDocs(
    mkDocsPath: String = "mkdocs.yml",
    section: String = "Changelog",
  )

  /**
   * Writes a new entry to the specified section in the MkDocs Changelog.
   *
   * @param mkDocsPath The path to the MkDocs configuration file.
   * @param section The section to update.
   * @param version The version to add to the Changelog.
   */
  fun writeMkDocs(
    mkDocsPath: String,
    section: String,
    version: String,
  ) {
    val mkDocsFile = root file mkDocsPath
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

    if (!filteredChangelogEntries.any { it == version }) {
      val padding = buildString {
        repeat(categoryIndent) {
          append(" ")
        }
        append("  ")
      }
      val newChangelogEntry = "$padding- $version: releases/changelog/$version.md"
      mkDocsFileLines.add(changelogIndex + 1, newChangelogEntry)
      val updatedMkDocsFileContent = mkDocsFileLines.joinToString("\n")
      mkDocsFile.writeText(updatedMkDocsFileContent)
    }
  }

  // Protected properties for file paths:
  protected val root = projectDir
  protected val docsFolder by lazy { root directory "docs" }
  protected val resFolder by lazy { root directory ".winds/resources" }
  protected val releasesDir by lazy { docsFolder directory "releases" }
  protected val changelogDir by lazy { releasesDir directory "changelog" }
}
