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

import dev.teogor.winds.api.WorkflowSynthesizer
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.gradle.tasks.BaseGeneratorTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

abstract class WorkflowSynthesizerTask : BaseGeneratorTask(
  description = "Generates and orchestrates workflows for the project to publish to maven.",
) {

  private lateinit var workflowSynthesizer: WorkflowSynthesizer
  private val libraries = mutableListOf<ModuleInfo>()

  @TaskAction
  override fun action() {
    libraries
      .filter {
        it.canBePublished
      }
      .forEach {
        if (it.isBoM) {
          val name = it.names.last()
            .replace(" ", "")
            .replaceFirstChar { it.titlecase() }
          val taskName = "publish${name}ToMaven"

          // BoM
          // println("BoM($name) @ ${taskName} ${it.names}")
        } else {
          val name = if (it.names.size >= 2) {
            it.names[it.names.size - 2]
          } else {
            it.names.last()
          }.replace(" ", "")
            .replaceFirstChar { it.titlecase() }
          val taskName = "publish${name}ToMaven"

          // Module pr Library
          // println("Lib($name) @ ${taskName}")
        }
      }
  }

  fun addLibrary(data: ModuleInfo) {
    libraries.add(data)
  }

  @Internal
  fun getLibs() = libraries

  fun provideWorkflowSynthesizer(workflowSynthesizer: WorkflowSynthesizer) {
    this.workflowSynthesizer = workflowSynthesizer
  }
}
