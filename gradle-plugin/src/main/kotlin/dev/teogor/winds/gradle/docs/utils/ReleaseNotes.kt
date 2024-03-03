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

import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.ReleaseStatus
import dev.teogor.winds.api.model.DependencyBundle
import dev.teogor.winds.api.model.ModuleDescriptor
import dev.teogor.winds.api.model.Path
import dev.teogor.winds.common.MarkdownTable
import dev.teogor.winds.common.markdownTable
import dev.teogor.winds.common.utils.decodeAsChangelogs
import dev.teogor.winds.gradle.codegen.ModuleCodeGenWriter
import dev.teogor.winds.gradle.codegen.RegionTag
import dev.teogor.winds.gradle.codegen.bomModuleMd
import dev.teogor.winds.gradle.codegen.releaseMarkdownContent
import dev.teogor.winds.gradle.codegen.replaceContentsWithinRegion
import dev.teogor.winds.gradle.utils.appendContent
import dev.teogor.winds.gradle.utils.appendTo
import dev.teogor.winds.gradle.utils.escapeAlias
import dev.teogor.winds.ktx.getMajorMinorPatchVersion
import dev.teogor.winds.ktx.getShowcaseCoordinates
import dev.teogor.winds.ktx.validateAndFormatArtifactId
import org.gradle.configurationcache.extensions.capitalized
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.text.SimpleDateFormat

fun formatDependency(dependency: String, path: Path, newlineSeparator: String): String {
  return "[`$dependency`]($path)$newlineSeparator"
}

fun createReleaseNotes(
  bundle: ModuleDescriptor,
  bundleInfo: DependencyBundle,
  dependencies: List<ModuleDescriptor>,
  inputDir: File,
  outputDir: File,
  windsChangelogYml: File = File(bundle.windsChangelogYml),
  fileName: String,
  includeOwner: Boolean,
  asModule: Boolean,
) {
  inputDir.mkdirs()
  outputDir.mkdirs()

  val file = File(inputDir, "$fileName.md").also { file ->
    if (!file.exists()) {
      file.writeText(
        buildString {
          // Header
          appendLine("# ${bundle.completeName}")
          appendLine()
          buildString {
            append("Learn more: ")
            append("**[User Guide](../user-guide.md)**")
            append(" and ")
            append("**[Code Samples](../code-samples.md)**")
          }.appendTo(this)

          bundle.description?.appendTo(this)

          append(releaseMarkdownContent)
        },
      )
    }
  }

  val newlineSeparator = bundle.documentationBuilder.markdownNewlineSeparator

  file.replaceContentsWithinRegion(
    region = RegionTag.ApiReference,
  ) {
    if (bundle.documentationBuilder.createApiReference) {
      appendLine("API Reference$newlineSeparator")

      val htmlPath = Path(bundle.documentationBuilder.htmlPath).let {
        if (it.isUrl()) {
          it
        } else {
          Path("../$it")
        }
      }

      val moduleArtifact = bundle.artifact
      val formattedDependency = when (bundle.artifact.artifactIdFormat) {
        ArtifactIdFormat.FULL -> {
          "${moduleArtifact.module}-*"
        }

        ArtifactIdFormat.GROUP_MODULE_NAME,
        ArtifactIdFormat.MODULE_NAME_ONLY,
        ArtifactIdFormat.NAME_ONLY,
        -> {
          "${moduleArtifact.group}:${moduleArtifact.name.validateAndFormatArtifactId()}-*"
        }
      }.lowercase()
      appendLine(
        formatDependency(
          dependency = formattedDependency,
          path = Path.from(htmlPath, bundle.path.value),
          newlineSeparator = newlineSeparator,
        ),
      )

      dependencies.forEach {
        appendLine(
          formatDependency(
            dependency = it.artifact.module,
            path = Path.from(htmlPath, it.path.value),
            newlineSeparator = newlineSeparator,
          ),
        )
      }

      // todo
      //  appendLine("(See the API reference docs for all compose packages)")
    }
  }

  file.replaceContentsWithinRegion(
    region = RegionTag.ReleaseTable,
  ) {
    if (bundle.documentationBuilder.generateReleaseTable) {
      val columnTitles = mutableListOf(
        "Latest Update" to MarkdownTable.Align.Left,
        "Stable Release" to MarkdownTable.Align.Center,
      )
      val includeReleaseCandidate = bundle.documentationBuilder.includeReleaseCandidate
      val includeBetaRelease = bundle.documentationBuilder.includeBetaRelease
      val includeAlphaRelease = bundle.documentationBuilder.includeAlphaRelease

      if (includeReleaseCandidate) {
        columnTitles.add("Release Candidate" to MarkdownTable.Align.Center)
      }
      if (includeBetaRelease) {
        columnTitles.add("Beta Release" to MarkdownTable.Align.Center)
      }
      if (includeAlphaRelease) {
        columnTitles.add("Alpha Release" to MarkdownTable.Align.Center)
      }

      val stableIndex = columnTitles.indexOfFirst { it.first == "Stable Release" }
      val releaseCandidateIndex = columnTitles.indexOfFirst { it.first == "Release Candidate" }
      val betaIndex = columnTitles.indexOfFirst { it.first == "Beta Release" }
      val alphaIndex = columnTitles.indexOfFirst { it.first == "Alpha Release" }

      markdownTable {
        columnTitles.forEach {
          addColumn(
            title = it.first,
            alignment = it.second,
          )
        }

        row(true) {
          addValueAt(
            index = 0,
            value = SimpleDateFormat("MMMM dd, yyyy").format(bundleInfo.date * 1000),
          )
          addValueAt(
            index = when (bundleInfo.version.flag) {
              ReleaseStatus.Stable -> stableIndex
              ReleaseStatus.Deprecated -> stableIndex
              ReleaseStatus.ReleaseCandidate -> releaseCandidateIndex
              ReleaseStatus.Beta -> betaIndex
              ReleaseStatus.Alpha -> alphaIndex
              else -> stableIndex
            },
            value = bundleInfo.version.toString(),
          )
          row.forEachIndexed { index, s ->
            if (s.isEmpty()) {
              row[index] = "-"
            }
          }
        }
      }.also {
        append(it.build())
      }
    }
  }

  file.replaceContentsWithinRegion(
    region = RegionTag.Dependencies,
  ) {
    appendLine("## Declaring dependencies")
    appendLine()
    val codeGenWriter = ModuleCodeGenWriter(
      module = bundle,
      modules = dependencies.filter { it.publish },
    )
    codeGenWriter.generateDependencyDocumentation(
      scm = bundle.scm,
      includeOwner = includeOwner,
      asModule = asModule,
      short = true,
      implementationPath = if (bundle.path.isRoot()) {
        "../index.md"
      } else {
        "../${bundle.name.lowercase()}/index.md"
      },
    ).also {
      appendLine(it)
    }
  }

  file.replaceContentsWithinRegion(
    region = RegionTag.Feedback,
  ) {
    if (bundle.ticketSystem != null) {
      appendLine("## Feedback")
      appendLine()
      appendLine(
        """
          Your feedback helps make ${bundle.name.capitalized()} better. Let us know if you discover new issues or have
          ideas for improving this library. Please take a look at the [existing issues on ${bundle.ticketSystem!!.system}](${bundle.ticketSystem!!.url})
          for this library before you create a new one.
        """.trimIndent(),
      )
      appendLine()
      appendLine("[Create a new issue](${bundle.ticketSystem!!.url}/new){ .md-button }")
    }
  }

  file.replaceContentsWithinRegion(
    region = RegionTag.VersionChangelog,
  ) {
    if (windsChangelogYml.exists()) {
      val yaml = Yaml()

      @Suppress("VulnerableCodeUsages")
      val data = yaml.loadAs(
        windsChangelogYml.readText(),
        Map::class.java,
      ).decodeAsChangelogs().sortedByDescending {
        it.version
      }
      data.groupBy {
        it.version.getMajorMinorPatchVersion()
      }.forEach { (strippedVersion, versions) ->
        appendLine("### Version $strippedVersion")
        appendLine()
        versions.forEach {
          val version = it.version
          val previousVersion = it.previousVersion
          val releaseLinkText = bundle.artifact.getShowcaseCoordinates(version)
          val releaseLinkUrl = "${bundle.scm?.repositoryUrl}/releases/$version"
          val releaseLink = "[`$releaseLinkText`]($releaseLinkUrl)"

          val versionLinkText = "Version $version contains these commits"
          val versionLinkUrl = if (previousVersion != null) {
            "${bundle.scm?.repositoryUrl}/compare/$previousVersion...$version"
          } else {
            "${bundle.scm?.repositoryUrl}/commits/$version"
          }
          val versionLink = "[$versionLinkText]($versionLinkUrl)"
          appendLine("#### Version ${it.version}")
          appendLine()
          if (it.info != null) {
            appendLine("${it.info}")
            appendLine()
          }
          appendLine("$releaseLink is released. $versionLink")
          appendLine()
          if (it.content != null) {
            appendLine("${it.content}")
            appendLine()
          }
          it.sections
            ?.filter { it.items.isNotEmpty() }
            ?.forEach { section ->
              with(section) {
                appendLine("**$type**")
                appendLine()
                items.forEach {
                  val usernameRegex = Regex("""@(\w+)""")
                  val issueNumberRegex = Regex("""#(\d+)""")
                  val usernameReplacement = { matchResult: MatchResult ->
                    val username = matchResult.groups[1]!!.value
                    "[@$username](https://github.com/$username)"
                  }
                  val issueNumberReplacement = { matchResult: MatchResult ->
                    val issueNumber = matchResult.groups[1]!!.value
                    "[#$issueNumber](${bundle.ticketSystem?.url}/$issueNumber)"
                  }

                  val message = it.message.let { message ->
                    usernameRegex.replace(message, usernameReplacement)
                  }.let { message ->
                    issueNumberRegex.replace(message, issueNumberReplacement)
                  }

                  appendLine("* $message")
                }
                appendLine()
              }
            }
        }
      }
    }
  }
}

fun createBomGroupNotes(
  bundle: ModuleDescriptor,
  bundlesInfo: List<DependencyBundle>,
  dependencies: List<ModuleDescriptor>,
  inputDir: File,
  outputDir: File,
  fileName: String,
  includeOwner: Boolean,
  asModule: Boolean,
) {
  inputDir.mkdirs()
  outputDir.mkdirs()

  val file = File(inputDir, "$fileName.md").also { file ->
    if (!file.exists()) {
      file.writeText(
        buildString {
          // Header
          appendLine("# ${bundle.completeName}")
          appendLine()
          buildString {
            append("Learn more: ")
            append("**[User Guide](user-guide.md)**")
            append(" and ")
            append("**[Code Samples](code-samples.md)**")
          }.appendTo(this)

          bundle.description?.appendTo(this)

          appendLine(bomModuleMd)

          append(
            """
            |## BOMs
            |
            |To stay updated with the latest BOM releases, explore the [${bundle.name.capitalized()} BOM Mapping Page](./bom/bom-mapping.md).
            """.trimMargin(),
          )
        },
      )
    }
  }

  val newlineSeparator = bundle.documentationBuilder.markdownNewlineSeparator

  // API Reference
  file.replaceContentsWithinRegion(
    region = RegionTag.ApiReference,
  ) {
    if (bundle.documentationBuilder.createApiReference) {
      appendContent {
        appendLine("API Reference$newlineSeparator")
        val htmlPath = Path(bundle.documentationBuilder.htmlPath).let {
          if (it.isUrl()) {
            it
          } else {
            Path("$it")
          }
        }

        val moduleArtifact = bundle.artifact
        val formattedDependency = "${moduleArtifact.group}:*".lowercase()
        appendLine(
          formatDependency(
            dependency = formattedDependency,
            path = Path.from(htmlPath, bundle.path.value),
            newlineSeparator = newlineSeparator,
          ),
        )
      }
    }
  }

  file.replaceContentsWithinRegion(
    region = RegionTag.GroupOverview,
  ) {
    appendLine(
      """
      |**${bundle.name.capitalized()}** is formed by combining ${dependencies.size} Maven Group Ids within ${bundle.artifact.group}. Each Group encompasses a targeted subset of functionality, with individual sets of release notes.
      |
      |This table provides an overview of the groups and their corresponding links to individual sets of release notes.
      |
      """.trimMargin(),
    )

    // Releases
    appendContent {
      val columnTitles = mutableListOf(
        "Group" to MarkdownTable.Align.Left,
        "Description" to MarkdownTable.Align.Left,
      )

      markdownTable {
        columnTitles.forEach {
          addColumn(
            title = it.first,
            alignment = it.second,
          )
        }

        dependencies.forEach {
          val description = if (it.description == bundle.description) {
            "not provided"
          } else {
            it.description ?: "not provided"
          }
          val name = "${bundle.name} ${it.name}".escapeAlias(".").lowercase()
          val link = "./${it.name.escapeAlias("-").lowercase()}/index.md"
          row {
            addValues(
              "[$name]($link)",
              description,
            )

            row.forEachIndexed { index, s ->
              if (s.isEmpty()) {
                row[index] = "-"
              }
            }
          }
        }
      }.also {
        append(it.build())
      }
    }
  }

  file.replaceContentsWithinRegion(
    region = RegionTag.GroupVersionOverview,
  ) {
    appendLine(
      """
      |## Version
      |
      |Refer to the table below for an overview of the current versions associated with each group.
      |
      """.trimMargin(),
    )

    appendContent {
      val columnTitles = mutableListOf(
        "Maven Group ID" to MarkdownTable.Align.Left,
        "Latest Update" to MarkdownTable.Align.Left,
        "Stable Release" to MarkdownTable.Align.Center,
      )
      val includeReleaseCandidate = bundle.documentationBuilder.includeReleaseCandidate
      val includeBetaRelease = bundle.documentationBuilder.includeBetaRelease
      val includeAlphaRelease = bundle.documentationBuilder.includeAlphaRelease

      if (includeReleaseCandidate) {
        columnTitles.add("Release Candidate" to MarkdownTable.Align.Center)
      }
      if (includeBetaRelease) {
        columnTitles.add("Beta Release" to MarkdownTable.Align.Center)
      }
      if (includeAlphaRelease) {
        columnTitles.add("Alpha Release" to MarkdownTable.Align.Center)
      }

      val stableIndex = columnTitles.indexOfFirst { it.first == "Stable Release" }
      val releaseCandidateIndex = columnTitles.indexOfFirst { it.first == "Release Candidate" }
      val betaIndex = columnTitles.indexOfFirst { it.first == "Beta Release" }
      val alphaIndex = columnTitles.indexOfFirst { it.first == "Alpha Release" }

      markdownTable {
        columnTitles.forEach {
          addColumn(
            title = it.first,
            alignment = it.second,
          )
        }

        dependencies.forEach { module ->
          val artifact = module.artifact
          val version = artifact.version
          val name = "${bundle.name} ${module.name}".escapeAlias(".").lowercase()
          val link = "./${module.name.escapeAlias("-").lowercase()}/index.md"
          val date = bundlesInfo.firstOrNull {
            it.module == artifact.module
          }?.date ?: 0
          row(true) {
            addValueAt(
              index = 0,
              value = "[$name]($link)",
            )
            addValueAt(
              index = 1,
              value = SimpleDateFormat("MMMM dd, yyyy").format(date * 1000),
            )
            addValueAt(
              index = when (version.flag) {
                ReleaseStatus.Stable -> stableIndex
                ReleaseStatus.Deprecated -> stableIndex
                ReleaseStatus.ReleaseCandidate -> releaseCandidateIndex
                ReleaseStatus.Beta -> betaIndex
                ReleaseStatus.Alpha -> alphaIndex
                else -> stableIndex
              },
              value = version.toString(),
            )
            row.forEachIndexed { index, s ->
              if (s.isEmpty()) {
                row[index] = "-"
              }
            }
          }
        }
      }.also {
        append(it.build())
      }
    }
  }

  if (bundle.ticketSystem != null) {
    file.replaceContentsWithinRegion(
      region = RegionTag.ReportIssueFeedback,
    ) {
      appendLine(
        """
        |## Feedback
        |
        |Your feedback helps make ${bundle.name.capitalized()} better. Let us know if you discover new issues or have
        |ideas for improving this library. Please take a look at the [existing issues on ${bundle.ticketSystem!!.system}](${bundle.ticketSystem!!.url})
        |for this library before you create a new one.
        |
        |[Create a new issue](${bundle.ticketSystem!!.url}/new){ .md-button }
        """.trimMargin(),
      )
      appendLine()
    }
  }
}
