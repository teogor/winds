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
import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.api.docs.impl.factory.DocsModuleHandlerFactory
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.gradle.tasks.BaseGeneratorTask
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Abstract base class for tasks that generate documentation for a project.
 *
 * @constructor Creates a DocsGeneratorTask with a descriptive label.
 */
abstract class DocsGeneratorTask : BaseGeneratorTask(
  description = "Generates documentation for a project.",
) {

  /**
   * The DocsGenerator instance responsible for generating documentation.
   */
  private lateinit var docsGenerator: DocsGenerator

  /**
   * The root directory of the project for which documentation is being generated.
   */
  private lateinit var projectDir: File

  /**
   * A mutable list of ModuleInfo objects representing project dependencies.
   */
  private val libraries = mutableListOf<ModuleMetadata>()

  /**
   * The main action of the task, executed when the task is run.
   *
   * 1. Creates the appropriate DocsModuleHandler based on project dependencies.
   * 2. Manages dependencies using the handler.
   * 3. Writes release notes using the handler.
   * 4. Optionally updates MkDocs configuration if enabled.
   */
  @TaskAction
  override fun action() {
    val docsModuleHandlerFactory = DocsModuleHandlerFactory()
    libraries.forEach {
      println("library - ${it.artifactDescriptor?.coordinates}")
    }
    return
    // docsModuleHandler = docsModuleHandlerFactory.createHandler(
    //   projectDir = projectDir,
    //   docsGenerator = docsGenerator,
    //   libraries = libraries,
    // )
    //
    // docsModuleHandler.manageDependencies()
    // docsModuleHandler.writeReleaseNotes()
    //
    // if (docsGenerator.mkdocsEnabled) {
    //   docsModuleHandler.updateMkDocs()
    // }
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
  fun addLibrary(data: ModuleMetadata) {
    if (libraries.firstOrNull {
        it.artifactDescriptor!!.coordinates == data.artifactDescriptor!!.coordinates
      } == null
    ) {
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
