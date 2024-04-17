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

package dev.teogor.winds.gradle

import dev.teogor.winds.api.ArtifactDescriptor
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.impl.WindsImpl
import dev.teogor.winds.api.model.ModuleDescriptor
import dev.teogor.winds.api.model.Path
import dev.teogor.winds.common.ktx.hasVanniktechMavenPlugin
import dev.teogor.winds.common.ktx.register
import dev.teogor.winds.common.maven.configureMavenPublishing
import dev.teogor.winds.gradle.tasks.ReleaseNotesTask
import dev.teogor.winds.gradle.tasks.configureMavenPublish
import dev.teogor.winds.ktx.hasPublishGradlePlugin
import dev.teogor.winds.ktx.inheritFromParentWinds
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import java.io.File

/**
 * A Gradle plugin that adds Winds support to projects.
 *
 * This plugin provides the following features:
 *
 * - Configures Maven publishing for the project.
 * - Configures documentation generation for the project.
 */
class WindsPlugin : BaseWindsPlugin {
  override fun apply(target: Project) {
    with(target) {
      withWinds(
        onWindsAvailable = {
          inheritFromParentWinds(this)
          configureMavenPublish(this)

          extractAndSetProjectDetails(
            depSpec = this@withWinds.moduleMetadata.artifactDescriptor,
          )
        },
      ) {
        extractAndSetProjectDetails(
          depSpec = this@withWinds.moduleMetadata.artifactDescriptor,
        )

        configureMavenPublish(this)
        configureMavenPublishing(this)
        if (!hasVanniktechMavenPlugin()) {
          publishingOptions.publish = false
        }

        fun getPublishTask(project: Project): Task {
          return project.tasks.findByName("publish") ?: project.tasks.create("publish")
        }

        if (publishingOptions.cascadePublish) {
          if (parent != null) {
            // getPublishTask(parent!!).dependsOn("$path:publish")
          }
        }

        val taskName = "windsMd"
        tasks.register<ReleaseNotesTask>(taskName) {
          val forceDateUpdate = properties
            .getOrDefault("forceDateUpdate", "false")
            .toString().toBoolean()
          setForceDateUpdate(forceDateUpdate)
          if (properties.containsKey("forceDateUpdate")) {
            project.setProperty("forceDateUpdate", "false")
          }

          subprojects.forEach {
            it.afterEvaluate {
              it.tasks.findByName(taskName)?.let { task ->
                // dependsOn(task)
              }
            }
          }

          this@withWinds.let { winds ->
            addModuleDescriptor(
              ModuleDescriptor(
                name = winds.moduleMetadata.artifactDescriptor!!.name,
                path = Path.from(this@with),
                artifact = winds.moduleMetadata.artifactDescriptor!!,
                dependencies = winds.moduleMetadata.artifactDescriptor!!.artifacts.drop(1),
                publish = winds.publishingOptions.publish,
                completeName = winds.moduleMetadata.artifactDescriptor!!.completeName,
                description = winds.moduleMetadata.description,
                documentationBuilder = winds.documentationBuilder,
                ticketSystem = winds.moduleMetadata.ticketSystem,
                scm = winds.moduleMetadata.scm,
                isBom = winds.moduleMetadata.isBom,
                isPlugin = hasPublishGradlePlugin(),
                windsChangelogYml = File(project.projectDir, "winds-changelog.yml").absolutePath,
              ),
            )
          }
          subprojects.forEach {
            it.afterEvaluate {
              it.extensions.findByType<Winds>()?.let { winds ->
                addModuleDescriptor(
                  ModuleDescriptor(
                    name = winds.moduleMetadata.artifactDescriptor!!.name,
                    path = Path.from(it),
                    artifact = winds.moduleMetadata.artifactDescriptor!!,
                    dependencies = winds.moduleMetadata.artifactDescriptor!!.artifacts.drop(1),
                    publish = winds.publishingOptions.publish,
                    completeName = winds.moduleMetadata.artifactDescriptor!!.completeName,
                    description = winds.moduleMetadata.description,
                    documentationBuilder = winds.documentationBuilder,
                    ticketSystem = winds.moduleMetadata.ticketSystem,
                    scm = winds.moduleMetadata.scm,
                    isBom = winds.moduleMetadata.isBom,
                    isPlugin = it.hasPublishGradlePlugin(),
                    windsChangelogYml = File(it.projectDir, "winds-changelog.yml").absolutePath,
                  ),
                )
              }
            }
          }
        }
      }
    }
  }

  /**
   * Extracts group and version information from the provided artifact descriptor and sets
   * the project's group and version if they are not already set. If the artifact descriptor
   * is null or doesn't contain the information, it falls back to using the existing project
   * group and version or defaults to "unspecified".
   *
   * @param depSpec The artifact descriptor to extract information from, can be null.
   */
  private fun Project.extractAndSetProjectDetails(depSpec: ArtifactDescriptor?) {
    project.group = depSpec?.group ?: project.group ?: "unspecified"
    project.version = depSpec?.version?.toString() ?: project.version ?: "unspecified"
  }
}

fun Project.withWinds(
  onWindsAvailable: Winds.() -> Unit = {},
  onWindsReady: Winds.() -> Unit,
) {
  withWinds {
    val windsExtension = this
    onWindsAvailable(windsExtension)
    afterEvaluate {
      onWindsReady(windsExtension)
    }
  }
}

fun Project.withWinds(block: Winds.() -> Unit): Winds {
  val extension = extensions.findByType<Winds>() ?: createWindsExtension()
  block(extension)
  return extension
}

private fun Project.createWindsExtension(): Winds {
  return extensions.create(
    publicType = Winds::class,
    name = "winds",
    instanceType = WindsImpl::class,
  )
}
