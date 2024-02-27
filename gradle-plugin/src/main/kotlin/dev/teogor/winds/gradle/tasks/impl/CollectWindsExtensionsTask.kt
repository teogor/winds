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

package dev.teogor.winds.gradle.tasks.impl

import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.api.model.ModuleMetadataDefault
import dev.teogor.winds.api.model.Path
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register
import java.io.File

abstract class CollectWindsExtensionsTask : DefaultTask() {
  private val buildDir: File = project.buildDir
  private val outputFile: File
    get() = File(buildDir, "intermediates/winds/metadata.txt").apply {
      if (!exists()) {
        parentFile.mkdirs()
        createNewFile()
      }
    }

  @TaskAction
  fun customAction() {
    childMetadata.forEach {
      println("customAction - ${it.artifactDescriptor?.coordinates}")
    }
    childMetadata.map {
      val dependencySpec = it.artifactDescriptor!!
      ModuleMetadataDefault(
        completeName = dependencySpec.completeName,
        name = dependencySpec.name,
        artifactId = dependencySpec.artifactId,
        canBePublished = true,
        version = dependencySpec.version,
      )
    }
    val dependencySpec = moduleMetadata?.artifactDescriptor

    outputFile.bufferedWriter().use { writer ->
      childMetadata.sortedBy {
        it.artifactDescriptor?.coordinates
      }.forEach {
        writer.write(it.toString())
        writer.newLine()
      }
      writer.write("Module Metadata $pathEscaped")
      writer.newLine()
      writer.write("Artifact Id = '${dependencySpec?.artifactId}'")
      writer.newLine()
      writer.write("Dependencies")
      writer.newLine()
      childMetadata.sortedBy {
        it.artifactDescriptor?.coordinates
      }.forEach {
        writer.write(
          "group '${it.artifactDescriptor?.group}' artifactId '${it.artifactDescriptor?.artifactId}' version '${it.artifactDescriptor?.version}'",
        )
        writer.newLine()
      }
      writer.newLine()
    }
  }

  private var moduleMetadata: ModuleMetadata? = null
  fun setModuleMetadata(moduleMetadata: ModuleMetadata) {
    this.moduleMetadata = moduleMetadata
  }

  private val childMetadata: MutableList<ModuleMetadata> = mutableListOf()

  fun addChildMetadata(vararg moduleMetadata: ModuleMetadata) {
    childMetadata.addAll(moduleMetadata)
  }

  @Internal
  fun getChildMetadata() = childMetadata.toList()
  fun propagateChildMetadata(
    block: ModuleMetadata.() -> Unit,
  ) {
    childMetadata.forEach {
      block(it)
    }
  }

  private lateinit var prjPath: Path
  private var projectPath: String? = null
  private val pathEscaped: String
    get() = prjPath.absolutePath

  fun setProjectPath(projectPath: String) {
    this.projectPath = projectPath
    prjPath = Path(projectPath)
  }

  @Internal
  fun getProjectPath() = projectPath
}

val collectWindsExtensionsTaskName = "collectWindsExtensions"

fun Project.configureCollectWindsExtensionsTask(): CollectWindsExtensionsTask {
  val projectPath = path
  return registerCollectWindsExtensionsTask {
    setProjectPath(projectPath)
  }
}

inline fun Project.registerCollectWindsExtensionsTask(
  crossinline block: CollectWindsExtensionsTask.() -> Unit = {},
): CollectWindsExtensionsTask {
  return tasks.register(collectWindsExtensionsTaskName, CollectWindsExtensionsTask::class).get()
    .apply(block)
}

fun Project?.getCollectWindsExtensionsTask(): CollectWindsExtensionsTask {
  return this?.tasks?.findByName(collectWindsExtensionsTaskName) as? CollectWindsExtensionsTask
    ?: error("Not Found")
}

inline fun Project?.getCollectWindsExtensionsTask(
  crossinline block: CollectWindsExtensionsTask.(Project) -> Unit = {},
) {
  this?.tasks?.findByName(collectWindsExtensionsTaskName)?.let {
    if (it is CollectWindsExtensionsTask) {
      block(it, this)
    }
  }
}

inline fun Project.parentDependsOnCollectWindsExtensionsTask(
  crossinline block: CollectWindsExtensionsTask.() -> Unit = {},
) {
  val parentPath = path
  parent?.getCollectWindsExtensionsTask {
    dependsOn("$parentPath:$collectWindsExtensionsTaskName")
    block(this)
  }
}
