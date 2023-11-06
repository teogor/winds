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
import dev.teogor.winds.api.impl.WindsOptions
import dev.teogor.winds.api.model.Dependency
import dev.teogor.winds.api.model.Developer
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register

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

inline fun <reified T : DefaultTask> WindsOptions.registerTask(
  name: String,
) = project.tasks.register<T>(name)

fun Project.lazy(block: Project.() -> Unit) = afterEvaluate(block)

fun Project.getAllDependencies(): List<Dependency> {
  val dependencies = mutableListOf<Dependency>()

  this.configurations.forEach { configuration ->
    configuration.dependencies.forEach { dependency ->
      dependencies.add(
        Dependency(
          implementationType = configuration.name,
          group = dependency.group ?: "",
          artifact = dependency.name,
          version = dependency.version ?: "",
        ),
      )
    }
  }

  return dependencies
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
      mavenPublish.developers?.toDeveloperSpec(this)
    }

    scm {
      url.set(mavenPublish.scmUrl)
      connection.set(mavenPublish.scmConnection)
      developerConnection.set(mavenPublish.scmDeveloperConnection)
    }
  }
}

fun List<Developer>.toDeveloperSpec(
  mavenPomDeveloperSpec: MavenPomDeveloperSpec,
) {
  forEach { developer ->
    mavenPomDeveloperSpec.developer {
      id.set(developer.id)
      name.set(developer.name)
      url.set(developer.url)
      roles.set(developer.roles)
      timezone.set(developer.timezone)
      organization.set(developer.organization)
      organizationUrl.set(developer.organizationUrl)
    }
  }
}
