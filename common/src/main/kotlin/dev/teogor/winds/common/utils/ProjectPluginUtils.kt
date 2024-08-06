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

package dev.teogor.winds.common.utils

import org.gradle.api.Project

/**
 * A list of Gradle plugins that are considered publishing plugins.
 * These plugins are typically used to publish libraries or other artifacts to a repository.
 */
private val publishPlugins = listOf(
  "com.android.library",
  "com.gradle.plugin-publish",
  "org.jetbrains.kotlin.multiplatform",
  "org.jetbrains.kotlin.jvm",
  "org.jetbrains.kotlin.js",
  "java",
  "java-library",
  "java-platform",
  "java-gradle-plugin",
  "version-catalog",
)

/**
 * Checks whether the project has the Android library plugin applied.
 *
 * @return `true` if the project has the Android library plugin applied, `false` otherwise.
 */
fun Project.hasAndroidLibraryPlugin(): Boolean {
  return plugins.hasPlugin("com.android.library")
}

/**
 * Checks whether the project has the Kotlin Multiplatform plugin applied.
 *
 * @return `true` if the project has the Kotlin Multiplatform plugin applied, `false` otherwise.
 */
fun Project.hasKotlinMultiplatformPlugin(): Boolean {
  return plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")
}

/**
 * Checks whether the project has the Kotlin DSL plugin applied.
 *
 * @return `true` if the project has the Kotlin DSL plugin applied, `false` otherwise.
 */
fun Project.hasKotlinDslPlugin(): Boolean {
  return plugins.hasPlugin("org.gradle.kotlin.kotlin-dsl")
}

/**
 * Checks whether the project has any of the publishing plugins applied.
 *
 * @return `true` if the project has any of the publishing plugins applied, `false` otherwise.
 */
fun Project.hasPublishPlugin(): Boolean {
  return publishPlugins.any { plugins.hasPlugin(it) }
}

/**
 * Checks whether the project has the Winds plugin applied.
 *
 * @return `true` if the project has the Winds plugin applied, `false` otherwise.
 */
fun Project.hasWindsPlugin(): Boolean {
  return project.plugins.hasPlugin("dev.teogor.winds")
}

/**
 * Applies a given action to all child projects that have the Winds plugin applied.
 *
 * @param action The action to apply to each Wind-enabled child project.
 */
inline fun Project.processWindsChildProjects(
  crossinline action: Project.() -> Unit,
) {
  childProjects.values
    .toList()
    .filter { hasWindsPlugin() }
    .forEach {
      it.action()
    }
}
