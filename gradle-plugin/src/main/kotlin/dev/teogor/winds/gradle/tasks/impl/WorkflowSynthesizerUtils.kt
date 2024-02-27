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

import dev.teogor.winds.api.WindsLegacy
import dev.teogor.winds.api.WorkflowSynthesizer
import dev.teogor.winds.api.getValue
import dev.teogor.winds.api.impl.MavenPublishImpl
import dev.teogor.winds.api.impl.WorkflowSynthesizerImpl
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.api.model.WindsFeature
import dev.teogor.winds.gradle.utils.getAllDependencies
import dev.teogor.winds.gradle.utils.isWindsApplied
import dev.teogor.winds.gradle.utils.lazy
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

fun Project.configureWorkflowSynthesizer() {
  lazy {
    val base = this
    val windsLegacy: WindsLegacy by extensions
    val workflowSynthesizerFeatureEnabled = windsLegacy isEnabled WindsFeature.WORKFLOW_SYNTHESIZER
    val workflowSynthesizer: WorkflowSynthesizer by windsLegacy
    val workflowSynthesizerImpl = workflowSynthesizer as WorkflowSynthesizerImpl
    workflowSynthesizerImpl.task?.isEnabled = workflowSynthesizerFeatureEnabled
    if (workflowSynthesizerFeatureEnabled) {
      subprojects {
        if (workflowSynthesizer.excludedModules.contains(this.path)) {
          return@subprojects
        }
        val projectBase = this
        isWindsApplied(lazyStart = true) {
          lazy {
            val windsLegacyBase = projectBase.extensions.getByType<WindsLegacy>()
            val mavenPublishFeatureEnabled = windsLegacyBase isEnabled WindsFeature.MAVEN_PUBLISH
            val mavenPublish = windsLegacyBase.mavenPublish
            val moduleInfo = ModuleInfo(
              completeName = mavenPublish.completeName,
              name = mavenPublish.name ?: "",
              displayName = mavenPublish.displayName ?: "",
              description = mavenPublish.description ?: "",
              groupId = mavenPublish.groupId ?: "",
              artifactId = mavenPublish.artifactId ?: "",
              version = mavenPublish.version ?: Version(0, 0, 0),
              path = path,
              dependencies = getAllDependencies(),
              canBePublished = mavenPublish.canBePublished,
              names = (mavenPublish as MavenPublishImpl).gets { displayName },
            )
            (workflowSynthesizerImpl.task as? WorkflowSynthesizerTask)?.addLibrary(moduleInfo)
            (workflowSynthesizerImpl.task as? WorkflowSynthesizerTask)?.getLibs()?.let { libs ->
              libs
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
                    println("BoM($name) @ $taskName ${it.names}")
                    // project.tasks.create(taskName) {
                    //
                    // }
                  } else {
                    val name = if (it.names.size >= 2) {
                      it.names[it.names.size - 2]
                    } else {
                      it.names.last()
                    }.replace(" ", "")
                      .replaceFirstChar { it.titlecase() }
                    val taskName = "publish${name}ToMaven"

                    // Module pr Library
                    println("Lib($name) @ $taskName")
                    // project.tasks.create(taskName) {
                    //
                    // }
                  }
                }
            }
          }
        }
      }

      println("configureWorkflowSynthesizer :: $path")
      subprojectChildrens {
        val isWindsApplied = isWindsApplied()
        val hasChildrens = childProjects.isNotEmpty()
        println(
          "configureWorkflowSynthesizer(subprojectChildrens) :: $path $hasChildrens $isWindsApplied",
        )
        if (hasChildrens) {
          subprojectChildrens {
            val isWindsApplied = isWindsApplied()
            val hasChildrens = childProjects.isNotEmpty()
            println(
              "configureWorkflowSynthesizer(subprojectChildrens) :: $path $hasChildrens $isWindsApplied",
            )
          }
        }
      }
    }
  }
}

inline fun Project.subprojectChildrens(
  crossinline action: Project.() -> Unit,
) {
  val base = this
  subprojects {
    if (parent == base) {
      action()
    }
  }
}
