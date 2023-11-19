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
import dev.teogor.winds.api.model.Developer
import dev.teogor.winds.api.model.LicenseType
import dev.teogor.winds.api.model.LocalProjectDependency
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.gradle.WindsPlugin
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPomLicenseSpec
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

infix fun MavenPublish.attachTo(pom: MavenPom) {
  pom.apply {
    val mavenPublish = this@attachTo

    name.set(mavenPublish.displayName)
    description.set(mavenPublish.description)
    inceptionYear.set(mavenPublish.inceptionYear.toString())
    url.set(mavenPublish.url)

    contributors {
      // TODO maven contributors
    }

    developers {
      mavenPublish.developers?.toDeveloperSpec(
        mavenPomDeveloperSpec = this,
      ) ?: developerError()
    }

    licenses {
      mavenPublish.licenses?.toLicenseSpec(
        mavenPomLicenseSpec = this,
      ) ?: licenseError()
    }

    scm {
      url.set(mavenPublish.scmUrl)
      connection.set(mavenPublish.scmConnection)
      developerConnection.set(mavenPublish.scmDeveloperConnection)
    }
  }
}

private fun List<Developer>.toDeveloperSpec(
  mavenPomDeveloperSpec: MavenPomDeveloperSpec,
) {
  forEach { developer ->
    mavenPomDeveloperSpec.developer {
      id.set(developer.id)
      name.set(developer.name)
      email.set(developer.email)
      url.set(developer.url)
      roles.set(developer.roles)
      timezone.set(developer.timezone)
      organization.set(developer.organization)
      organizationUrl.set(developer.organizationUrl)
    }
  }
}

private fun List<LicenseType>.toLicenseSpec(
  mavenPomLicenseSpec: MavenPomLicenseSpec,
) {
  forEach { license ->
    mavenPomLicenseSpec.license {
      name.set(license.name)
      url.set(license.url)
      distribution.set(license.distribution)
      // TODO comments.set(license.comments)
    }
  }
}

private fun licenseError(): Nothing = error(
  """
  Uh-oh! A license must be provided for your module. Please specify the license in the `mavenPublish` block within the `winds` extension.
  If you think this is an error, please [create an issue](https://github.com/teogor/winds) to assist in resolving this matter.
  Be sure to include the following error ID in your report to help us identify and address the issue:
  ${ErrorId.PomLicenseError.getErrorIdString()}
  Thank you for your contribution to improving Winds!
  """.trimIndent(),
)

private fun developerError(): Nothing = error(
  """
  Uh-oh! At least a developer must be provided for your module. Please add developer information in the `mavenPublish` block within the `winds` extension.
  If you think this is an error, please [create an issue](https://github.com/teogor/winds) to assist in resolving this matter.
  Be sure to include the following error ID in your report to help us identify and address the issue:
  ${ErrorId.PomDeveloperError.getErrorIdString()}
  Thank you for your contribution to improving Winds!
  """.trimIndent(),
)

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
 * Collects information about all the modules in the project.
 *
 * @param onModuleInfo A callback that will be called for each module.
 */
inline fun Project.collectModulesInfo(
  crossinline onModuleInfo: (ModuleInfo) -> Unit,
) {
  afterWindsPluginConfiguration {
    val winds: Winds by extensions
    val mavenPublish = winds.mavenPublish
    val docsGenerator = winds.docsGenerator

    val dependencies = when (docsGenerator.dependencyGatheringType) {
      DependencyType.NONE -> emptyList()
      DependencyType.LOCAL -> getAllDependencies().filterIsInstance<LocalProjectDependency>()
      DependencyType.ALL -> getAllDependencies()
    }

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
