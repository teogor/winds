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

package dev.teogor.winds.gradle.tasks

import dev.teogor.winds.api.model.ModuleDescriptor
import dev.teogor.winds.gradle.docs.factory.DocsModuleHandlerFactory
import dev.teogor.winds.gradle.utils.isLibrary
import dev.teogor.winds.ktx.groupModulesByDependency
import dev.teogor.winds.ktx.isRootProject
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class ReleaseNotesTask : DefaultTask() {

  init {
    group = "dev.teogor.winds"
    description = """
    |Generates a file containing release notes for the module, summarizing significant changes and updates for the current or latest release.
    """.trimMargin()
  }

  private val isRootPrj: Boolean = project.isRootProject()

  private val projectDir: File = project.projectDir

  @OutputDirectory
  var outputDir: File = project.rootDir.resolve("docs")

  @OutputDirectory
  var buildOutputDir: File = project.buildDir.resolve("intermediates/winds")

  @OutputDirectory
  var buildDocsOutputDir: File = buildOutputDir.resolve("docs")

  private val docsModuleHandlerFactory: DocsModuleHandlerFactory = DocsModuleHandlerFactory()

  @Input
  var forceDateUpdate: Boolean = false
    internal set

  fun setForceDateUpdate(force: Boolean) {
    this.forceDateUpdate = force
  }

  @TaskAction
  fun action() {
    if (!isRootPrj) {
      return
    }

    buildOutputDir.mkdirs()
    outputDir.mkdirs()

    /**
     * ToDo: Handle this scenario at the module level.
     *  In some project structures, we may encounter the following configurations:
     *  1. Root
     *     - BOM (Bill of Materials)
     *     - Module
     *       - Dependency-1
     *       - Dependency-2
     *  2. Root
     *     - Module
     *       - BOM
     *       - Dependency-1
     *       - Dependency-2
     *     - Module-2
     *       - BOM
     *       - Dependency-1
     *       - Dependency-2
     *  In either case, where BOM (Bill of Materials) is present either at the root or module level,
     *  handling this structure appropriately is essential.
     */
    val hasBom = modules.any { it.isBom }
    if (modules.count { it.isBom } > 1) {
      throw IllegalStateException("Currently, we support only up to 1 BOM per project.")
    }
    val baseModules = modules.groupModulesByDependency()
    val modulesCount = baseModules.size

    if (hasBom) {
      baseModules.values.flatten().first { it.isBom }.let { bomLibrary ->
        val moduleHandler = docsModuleHandlerFactory.createHandler(
          bundle = bomLibrary,
          dependencies = modules.filter { it.isLibrary() },
          projectDir = projectDir,
          outputDir = outputDir,
          buildOutputDir = buildOutputDir,
          forceDateUpdate = forceDateUpdate,
        )

        moduleHandler.updateDependencyBundles()
        moduleHandler.createReleaseNotes()
        moduleHandler.createDependencyDocumentation()
      }
      baseModules.filter { (_, modules) ->
        modules.any { it.publish } && modules.none { it.isBom }
      }.forEach { (bundle, dependencies) ->
        val moduleHandler = docsModuleHandlerFactory.createHandler(
          bundle = bundle,
          dependencies = dependencies,
          projectDir = projectDir,
          outputDir = outputDir,
          buildOutputDir = buildOutputDir,
          forceDateUpdate = forceDateUpdate,
        )
        moduleHandler.updateDependencyBundles()
        moduleHandler.createReleaseNotes()
        moduleHandler.createDependencyDocumentation()
      }

      val bundles = baseModules.filter {
        it.key.dependencies.isNotEmpty()
      }.map { it.key }
      val bundleRootNullable = bundles.map {
        it.dependencies
      }.flatten().firstOrNull()?.let { artifact ->
        modules.first { it.artifact == artifact }
      }
      val bundleRoot = bundleRootNullable ?: error("Failed to locate the root module")
      val bundleRootHandler = docsModuleHandlerFactory.createHandler(
        bundle = bundleRoot,
        dependencies = bundles,
        projectDir = projectDir,
        outputDir = outputDir,
        buildOutputDir = buildOutputDir,
        forceDateUpdate = forceDateUpdate,
      )
      bundleRootHandler.createRootNotes()
    } else if (modulesCount == 1) {
      val moduleHandler = docsModuleHandlerFactory.createHandler(
        bundle = baseModules.keys.first(),
        dependencies = baseModules.values.first(),
        projectDir = projectDir,
        outputDir = outputDir,
        buildOutputDir = buildOutputDir,
        forceDateUpdate = forceDateUpdate,
      )

      moduleHandler.updateDependencyBundles()
      moduleHandler.createReleaseNotes()
      moduleHandler.createDependencyDocumentation()
    } else {
      baseModules.filter { (_, modules) ->
        modules.any { it.publish }
      }.forEach { (bundle, dependencies) ->
        val moduleHandler = docsModuleHandlerFactory.createHandler(
          bundle = bundle,
          dependencies = dependencies,
          projectDir = projectDir,
          outputDir = outputDir,
          buildOutputDir = buildOutputDir,
          forceDateUpdate = forceDateUpdate,
        )
        moduleHandler.updateDependencyBundles()
        moduleHandler.createReleaseNotes()
        moduleHandler.createDependencyDocumentation()
      }
    }

    deleteEmptyDirectories(buildDocsOutputDir)
    buildDocsOutputDir.copyRecursively(
      target = outputDir,
      overwrite = true,
      onError = { _, _ ->
        OnErrorAction.SKIP
      },
    )
  }

  private fun deleteEmptyDirectories(dir: File) {
    if (!dir.isDirectory) return // Skip non-directories

    val contents = dir.listFiles()?.toList() ?: emptyList()

    // Check if directory is empty or only contains hidden files
    if (contents.isEmpty() || contents.all { it.isHidden }) {
      dir.delete()
    } else {
      // Recursively delete empty directories within subdirectories
      contents.forEach { subDir -> deleteEmptyDirectories(subDir) }
    }
  }

  private val modules = mutableListOf<ModuleDescriptor>()

  fun addModuleDescriptor(moduleDescriptor: ModuleDescriptor) {
    modules.add(moduleDescriptor)
  }
}
