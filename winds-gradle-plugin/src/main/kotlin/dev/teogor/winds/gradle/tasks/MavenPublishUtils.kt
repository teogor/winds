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

import dev.teogor.winds.api.BomOptions
import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.api.Publishing
import dev.teogor.winds.api.Winds
import dev.teogor.winds.common.utils.hasAndroidLibraryPlugin
import dev.teogor.winds.common.utils.hasKotlinDslPlugin
import dev.teogor.winds.common.utils.hasKotlinMultiplatformPlugin
import dev.teogor.winds.common.utils.hasPublishPlugin
import dev.teogor.winds.ktx.getValue
import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate

fun Project.configureMavenPublish(winds: Winds) {
  // Apply publish plugins only for Android modules due to Maven plugin requirements
  if (hasAndroidLibraryPlugin()) {
    val publishing: Publishing by winds
    val metadata: ModuleMetadata by winds
    if (hasKotlinDslPlugin()) {
      plugins.apply("java-library")
      plugins.apply("com.vanniktech.maven.publish")
    } else if (hasPublishPlugin()) {
      plugins.apply("com.vanniktech.maven.publish")
    } else {
      publishing.enabled = false
    }
  } else if (hasKotlinMultiplatformPlugin()) {
    val publishing: Publishing by winds
    if (publishing.enabled) {
      plugins.apply("com.vanniktech.maven.publish")
    }
  }

  afterEvaluate {
    val publishing: Publishing by winds
    val metadata: ModuleMetadata by winds

    // Handle BoM and artifact publishing separately
    if (metadata.isBom) {
      configureAndApplyBomPlugins(publishing, metadata.bomOptions)
    } else if (publishing.enabled) {
      configureAndApplyPublicationPlugins(publishing)
    }

    // Create custom publish task for child projects not directly publishing
    // TODO better handling for path is ':'
    if (path != ":" && !publishing.enabled) {
      // todo enable task creation. handling based on actual publish value
      //  createAndConfigureSubprojectPublishTask(metadata)
    }
  }
}

private fun Project.configureAndApplyBomPlugins(
  publishing: Publishing,
  bomOptions: BomOptions?,
) {
  if (!hasPublishPlugin()) {
    plugins.apply("java-platform")
  }
  plugins.apply("com.vanniktech.maven.publish")
  configureBomModule(publishing, bomOptions)
}

private fun Project.configureAndApplyPublicationPlugins(publishing: Publishing) {
  if (hasKotlinDslPlugin()) {
    plugins.apply("java-library")
    plugins.apply("com.vanniktech.maven.publish")
  } else if (hasPublishPlugin()) {
    plugins.apply("com.vanniktech.maven.publish")
  } else {
    publishing.enabled = false
  }
}

private fun Project.createAndConfigureSubprojectPublishTask(metadata: ModuleMetadata) {
  val isBomModule = metadata.isBom
  val artifactIdPrefix = metadata.artifactDescriptor?.artifactId

  val taskName = "publish${artifactIdPrefix}Libraries"
  project.tasks.create(taskName) {
    if (!isBomModule) {
      project.childProjects.values.forEach { subproject ->
        val parentPublishTask = subproject.parent!!.tasks.findByName(taskName)
        subproject.afterEvaluate {
          subproject.tasks.findByName("publish")?.let { task ->
            // parentPublishTask?.dependsOn(task)
          }
        }
      }
    }
  }
}
