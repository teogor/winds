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
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.impl.MavenPublishImpl
import dev.teogor.winds.api.impl.WindsOptions
import dev.teogor.winds.api.model.DependencyDefinition
import dev.teogor.winds.api.model.DependencyType
import dev.teogor.winds.api.model.LocalProjectDependency
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.api.model.VersionBuilder
import dev.teogor.winds.common.dependencies.DependencyCollector
import dev.teogor.winds.common.dependencies.filterVariants
import dev.teogor.winds.common.dependencies.includePlatform
import dev.teogor.winds.common.utils.attachMavenData
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
  crossinline block: Winds.() -> Unit,
) {
  if (lazyStart) {
    lazy {
      checkPluginApplied(block)
    }
  } else {
    checkPluginApplied(block)
  }
}

fun Project.isWindsApplied(): Boolean {
  val hasWinds = project.plugins.hasPlugin("dev.teogor.winds")
  return hasWinds
}

inline fun Project.checkPluginApplied(crossinline block: Winds.() -> Unit) {
  val hasWinds = project.plugins.hasPlugin("dev.teogor.winds")
  if (hasWinds) {
    val winds: Winds by extensions
    winds.block()
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

fun Project.hasPublishPlugin() = publishPlugins.any { plugins.hasPlugin(it) }

fun Project.isAndroidModule() =
  plugins.hasPlugin("com.android.application") || plugins.hasPlugin("com.android.library")

fun Project.hasKotlinDslPlugin() = plugins.hasPlugin("org.gradle.kotlin.kotlin-dsl")

inline fun <reified T : DefaultTask> WindsOptions.registerTask(
  name: String,
) = project.tasks.register<T>(name)

@Deprecated("if necessary use afterWindsPluginConfiguration")
fun Project.lazy(block: Project.() -> Unit) = afterEvaluate(block)

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
fun MavenPublish.copyVersion(
  block: VersionBuilder.() -> Unit = {},
): Version {
  return version!!.toBuilder().apply(block).build()
}

infix fun MavenPublish.attachTo(pom: MavenPom) {
  attachMavenData(pom, this)
}

fun Project.windsPluginConfiguration(action: Project.(Winds) -> Unit) {
  subprojects {
    val project = this
    plugins.withType<WindsPlugin> {
      val winds: Winds by extensions
      project.action(winds)
    }
  }
}

fun Project.afterWindsPluginConfiguration(action: Project.(Winds) -> Unit) {
  subprojects {
    val project = this
    plugins.withType<WindsPlugin> {
      if (project.state.executed) {
        val winds: Winds by extensions
        project.action(winds)
      } else {
        project.afterEvaluate {
          val winds: Winds by extensions
          project.action(winds)
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
  val allDependencies = if (isAndroidModule()) getAllDependencies() else emptyList()

  afterWindsPluginConfiguration {
    val winds: Winds by extensions

    val docsGenerator = winds.docsGenerator
    val dependencies = aggregateDependencies(
      allDependencies = allDependencies,
      dependencyGatheringType = docsGenerator.dependencyGatheringType,
    )

    val mavenPublish = winds.mavenPublish
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
      names = (mavenPublish as MavenPublishImpl).gets { displayName },
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
    if (!isAndroidModule()) {
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
  }
}
