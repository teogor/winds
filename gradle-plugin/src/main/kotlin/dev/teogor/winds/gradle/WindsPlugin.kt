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

import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.impl.WindsImpl
import dev.teogor.winds.api.impl.WindsLegacyOptions
import dev.teogor.winds.api.model.ModuleDescriptor
import dev.teogor.winds.api.model.Path
import dev.teogor.winds.common.ktx.hasVanniktechMavenPlugin
import dev.teogor.winds.common.ktx.register
import dev.teogor.winds.common.maven.configureMavenPublishing
import dev.teogor.winds.gradle.tasks.ReleaseNotesTask
import dev.teogor.winds.gradle.tasks.configureMavenPublish
import dev.teogor.winds.gradle.tasks.impl.CollectWindsExtensionsTask
import dev.teogor.winds.gradle.tasks.impl.collectWindsExtensionsTaskName
import dev.teogor.winds.gradle.tasks.impl.configureCollectWindsExtensionsTask
import dev.teogor.winds.gradle.tasks.impl.configureDocsGenerator
import dev.teogor.winds.gradle.tasks.impl.configureMavenPublishLegacy
import dev.teogor.winds.gradle.tasks.impl.configureWorkflowSynthesizer
import dev.teogor.winds.gradle.tasks.impl.getCollectWindsExtensionsTask
import dev.teogor.winds.ktx.hasPublishGradlePlugin
import dev.teogor.winds.ktx.inheritFromParentWinds
import dev.teogor.winds.ktx.isRootProject
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
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
      // region Deprecated
      // Creates the `Winds` extension object for the project.
      createWindsLegacyExtension()

      // Configures Maven publishing for the project.
      configureMavenPublishLegacy()

      // Configures documentation generation for the project.
      configureDocsGenerator()

      // Configures workflow synthesis for the project.
      configureWorkflowSynthesizer()
      // endregion

      withWinds(
        onWindsAvailable = {
          inheritFromParentWinds(this)
          configureMavenPublish(this)

          val depSpec = this@withWinds.moduleMetadata.artifactDescriptor
          project.group = depSpec?.group ?: "unspecified"
          project.version = depSpec?.version?.toString() ?: "unspecified"

          if (isRootProject()) {
            val collectWindsExtensions = configureCollectWindsExtensionsTask()
            allprojects.toList()
              .filterNot { it == target }
              .forEach { subProject ->
                subProject.configureCollectWindsExtensionsTask()
                collectWindsExtensions.dependsOn(
                  "${subProject.path}:$collectWindsExtensionsTaskName",
                )
              }
          } else {
            val collectWindsExtensions = getCollectWindsExtensionsTask()
            allprojects.toList()
              .filterNot { it == target }
              .forEach { subProject ->
                collectWindsExtensions.dependsOn(
                  "${subProject.path}:$collectWindsExtensionsTaskName",
                )
              }
          }
        },
      ) {
        val collectWindsExtensions = getCollectWindsExtensionsTask()

        collectWindsExtensions.setModuleMetadata(this@withWinds.moduleMetadata)

        collectAndPropagateChildMetadata(
          winds = this,
          collectWindsExtensions = collectWindsExtensions,
        )

        // Set project group and version based on module metadata or defaults
        // TODO instead of ?: 'unspecified' use project.group or project.version
        val depSpec = this@withWinds.moduleMetadata.artifactDescriptor
        project.group = depSpec?.group ?: "unspecified"
        project.version = depSpec?.version?.toString() ?: "unspecified"

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
            getPublishTask(parent!!).dependsOn("$path:publish")
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
                dependsOn(task)
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
}

fun Project.collectAndPropagateChildMetadata(
  winds: Winds,
  collectWindsExtensions: CollectWindsExtensionsTask,
) {
  (parent ?: rootProject).getCollectWindsExtensionsTask {
    addChildMetadata(winds.moduleMetadata)
    collectWindsExtensions.propagateChildMetadata {
      // addChildMetadata(this)
    }
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

/**
 * Creates a `WindsOptions` extension for the project and propagates
 * its `buildFeatures` property to child projects. If the parent project
 * has the `dev.teogor.winds` plugin, the `buildFeatures` and `docsGenerator`
 * properties are copied from the parent project's `WindsOptions` extension.
 */
private fun Project.createWindsLegacyExtension() {
  // Check if the WindsOptions extension already exists
  extensions.findByType<WindsLegacyOptions>() ?: let {
    val rootHas = parent?.plugins?.hasPlugin("dev.teogor.winds") ?: false
    extensions.create<WindsLegacyOptions>(name = "windsLegacy").also {
      if (rootHas) {
        val rootWindsOptions = parent!!.extensions.getByType<WindsLegacyOptions>()
        it.buildFeatures = rootWindsOptions.buildFeatures
        it.docsGenerator = rootWindsOptions.docsGenerator
      }
    }
  }
}
