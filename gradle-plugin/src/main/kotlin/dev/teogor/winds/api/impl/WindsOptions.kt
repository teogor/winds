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

package dev.teogor.winds.api.impl

import dev.teogor.winds.api.BuildFeatures
import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.TaskBuilder
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.WorkflowSynthesizer
import dev.teogor.winds.gradle.tasks.impl.DocsGeneratorTask
import dev.teogor.winds.gradle.tasks.impl.WorkflowSynthesizerTask
import dev.teogor.winds.gradle.utils.registerTask
import org.gradle.api.DefaultTask
import org.gradle.api.Project

abstract class WindsOptions(
  val project: Project,
) : Winds {
  override var buildFeatures: BuildFeatures = BuildFeaturesImpl()

  override fun buildFeatures(action: BuildFeatures.() -> Unit) {
    buildFeatures = buildFeatures.apply(action)
    prepareDocsGeneratorTask()
    prepareWorkflowSynthesizerTask()
  }

  override var mavenPublish: MavenPublish = MavenPublishImpl()

  override fun mavenPublish(action: MavenPublish.() -> Unit) {
    mavenPublish = mavenPublish.apply(action).apply {
      (this as MavenPublishImpl).configured = true
    }
  }

  override var docsGenerator: DocsGenerator = DocsGeneratorImpl()

  override fun docsGenerator(action: DocsGenerator.() -> Unit) {
    docsGenerator = docsGenerator.apply(action)
    prepareDocsGeneratorTask()
  }

  override var workflowSynthesizer: WorkflowSynthesizer = WorkflowSynthesizerImpl()

  override fun workflowSynthesizer(action: WorkflowSynthesizer.() -> Unit) {
    workflowSynthesizer = workflowSynthesizer.apply(action)
    prepareWorkflowSynthesizerTask()
  }

  private inline fun <reified I : TaskBuilder, reified T : DefaultTask> I.createTask(
    name: String,
    crossinline block: I.() -> Unit,
  ) {
    if (task == null) {
      task = registerTask<T>(name).get()
    }
    this.block()
  }

  private fun prepareDocsGeneratorTask() {
    if (buildFeatures.docsGenerator) {
      docsGenerator.createTask<DocsGenerator, DocsGeneratorTask>(
        name = "docsGeneratorTask",
      ) {
        (task as DocsGeneratorTask).provideDocsGenerator(this)
      }
    }
  }

  private fun prepareWorkflowSynthesizerTask() {
    if (buildFeatures.workflowSynthesizer) {
      workflowSynthesizer.createTask<WorkflowSynthesizer, WorkflowSynthesizerTask>(
        name = "workflowSynthesizerTask",
      ) {
        (task as WorkflowSynthesizerTask).provideWorkflowSynthesizer(this)
      }
    }
  }
}
