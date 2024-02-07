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

import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.getValue
import dev.teogor.winds.api.impl.MavenPublishImpl
import dev.teogor.winds.api.model.WindsFeature
import dev.teogor.winds.common.utils.hasAndroidLibraryPlugin
import dev.teogor.winds.common.utils.hasKotlinDslPlugin
import dev.teogor.winds.common.utils.hasPublishPlugin
import dev.teogor.winds.common.utils.hasWindsPlugin
import dev.teogor.winds.common.utils.processWindsChildProjects
import dev.teogor.winds.gradle.utils.configureBomModule
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

fun Project.configureMavenPublish() {
  // Apply publish plugins only for Android modules due to Maven plugin requirements
  if (hasAndroidLibraryPlugin()) {
    val winds: Winds by extensions
    val maven: MavenPublish by winds
    if (hasKotlinDslPlugin()) {
      plugins.apply("java-library")
      plugins.apply("com.vanniktech.maven.publish")
    } else if (hasPublishPlugin()) {
      plugins.apply("com.vanniktech.maven.publish")
    } else {
      maven.canBePublished = false
    }
  }

  afterEvaluate {
    val winds: Winds by extensions
    val mavenPublishFeatureEnabled = winds isEnabled WindsFeature.MAVEN_PUBLISH
    if (mavenPublishFeatureEnabled) {
      val maven: MavenPublish by winds

      // Handle BOM and artifact publishing separately
      if (maven.isBoM) {
        configureAndApplyBomPlugins(maven)
      } else if (maven.canBePublished) {
        configureAndApplyPublicationPlugins(maven)
      }

      // Create custom publish task for child projects not directly publishing
      if (path != ":" && !maven.canBePublished) {
        createAndConfigureSubprojectPublishTask(maven)
      }

      // Propagate Winds Maven Publish configuration to child projects
      propagateMavenPublishToChildProjects(winds)
    }
  }
}

/**
 * Configures and applies plugins necessary for publishing a Bill of Materials (BOM) module.
 *
 * This function is called only for modules marked as BOM within the `Winds` configuration.
 * It ensures the appropriate plugins are applied for BOM publishing using Maven.
 *
 * @param maven The `MavenPublish` instance containing configuration for the BOM.
 *
 * @see MavenPublish
 */
private fun Project.configureAndApplyBomPlugins(maven: MavenPublish) {
  if (!hasPublishPlugin()) {
    plugins.apply("java-platform")
  }
  plugins.apply("com.vanniktech.maven.publish")
  configureBomModule(maven)
}

/**
 * Configures and applies plugins necessary for publishing a regular artifact.
 *
 * This function is called for modules that are not BOMs and are marked as publishable
 * within the `Winds` configuration. It ensures the appropriate plugins are applied
 * for artifact publishing using Maven.
 *
 * @param maven The `MavenPublish` instance containing configuration for the artifact.
 *
 * @see MavenPublish
 */
private fun Project.configureAndApplyPublicationPlugins(maven: MavenPublish) {
  if (hasKotlinDslPlugin()) {
    plugins.apply("java-library")
    plugins.apply("com.vanniktech.maven.publish")
  } else if (hasPublishPlugin()) {
    plugins.apply("com.vanniktech.maven.publish")
  } else {
    maven.canBePublished = false
  }
}

/**
 * Creates and configures a custom "publish" task for child projects that are not directly
 * publishing.
 *
 * This function is called for child projects that are not marked as BOMs or explicitly
 * publishable, but have a parent project that is publishing. It creates a task named
 * "publish{artifactIdPrefix}Libraries" in the child project, which depends on the
 * corresponding "publish" task in the parent project.
 * This allows for chained publishing behavior even across projects that don't publish
 * directly.
 *
 * @param maven The `MavenPublish` instance containing configuration for the parent project.
 *
 * @see MavenPublish
 */
private fun Project.createAndConfigureSubprojectPublishTask(maven: MavenPublish) {
  val isBomModule = maven.isBoM
  val artifactIdPrefix = maven.artifactId!!.split("-").mapIndexed { index, segment ->
    if (index == 0 || segment.isEmpty()) {
      segment
    } else {
      segment.replaceFirstChar { it.titlecase() }
    }
  }.joinToString("")

  val taskName = "publish${artifactIdPrefix}Libraries"
  project.tasks.create(taskName) {
    if (!isBomModule) {
      project.childProjects.values.forEach { subproject ->
        val parentPublishTask = subproject.parent!!.tasks.findByName(taskName)
        subproject.afterEvaluate {
          val publishTask = subproject.tasks.findByName("publish")
          parentPublishTask?.dependsOn(publishTask)
        }
      }
    }
  }
}

/**
 * Propagates `Winds` Maven Publish configuration to child projects within the Winds
 * plugin context.
 *
 * This function iterates through child projects that have the Winds plugin applied and
 * copies the current project's Maven Publish configuration (build features and options)
 * to them. This ensures consistent Maven Publish settings across the project hierarchy.
 *
 * @param winds The `Winds` instance for the current project.
 *
 * @see MavenPublish
 * @see processWindsChildProjects
 */
private fun Project.propagateMavenPublishToChildProjects(winds: Winds) {
  processWindsChildProjects {
    afterEvaluate {
      if (hasWindsPlugin()) {
        val childWinds = extensions.getByType<Winds>()
        childWinds.buildFeatures.mavenPublish = winds.buildFeatures.mavenPublish
        (childWinds.mavenPublish as MavenPublishImpl).let { childMavenPublish ->
          childMavenPublish.mavenPublishOptions.apply {
            add(winds.mavenPublish)
            addAll((winds.mavenPublish as MavenPublishImpl).mavenPublishOptions)
          }
        }
      }
    }
  }
}
