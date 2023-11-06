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

import com.google.gson.Gson
import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.model.BomInfo
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.gradle.tasks.BaseGeneratorTask
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.provideDelegate
import java.io.File
import java.time.Instant
import java.time.ZoneOffset
import kotlin.random.Random

abstract class DocsGeneratorTask : BaseGeneratorTask(
  description = "Generates documentation for a project.",
) {

  private lateinit var docsGenerator: DocsGenerator
  private val libraries = mutableListOf<ModuleInfo>()

  private fun MutableList<ModuleInfo>.bom() = this.firstOrNull {
    it.isBoM
  }

  private val docsFolder = root directory "docs"

  private val resFolder = root directory ".resources"

  private val bomFolder = docsFolder directory "bom"

  private val bomResFolder = resFolder directory "bom"

  private val alphaEmoji = "\uD83E\uDDEA"
  private val betaEmoji = "\uD83D\uDEE0\uFE0F"
  private val deprecatedEmoji = "\uD83D\uDEA7"

  private val bomLibrary by lazy {
    libraries.bom() ?: error("attempting to get bom when it is not available")
  }

  private val hasBoM by lazy { libraries.bom() != null }

  // todo be able to use without calling toString()
  private val bomResLibraryInfo by lazy { bomResFolder directory bomLibrary.version.toString() }

  private val bomLibraryInfo by lazy { bomFolder directory bomLibrary.version.toString() }

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
    writeModulesDocs()

    if (hasBoM) {
      writeLibrariesVersionCatalog()
      writeBoMVersions()
      writeBoMVariants()
    }
  }

  private fun writeLibrariesVersionCatalog() {
    val projectName = docsGenerator.name
    val projectIdentifier = docsGenerator.identifier
    val lbsVersionCatalogContent = buildString {
      appendLine("## Libraries Implementation Version Catalog")
      appendLine()
      appendLine(
        "This catalog provides the implementation details of $projectName libraries, including Build of Materials (BoM) and individual libraries, in TOML format.",
      )
      appendLine()
      appendLine("```toml")
      appendLine("[versions]")
      appendLine("$projectIdentifier-bom = \"${bomLibrary.version}\"")
      appendLine()

      appendLine("[libraries]")
      appendLine("# $projectName BoM")
      libraries
        .filter { it.canBePublished && it.isBoM }
        .forEach { library ->
          appendLine(
            "$projectIdentifier-${
              library.name.lowercase().replace(" ", "-")
            } = { group = \"${library.groupId}\", name = \"${library.artifactId}\", version.ref = \"$projectIdentifier-bom\" }",
          )
        }

      appendLine("# $projectName Libraries")
      libraries
        .filter { it.canBePublished && !it.isBoM }
        .forEach { library ->
          appendLine(
            "$projectIdentifier-${
              library.name.lowercase().replace(" ", "-")
            } = { group = \"${library.groupId}\", name = \"${library.artifactId}\" }",
          )
        }
      appendLine("```")
      appendLine()
      appendLine("## Libraries Implementation build.gradle.kts File")
      appendLine()
      appendLine(
        "This section presents the implementation dependencies for $projectName libraries in a Kotlin build.gradle.kts file format.",
      )
      appendLine()
      appendLine("```kotlin")
      appendLine("dependencies {")
      appendLine("  // ${docsGenerator.name} BoM")
      libraries
        .filter { it.canBePublished && it.isBoM }
        .forEach { library ->
          appendLine(
            "  implementation(platform(libs.$projectIdentifier.${
              library.name.lowercase().replace(" ", ".")
            }))",
          )
        }

      appendLine("  // ${docsGenerator.name} Libraries")
      libraries
        .filter { it.canBePublished && !it.isBoM }
        .forEach { library ->
          appendLine(
            "  implementation(libs.$projectIdentifier.${
              library.name.lowercase().replace(" ", ".").replace("-", ".")
            })",
          )
        }
      appendLine("}")
      appendLine("```")
      appendLine()
    }
    val filePath = "version-catalog.md"
    docsFolder file filePath write {
      write(lbsVersionCatalogContent)
    }
  }

  private fun writeBoMVersions() {
    writeBoMVersionsJson()
    writeBomVersionsMd()
  }

  @OptIn(ExperimentalSerializationApi::class)
  private fun writeBoMVersionsJson() {
    val bomInfoList = getBomVersions().toMutableList()
    val hasVersion =
      bomInfoList.firstOrNull { it.version.toString() == bomLibrary.version.toString() } != null
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

  private fun writeBomVersionsMd() {
    val content = StringBuilder()
    val bomInfoList = getBomVersions()

    content.appendLine("## ${bomLibrary.completeName} (Bill of Materials)")
    content.appendLine()
    content.appendLine(
      """
      The BoM (Bill of Materials) is the central hub for managing library versions within the ${docsGenerator.name} project.
      It enables you to effortlessly keep track of the latest versions of key components and dependencies.
      """.trimIndent(),
    )
    content.appendLine()
    content.appendLine(
      """
      ### Latest Version

      Here is how to declare dependencies using the latest version `${bomLibrary.version}`:

      ```kt
      dependencies {
        // Import the BoM for the ${docsGenerator.name} platform using the latest version
        implementation(platform("${bomLibrary.gradleDependency}:${bomLibrary.version}"))
      }
      ```
      """.trimIndent(),
    )
    content.appendLine()
    content.appendLine("## BoM Versions (Bill of Materials)")
    content.appendLine()
    content.appendLine("Below is a list of the latest versions of the BOM:")
    content.appendLine()
    content.appendLine("| Version | Release Notes | Release Date |")
    content.appendLine("| ------- | ------------- | ------------ |")
    for (bomInfo in bomInfoList) {
      content.appendLine(
        "| ${bomInfo.version} | [changelog \uD83D\uDD17](/docs/bom/${bomInfo.version}/bom-version-${bomInfo.version}.md) | ${bomInfo.dateFormatted} |",
      )
    }
    content.appendLine()
    content.appendLine(
      """
      The **Bill of Materials (BoM)** serves as a cornerstone for maintaining synchronization among various libraries and components in your project. By centralizing version management, it significantly reduces compatibility issues and streamlines the entire dependency management process.

      ### Advantages of Using the BoM

      - **Synchronization:** The BoM guarantees that all libraries within your project are aligned, ensuring seamless compatibility.
      - **Staying Current:** By adopting the BoM, you effortlessly stay updated with the latest advancements within the ever-evolving ${docsGenerator.name} ecosystem.

      ### Explore Further

      For in-depth insights, updates, and comprehensive information regarding ${docsGenerator.name}, please consult the official [${docsGenerator.name} documentation](/docs/). There, you'll discover a wealth of resources to enhance your ${docsGenerator.name} development journey.
      """.trimIndent(),
    )
    content.appendLine()

    val filePath = "versions.md"
    bomFolder file filePath write {
      write(content.toString())
    }
  }

  private fun writeBoMVariants() {
    writeBoMVariantsJson()
    writeBoMVariantsMd()
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

  private fun writeBoMVariantsMd() {
    val projectName = docsGenerator.name
    val projectIdentifier = docsGenerator.identifier
    val filePath = "bom-version-${bomLibrary.version}.md"
    bomLibraryInfo file filePath write {
      val random = Random(bomLibrary.version.hashCode())
      val randomLibs = libraries.filter { !it.isBoM && it.canBePublished }.shuffled(random).take(3)
      val libraryNames = randomLibs.joinToString("\n            ") {
        """// ${it.names.joinToString(" ")}
            implementation("${it.gradleDependency}")"""
      }
      libraries.bom()?.let {
        val textToAdd = """
          # $projectName BoM v${it.version} (Bill of Materials)

          The $projectName BoM (Bill of Materials) enables you to manage all your $projectName library versions by specifying only one version — the BoM's version.

          When you use the $projectName BoM in your app, the BoM automatically pulls in the individual library versions mapped to the BoM's version. All the individual library versions will be compatible. When you update the BoM's version in your app, all the $projectName libraries that you use in your app will update to the versions mapped to that BoM version.

          To learn which $projectName library versions are mapped to a specific BoM version, check out the release notes for that BoM version. If you need to compare the library versions mapped to one BoM version compared to another BoM version, use the comparison widget below.

          Learn more about [Gradle's support for BoM platforms](https://docs.gradle.org/4.6-rc-1/userguide/managing_transitive_dependencies.html#sec:bom_import).

          Here's how to use the $projectName BoM to declare dependencies in your module (app-level) Gradle file (usually app/build.gradle.kts). When using the BoM, you don't specify individual library versions in the dependency lines.

          ```kt
          dependencies {
            // Import the BoM for the $projectName platform
            implementation(platform("${it.coordinates}"))

            // Declare the dependencies for the desired $projectName products
            // without specifying versions. For example, declare:
            $libraryNames
          }
          ```
        """.trimIndent()

        write(textToAdd)
        newLine()
      }

      val moduleContent = buildString {
        appendLine()
        appendLine("## Latest SDK versions")
        appendLine()
        appendLine("| Status | Service or Product | Gradle dependency | Latest version |")
        appendLine("| ------ | ------------------ | ----------------- | -------------- |")

        val previousBomDependencies = if (previousBomVersion != null) {
          getDependenciesByVersion(previousBomVersion!!.version)
        } else {
          emptyList()
        }
        libraries
          .filter { !it.isBoM && it.canBePublished }
          .forEach { library ->
            val emoji = when {
              library.version.isAlphaRelease -> alphaEmoji
              library.version.isBetaRelease -> betaEmoji
              library.version.isDeprecated -> deprecatedEmoji
              else -> ""
            }
            val previousVersionData = previousBomDependencies.firstOrNull {
              it.artifactId == library.artifactId
            }
            val previousVersion = previousVersionData?.version ?: "N/A"
            val versionData = if (previousVersionData?.version != null) {
              val previousVersionDataD = previousVersionData.version
              if (previousVersionDataD != library.version) {
                "$previousVersionDataD -> ${library.version}"
              } else {
                library.version
              }
            } else {
              library.version
            }
            appendLine("| $emoji | [${library.name}](${library.localPath}) | ${library.gradleDependency} | ${library.version} |")
          }
        appendLine()
        appendLine(
          """
          ### Explore Further

          For the latest updates, in-depth documentation, and a comprehensive overview of the $projectName ecosystem, visit the official [$projectName documentation](/docs/). It's your gateway to a wealth of resources and insights that will elevate your $projectName development journey.

          Stay informed, stay current, and embrace the full potential of $projectName.
          """.trimIndent(),
        )
      }

      write(moduleContent)
    }
  }

  private fun getDependenciesByVersion(version: Version): List<ModuleInfo> {
    val filePath = "dependencies-$version.json"
    val bomMappingFile = bomResFolder directory version.toString() file filePath

    if (!bomMappingFile.exists()) {
      return emptyList()
    }

    return try {
      val jsonString = bomMappingFile.readText()
      val gson = Gson()
      val libraryInfoList = gson.fromJson(jsonString, Array<ModuleInfo>::class.java)
      return libraryInfoList.toList()
    } catch (e: Exception) {
      emptyList()
    }
  }

  private fun writeModulesDocs() {
    libraries
      .filter { !it.isBoM }
      .filter { it.canBePublished }
      .groupBy { it.module }
      .forEach { (module, libraries) ->
        val content = StringBuilder()
        content.appendLine("## ${docsGenerator.name} $module")
        content.appendLine()
        content.appendLine("| Status | Library | Gradle dependency |")
        content.appendLine("| ------ | ------- | ----------------- |")
        for (library in libraries) {
          val emoji = when {
            library.version.isAlphaRelease -> alphaEmoji
            library.version.isBetaRelease -> betaEmoji
            library.version.isDeprecated -> deprecatedEmoji
            else -> ""
          }
          val link = "#implementation ${library.name}".lowercase().replace(" ", "-")
          content.appendLine(
            "| $emoji | [${library.name}](${library.localPath}) | [${library.coordinates}]($link) |",
          )
        }
        content.appendLine()
        content.appendLine(
          """
          By referring to the [BoM documentation](/docs/bom/versions.md), you can learn how to integrate the BoM into your project and benefit from this hassle-free approach to library version management. It's a powerful tool for staying up-to-date with the latest ${docsGenerator.name} library versions and seamlessly integrating them into your projects.
          """.trimIndent(),
        )
        content.appendLine()
        content.appendLine()
        for (library in libraries) {
          content.appendLine(
            """
            ### Implementation ${library.displayName}

            To use ${library.name} in your project, add the following dependency to your module-level Gradle file (usually `app/build.gradle.kts`):

            ```kotlin
            implementation("${library.coordinates}")
            ```

            #### Gradle Dependency

            - **Group ID:** `${library.groupId}`
            - **Artifact ID:** `${library.artifactId}`
            - **Version:** `${library.version}` (not required when using [BoM](/docs/bom/versions.md))
            """.trimIndent(),
          )
          content.appendLine()
        }
        content.appendLine()

        val filePath = "${docsGenerator.identifier}-$module.md"
        docsFolder file filePath write {
          write(content.toString())
        }
      }
  }

  fun addLibrary(data: ModuleInfo) {
    libraries.add(data)
  }

  fun provideDocsGenerator(docsGenerator: DocsGenerator) {
    this.docsGenerator = docsGenerator
  }
}
