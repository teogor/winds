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

package dev.teogor.winds.gradle.tasks

import dev.teogor.winds.api.docs.DocsModuleHandler
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File

abstract class BaseGeneratorTask(
  description: String,
) : DefaultTask() {

  init {
    group = "dev.teogor"
    this.description = description
  }

  @get:Internal
  protected val root = File("")

  @get:Internal
  abstract var docsModuleHandler: DocsModuleHandler

  infix fun File.directory(path: String): File {
    return if (this == root) {
      directoryImpl("${project.projectDir.path}/$path")
    } else {
      directoryImpl("$this/$path")
    }
  }

  infix fun File.file(path: String): File {
    return if (this == root) {
      fileImpl(path)
    } else {
      fileImpl("$this/$path")
    }
  }

  infix fun File.write(block: BufferedWriter.() -> Unit) {
    bufferedWriter().use { it.block() }
  }

  infix fun File.read(block: BufferedReader.() -> Unit) {
    bufferedReader().use { it.block() }
  }

  @TaskAction
  abstract fun action()

  private fun directoryImpl(path: String) = File(path).also {
    if (!it.exists()) {
      it.mkdirs()
    }
  }

  private fun fileImpl(path: String) = File(path).also {
    if (!it.exists()) {
      // Create parent directories if they don't exist
      it.parentFile.mkdirs()

      // Create a new file
      it.createNewFile()
    }
  }
}
