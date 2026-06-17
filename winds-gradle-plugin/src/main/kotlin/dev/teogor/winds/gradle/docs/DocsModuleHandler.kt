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

import dev.teogor.winds.api.ArtifactDescriptor
import dev.teogor.winds.api.model.DependencyBundle
import dev.teogor.winds.api.model.ModuleDescriptor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.Instant

abstract class DocsModuleHandler(
  private val bundle: ModuleDescriptor,
  private val projectDir: File,
  private val outputDir: File,
  private val buildOutputDir: File,
  private val forceDateUpdate: Boolean,
) {
  // region Properties
  protected val currentTimestamp: Long = Instant.now().epochSecond
  protected val artifact: ArtifactDescriptor = bundle.artifact
  protected var buildDocsOutputDir: File = buildOutputDir.resolve("docs")
  protected lateinit var bundleInfo: DependencyBundle
  // endregion Properties

  // region Abstract method
  abstract fun updateDependencyBundles()

  abstract fun createReleaseNotes()

  abstract fun createDependencyDocumentation()

  abstract fun createRootNotes()
  // endregion Abstract method

  // region Protected methods
  protected fun readDependencyBundlesFromFile(
    sourceFile: File,
  ): Map<String, DependencyBundle> = if (sourceFile.exists()) {
    val jsonString = sourceFile.readText()
    try {
      Json.decodeFromString<List<DependencyBundle>>(jsonString)
        .associateBy { "${it.module}:${it.version}" }
        .mapValues { (_, bundle) ->
          bundle.copy(
            dependencies = bundle.dependencies.sortedByDescending {
              it.date
            },
          )
        }
    } catch (_: Exception) {
      emptyMap()
    }
  } else {
    emptyMap()
  }

  @OptIn(ExperimentalSerializationApi::class)
  protected fun writeDependencyBundleToFile(
    dependencyBundles: List<DependencyBundle>,
    destinationFile: File,
  ) {
    val json = Json {
      prettyPrint = true
      prettyPrintIndent = "  "
    }
    dependencyBundles.map {
      it.copy(
        dependencies = it.dependencies.sortedBy {
          it.date
        },
      )
    }.also {
      val jsonString = json.encodeToString(it)
      destinationFile.writeText(jsonString)
    }
  }
  // endregion Protected methods
}
