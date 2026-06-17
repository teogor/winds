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

import dev.teogor.winds.api.model.DependencyBundle
import dev.teogor.winds.api.model.ModuleDependency
import dev.teogor.winds.api.model.ModuleDescriptor
import dev.teogor.winds.gradle.docs.utils.createBomNotes
import dev.teogor.winds.gradle.utils.escapeAlias
import dev.teogor.winds.ktx.file
import java.io.File

class BomDocsModuleHandler(
  private val bundle: ModuleDescriptor,
  private val dependencies: List<ModuleDescriptor>,
  private val projectDir: File,
  private val outputDir: File,
  private val buildOutputDir: File,
  private val forceDateUpdate: Boolean,
) : DocsModuleHandler(bundle, projectDir, outputDir, buildOutputDir, forceDateUpdate) {
  override fun updateDependencyBundles() {
    val fileName = artifact.completeName.escapeAlias("-")
    val inputFile = outputDir.resolve("assets/winds") file "$fileName.json"
    val outputFile = buildDocsOutputDir.resolve("assets/winds") file "$fileName.json"
    inputFile.copyTo(outputFile, true)

    val bundles = readDependencyBundlesFromFile(outputFile)
      .toMutableMap()

    val key = "${artifact.module}:${artifact.version}"
    val existingBundle = bundles[key]

    val updatedBundle = when {
      existingBundle == null -> {
        DependencyBundle(
          date = currentTimestamp,
          module = artifact.module,
          version = artifact.version,
          dependencies = dependencies.filter { it.publish }
            .map {
              ModuleDependency(
                it.artifact.module,
                it.artifact.version,
                currentTimestamp,
              )
            },
        )
      }

      forceDateUpdate -> {
        val existingDependencies = existingBundle.dependencies.toSet()
        val updatedDependencies = dependencies.filter { it.publish }
          .map {
            ModuleDependency(
              it.artifact.module,
              it.artifact.version,
              currentTimestamp,
            )
          }
          .map { newDependency ->
            if (existingDependencies.contains(newDependency)) {
              existingBundle.dependencies.find {
                it.module == newDependency.module && it.version == newDependency.version
              }!!.copy(
                date = currentTimestamp,
              )
            } else {
              newDependency
            }
          }

        existingBundle.copy(
          date = currentTimestamp,
          dependencies = updatedDependencies,
        )
      }

      else -> {
        val existingDependencies = existingBundle.dependencies
        val updatedDependencies = dependencies.filter { it.publish }
          .map {
            ModuleDependency(
              it.artifact.module,
              it.artifact.version,
              currentTimestamp,
            )
          }
          .map { newDependency ->
            val existingDependency = existingDependencies.firstOrNull {
              it.module == newDependency.module
            }
            if (existingDependency != null) {
              if (existingDependency.version != newDependency.version) {
                println("Version increased")
              }
              existingDependency.copy(version = newDependency.version)
            } else {
              newDependency
            }
          }

        existingBundle.copy(
          dependencies = updatedDependencies,
        )
      }
    }

    bundles[key] = updatedBundle

    writeDependencyBundleToFile(bundles.values.toList(), outputFile)

    // TODO error
    bundleInfo = bundles[key] ?: error("Something went wrong")
  }

  override fun createReleaseNotes() {
    createBomNotes(
      module = bundle,
      modules = dependencies,
      inputDir = outputDir.resolve("bom"),
      outputDir = buildDocsOutputDir.resolve("bom"),
      includeOwner = true,
      asModule = false,
    )
  }

  override fun createDependencyDocumentation() = Unit

  override fun createRootNotes() = Unit
}
