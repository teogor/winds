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

package dev.teogor.winds.gradle

import dev.teogor.winds.api.model.ModuleDescriptor
import dev.teogor.winds.api.model.Path
import dev.teogor.winds.common.MarkdownTable
import dev.teogor.winds.gradle.docs.factory.DocsModuleHandlerFactory
import dev.teogor.winds.ktx.getEmoji
import dev.teogor.winds.ktx.getShowcaseCoordinates
import dev.teogor.winds.ktx.groupByModule
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.configurationcache.extensions.capitalized
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern
import kotlin.random.Random

abstract class ReleaseNotesTask : DefaultTask() {

  init {
    group = "dev.teogor.winds"
    description = "Generates a file containing release notes for the module, summarizing significant changes and updates for the current or latest release."
  }

  @OutputDirectory
  var outputDir: File = project.rootDir.resolve("kDocs")

  @OutputDirectory
  var buildOutputDir: File = project.buildDir.resolve("intermediates/winds")

  @OutputDirectory
  var buildDocsDir: File = buildOutputDir.resolve("docs")

  @OutputDirectory
  var buildReleasesDir: File = buildDocsDir.resolve("releases")

  @OutputDirectory
  var buildTrainingDir: File = buildDocsDir.resolve("training")

  private val docsModuleHandlerFactory: DocsModuleHandlerFactory = DocsModuleHandlerFactory()

  private inline fun file(
    outputDir: File,
    name: String,
    crossinline content: StringBuilder.() -> Unit,
  ) {
    outputDir.mkdirs()
    val filePath = File(outputDir, name)
    val newContent = buildString(content)
    if (filePath.exists()) {
      val text = filePath.readText()
      val linesWithIndices = text.lines().withIndex().toList()
      val pattern = Pattern.compile("""\[//]: #""")
      val firstOccurrenceIndex = linesWithIndices.indexOfFirst {
        pattern.matcher(it.value).find()
      } + 1
      filePath.writeText(
        buildString {
          if (firstOccurrenceIndex != -1) {
            appendLine(newContent)
            appendLine(
              linesWithIndices.subList(firstOccurrenceIndex + 1, linesWithIndices.size)
                .joinToString("\n") { it.value },
            )
          } else {
            appendLine(newContent)
          }
        },
      )
    } else {
      filePath.writeText(
        newContent,
      )
    }
  }

  private fun String.appendTo(writer: StringBuilder) {
    writer.appendLine(this)
    writer.appendLine()
  }

  private inline fun StringBuilder.appendContent(crossinline content: StringBuilder.() -> Unit) {
    content()
    appendLine()
  }

  private val module: ModuleDescriptor
    get() = modules.first()

  private fun generateRandomDates(count: Int): List<String> {
    val startDate = Date(1669824000000L) // 2023-11-29 00:00:00 UTC
    val endDate = Date() // Current date

    val randomDates = mutableListOf<Date>()
    while (randomDates.size < count) {
      val randomDate =
        Date((startDate.time + Math.random() * (endDate.time - startDate.time)).toLong())
      if (!randomDates.contains(randomDate)) { // Avoid duplicates
        randomDates.add(randomDate)
      }
    }

    val dateFormat = SimpleDateFormat("MMMM dd, yyyy") // Format to "Month dd, yyyy"
    return randomDates.sortedDescending().map { dateFormat.format(it) }
  }

  private fun generateRowData(i: Int, updateDates: List<String>, columnTitles: Int): List<String> {
    val latestUpdate = updateDates[i]

    // Generate a random number between 1 and 4
    val randomValue = Random.nextInt(1, 5)
    val randomValue2 = Random.nextInt(10, 99)
    val randomValue3 = Random.nextInt(100, 999)

    val values = mutableListOf(latestUpdate)
    // Replace remaining placeholders based on the random value
    repeat(columnTitles - 1) {
      values.add(if (it == randomValue - 1) "v$randomValue.$randomValue2.$randomValue3" else "-")
    }

    return values
  }

  @TaskAction
  fun action() {
    buildOutputDir.mkdirs()
    outputDir.mkdirs()

    file(
      outputDir = buildOutputDir,
      name = "releases.md",
    ) {
      // Header
      appendLine("# ${module.completeName}")
      appendLine()
      buildString {
        append("Learn more: ")
        append("**[User Guide](user-guide.md)**")
        append(" and ")
        append("**[Code Samples](code-samples.md)**")
      }.appendTo(this)
      module.description?.appendTo(this)

      // API Reference
      if (module.documentationBuilder.createApiReference) {
        appendContent {
          appendLine("API Reference<br>")
          val htmlPath = module.documentationBuilder.htmlPath
          fun formatDependency(dependency: String, path: Path): String {
            return "[`$dependency`](${Path.from(htmlPath, path.value).value})<br>"
          }
          appendLine(formatDependency(module.artifact.module, module.path))
          modules.groupByModule()
            .filter { it.key == module.name }
            .map { it.value }
            .flatten()
            .forEach {
              appendLine(formatDependency(it.artifact.module, it.path))
            }
          // todo
          //  appendLine("(See the API reference docs for all compose packages)")
        }
      }

      // Releases
      if (module.documentationBuilder.generateReleaseTable) {
        appendContent {
          val columnTitles = mutableListOf("Latest Update", "Stable Release")

          if (module.documentationBuilder.includeReleaseCandidate) {
            columnTitles.add("Release Candidate")
          }
          if (module.documentationBuilder.includeBetaRelease) {
            columnTitles.add("Beta Release")
          }
          if (module.documentationBuilder.includeAlphaRelease) {
            columnTitles.add("Alpha Release")
          }

          val rowsCount = 10
          val updateDates = generateRandomDates(rowsCount)
          val rows = List(rowsCount) { generateRowData(it, updateDates, columnTitles.size) }

          val table = MarkdownTable.withTitles(*columnTitles.toTypedArray())
          table.addRows(*rows.toTypedArray())
          appendLine(table.build())
        }
      }

      // Declaring dependencies
      appendContent {
        appendLine("## Declaring dependencies")
        appendLine()
        appendLine(
          """
          To add a dependency on ${module.name.capitalized()}, you must add the Central Maven repository to your project.
          Read [Central](https://central.sonatype.org/consume/consume-gradle/)'s Maven repository for more information.
          """.trimIndent(),
        )
        appendLine()
        appendLine(
          """
          Add the dependencies for the artifacts you need in the `build.gradle` file for your app or module:
          """.trimIndent(),
        )
        appendLine()
        appendLine("---")
        appendLine("> TODO add implementation for groovy/kotlin")
        appendLine("---")
        appendLine()
        appendLine(
          "For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).",
        )
        appendLine()
        appendLine(
          "For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).",
        )
        appendLine()
        appendLine(
          "For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).",
        )
      }

      // Feedback
      if (module.ticketSystem != null) {
        appendContent {
          appendLine("## Feedback")
          appendLine()
          appendLine(
            """
          Your feedback helps make ${module.name.capitalized()} better. Let us know if you discover new issues or have
          ideas for improving this library. Please take a look at the [existing issues on ${module.ticketSystem!!.system}](${module.ticketSystem!!.url})
          for this library before you create a new one.
            """.trimIndent(),
          )
          appendLine()
          appendLine("[Create a new issue](${module.ticketSystem!!.url}/new){ .md-button }")
        }
      }

      // Releases
      appendContent {
        appendLine(
          "[`${module.artifact.getShowcaseCoordinates()}`](${module.scm?.repositoryUrl}/releases/${module.artifact.version}) is released.",
        )
      }

      appendLine(
        "[//]: # (Do not remove this line or edit the content above it. Automatically generated.)",
      )
    }

    val gbm = modules.groupByModule()
    gbm
      .filter { it.key != null && it.key == module.name }
      .map { it.value }
      .flatten()
      .let { elements ->
        file(
          outputDir = buildReleasesDir,
          name = "${module.name.lowercase()}.md",
        ) {
          elements.forEach { element ->
            appendLine(element.artifact.coordinates)
            val isGroupModule = gbm.any { it.key == element.name }
            if (isGroupModule) {
            }
          }
        }
      }
    modules.groupByModule()
      .filter { it.key != null && it.key != module.name }
      .map { it.key!! to it.value }
      .forEach { (submodule, elements) ->
        file(
          outputDir = buildReleasesDir,
          name = "${module.name.lowercase()}-$submodule.md",
        ) {
          elements.forEach { element ->
            appendLine(element.artifact.coordinates)
          }
        }
      }

    val filePath = File(buildOutputDir, "metadata.md")
    filePath.writeText(
      buildString {
        val hasBom = modules.any { it.isBom }
        if (hasBom) {
          appendLine("## BoM:")
          modules.filter { it.isBom }.forEach {
            appendLine(it.path.value)
            appendLine(it.path.asPartialLocalPath())
            appendLine(it.path.absolutePath)
          }
        }
        appendLine("## Metadata Grouped:")
        modules.groupByModule()
          .forEach { (parentName, groupedSpecs) ->
            val groupCount = groupedSpecs.size
            appendLine("### ${parentName?.capitalized()}")
            appendLine("### Grouped Dependencies:")
            groupedSpecs.forEachIndexed { index, value ->
              val moduleInfo = buildString {
                append(
                  "${value.completeName}: `${value.artifact.coordinates}` ${value.artifact.version.getEmoji()}",
                )
                if (!value.publish) {
                  append(" --local")
                }
              }
              appendLine("${index + 1}. $moduleInfo")
            }
          }
      },
    )

    buildOutputDir.copyRecursively(
      target = outputDir,
      overwrite = true,
      onError = { _, _ ->
        OnErrorAction.SKIP
      },
    )
  }

  private val modules = mutableListOf<ModuleDescriptor>()

  fun addModuleDescriptor(moduleDescriptor: ModuleDescriptor) {
    modules.add(moduleDescriptor)
  }
}
