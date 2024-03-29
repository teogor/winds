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

package dev.teogor.winds.gradle.utils

import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.WindsLegacy
import dev.teogor.winds.api.impl.MavenPublishImpl
import dev.teogor.winds.api.impl.WindsLegacyOptions
import dev.teogor.winds.api.model.DependencyDefinition
import dev.teogor.winds.api.model.DependencyType
import dev.teogor.winds.api.model.LocalProjectDependency
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.common.dependencies.DependencyCollector
import dev.teogor.winds.common.dependencies.filterVariants
import dev.teogor.winds.common.dependencies.includePlatform
import dev.teogor.winds.common.utils.attachMavenData
import dev.teogor.winds.common.utils.hasAndroidLibraryPlugin
import dev.teogor.winds.common.utils.processWindsChildProjects
import dev.teogor.winds.gradle.WindsPlugin
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType

/**
 * Checks if the Winds plugin is applied to the project. If it
 * is, the given block is executed with the `Winds` extension object.

 * @param block The block to execute if the Winds plugin is applied.

 * @return `true` if the Winds plugin is applied, `false` otherwise.
 */
inline fun Project.isWindsApplied(
  lazyStart: Boolean = true,
  crossinline block: WindsLegacy.() -> Unit,
) {
  if (lazyStart) {
    lazy {
      checkPluginApplied(block)
    }
  } else {
    checkPluginApplied(block)
  }
}

inline fun Project.checkPluginApplied(crossinline block: WindsLegacy.() -> Unit) {
  val hasWinds = project.plugins.hasPlugin("dev.teogor.winds")
  if (hasWinds) {
    val windsLegacy: WindsLegacy by extensions
    windsLegacy.block()
  }
}

// todo kotlin-dsl
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

inline fun <reified T : DefaultTask> WindsLegacyOptions.registerTask(
  name: String,
) = project.tasks.register<T>(name)

fun Project.getAllDependencies(): List<DependencyDefinition> {
  project.evaluationDependsOnChildren()
  val collectedDependencies = DependencyCollector(includePlatform, filterVariants).collect(project)
  return collectedDependencies.flattenDependencies()
}

/**
 * Creates a copy of the current version object and applies the
 * provided configuration block to it.
 *
 * @param block A configuration block to apply to the copied version
 * object.
 * @return A new version object with the applied configuration.
 */
@Deprecated("")
fun MavenPublish.copyVersion(
  block: Version.VersionBuilder.() -> Unit = {},
): Version {
  return version!!.toBuilder().apply(block).build()
}

@Deprecated("")
infix fun MavenPublish.attachTo(pom: MavenPom) {
  attachMavenData(pom, this)
}

@Deprecated("")
fun Project.windsPluginConfiguration(action: Project.(WindsLegacy) -> Unit) {
  subprojects {
    val project = this
    plugins.withType<WindsPlugin> {
      val windsLegacy: WindsLegacy by extensions
      project.action(windsLegacy)
    }
  }
}

fun Project.afterWindsPluginConfiguration(action: Project.(WindsLegacy) -> Unit) {
  processWindsChildProjects {
    plugins.withType<WindsPlugin> {
      if (state.executed) {
        val windsLegacy: WindsLegacy by extensions
        action(windsLegacy)
      } else {
        afterEvaluate {
          val windsLegacy: WindsLegacy by extensions
          action(windsLegacy)
        }
      }
    }
  }
}

fun Project.configureWindsPluginConfiguration(action: Project.(WindsLegacy) -> Unit) {
  if (state.executed) {
    processWindsChildProjects {
      plugins.withType<WindsPlugin> {
        afterEvaluate {
          val windsLegacy: WindsLegacy by extensions
          action(windsLegacy)
        }
      }
    }
  } else {
    afterEvaluate {
      processWindsChildProjects {
        plugins.withType<WindsPlugin> {
          afterEvaluate {
            val windsLegacy: WindsLegacy by extensions
            action(windsLegacy)
          }
        }
      }
    }
  }
}

/**
 * Collects module information for the current project and its
 * subprojects.
 *
 * @param onModuleInfo A callback function that will be invoked for
 * each module with its module information.
 */
inline fun Project.collectModulesInfo(
  crossinline onModuleInfo: (ModuleInfo) -> Unit,
) {
  val allDependencies = if (hasAndroidLibraryPlugin()) getAllDependencies() else emptyList()

  configureWindsPluginConfiguration {
    val windsLegacy: WindsLegacy by extensions

    val docsGenerator = windsLegacy.docsGenerator
    val dependencies = aggregateDependencies(
      allDependencies = allDependencies,
      dependencyGatheringType = docsGenerator.dependencyGatheringType,
    )

    val mavenPublish = windsLegacy.mavenPublish as MavenPublishImpl
    val moduleInfo = ModuleInfo(
      completeName = mavenPublish.completeName,
      name = mavenPublish.name ?: "",
      displayName = mavenPublish.displayName ?: "",
      description = mavenPublish.description ?: "",
      groupId = mavenPublish.groupId ?: "",
      artifactId = mavenPublish.artifactId ?: "",
      version = mavenPublish.version ?: Version(0, 0, 0),
      path = path,
      dependencies = dependencies,
      canBePublished = mavenPublish.canBePublished,
      names = if (mavenPublish.enforceUniqueNames) {
        mavenPublish.gets { displayName }.distinct()
      } else {
        mavenPublish.gets { displayName }
      },
    )

    onModuleInfo(moduleInfo)
  }
}

/**
 * Aggregates dependencies for the current project based on the
 * provided dependency gathering type.
 *
 * @param allDependencies The list of all dependencies for the
 * project.
 * @param dependencyGatheringType The type of dependency gathering
 * to perform.
 *
 * @return The aggregated list of dependencies.
 */
fun Project.aggregateDependencies(
  allDependencies: List<DependencyDefinition>,
  dependencyGatheringType: DependencyType,
): MutableList<DependencyDefinition> {
  val dependencies = allDependencies.toMutableList().also {
    if (!hasAndroidLibraryPlugin()) {
      it.addAll(getAllDependencies())
    }
  }

  return when (dependencyGatheringType) {
    DependencyType.NONE -> {
      mutableListOf()
    }

    DependencyType.LOCAL -> {
      dependencies
        .filterIsInstance<LocalProjectDependency>()
        .toMutableList()
    }

    DependencyType.ALL -> {
      dependencies.toMutableList()
    }
  }.distinct().toMutableList()
}

// region Deprecated
@Deprecated(
  message = "Use hasWindsPlugin",
  replaceWith = ReplaceWith(
    "hasWindsPlugin()",
    "dev.teogor.winds.common.utils.hasWindsPlugin",
  ),
)
fun Project.isWindsApplied(): Boolean {
  return project.plugins.hasPlugin("dev.teogor.winds")
}

@Deprecated(
  message = "Use hasPublishPlugin",
  replaceWith = ReplaceWith(
    "hasPublishPlugin()",
    "dev.teogor.winds.common.utils.hasPublishPlugin",
  ),
)
fun Project.hasPublishPlugin() = publishPlugins.any { plugins.hasPlugin(it) }

@Deprecated(
  message = "Use hasAndroidLibraryPlugin",
  replaceWith = ReplaceWith(
    "hasAndroidLibraryPlugin()",
    "dev.teogor.winds.common.utils.hasAndroidLibraryPlugin",
  ),
)
fun Project.isAndroidLibrary() = plugins.hasPlugin("com.android.library")

@Deprecated(
  message = "Use hasKotlinDslPlugin",
  replaceWith = ReplaceWith(
    "hasKotlinDslPlugin()",
    "dev.teogor.winds.common.utils.hasKotlinDslPlugin",
  ),
)
fun Project.hasKotlinDslPlugin() = plugins.hasPlugin("org.gradle.kotlin.kotlin-dsl")

@Deprecated(
  message = "if necessary use afterWindsPluginConfiguration",
  replaceWith = ReplaceWith(
    "Project.afterWindsPluginConfiguration",
    "dev.teogor.winds.gradle.utils.afterWindsPluginConfiguration",
  ),
)
fun Project.lazy(block: Project.() -> Unit) = afterEvaluate(block)
// endregion
