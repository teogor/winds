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
import org.gradle.util.GradleVersion
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

  companion object {
    const val MIN_GRADLE_VERSION = "7.0"
  }

  override fun apply(project: Project) = with(project) {
    check(GradleVersion.current() >= GradleVersion.version(MIN_GRADLE_VERSION)) {
      "Gradle version must be at least $MIN_GRADLE_VERSION"
    }
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
        publishing.enabled = false
      }

      configurePublishTask(
        cascadePublish = publishing.cascade,
      )

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

        collectModuleDescriptors(winds = this@withWinds)
      }
    }
  }

  private fun ReleaseNotesTask.collectModuleDescriptors(winds: Winds) {
    addModuleDescriptor(
      moduleDescriptor = buildModuleDescriptor(
        project = project,
        winds = winds,
      ),
    )

    project.subprojects.forEach { subproject ->
      subproject.afterEvaluate {
        subproject.extensions.findByType<Winds>()?.let { winds ->
          addModuleDescriptor(
            moduleDescriptor = buildModuleDescriptor(
              project = subproject,
              winds = winds,
            ),
          )
        }
      }
    }
  }

  private fun buildModuleDescriptor(project: Project, winds: Winds): ModuleDescriptor {
    val artifactDescriptor = winds.moduleMetadata.artifactDescriptor ?: error(
      message = "Artifact descriptor is missing",
    )

    return ModuleDescriptor(
      name = artifactDescriptor.name,
      path = Path.from(project),
      artifact = artifactDescriptor,
      dependencies = artifactDescriptor.artifacts.drop(1),
      publish = winds.publishing.enabled,
      completeName = artifactDescriptor.completeName,
      description = winds.moduleMetadata.description,
      documentationBuilder = winds.documentationBuilder,
      ticketSystem = winds.moduleMetadata.ticketSystem,
      scm = winds.moduleMetadata.scm,
      isBom = winds.moduleMetadata.isBom,
      isPlugin = project.hasPublishGradlePlugin(),
      windsChangelogYml = File(project.projectDir, "winds-changelog.yml").absolutePath,
    )
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

  /**
   * Configures a dependency on the parent project's "publish" task for the current
   * project's "publish" task, based on the provided `cascadePublish` flag.
   *
   * This function is used to ensure that the current project's publishing process
   * is triggered after the parent project's publishing is complete, following a cascading
   * publishing behavior when enabled.
   *
   * @param cascadePublish A boolean flag indicating whether to configure the dependency.
   */
  private fun Project.configurePublishTask(cascadePublish: Boolean) {
    if (cascadePublish && parent != null) {
      val publishTask = getPublishTask(parent!!)
      publishTask.dependsOn("$path:publish")
    }
  }

  /**
   * Retrieves the "publish" task from the provided project.
   *
   * This function first attempts to find a task named "publish" using `findByName`.
   * If the task is not found, it creates a new task named "publish" using `create`.
   *
   * @param project The project from which to retrieve the task.
   * @return The "publish" task from the project, either existing or newly created.
   */
  private fun getPublishTask(project: Project): Task {
    return project.tasks.findByName("publish") ?: project.tasks.create("publish")
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
