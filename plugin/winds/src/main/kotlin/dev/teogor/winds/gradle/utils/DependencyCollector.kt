/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.teogor.winds.gradle.utils;

import dev.teogor.winds.api.model.Dependency
import dev.teogor.winds.api.model.DependencyDefinition
import dev.teogor.winds.api.model.LocalProjectDependency
import dev.teogor.winds.gradle.model.CollectedContainer
import java.io.File
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ModuleIdentifier
import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.api.artifacts.ResolvedDependency
import org.gradle.api.artifacts.ResolvedModuleVersion
import org.gradle.api.artifacts.component.ComponentArtifactIdentifier
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.api.artifacts.component.ProjectComponentIdentifier
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency
import org.slf4j.LoggerFactory

/**
 * Enable the inclusion of platform dependencies in the report.
 * By default `platform` level `bom` specifications will be included in the report.
 *
 * > Gradle provides support for importing bill of materials (BOM) files, which are effectively .pom files that use <dependencyManagement> to control the dependency versions of direct and transitive dependencies. The BOM support in Gradle works similar to using <scope>import</scope> when depending on a BOM in Maven.
 *
 * ```
 * aboutLibraries {
 *      includePlatform = false
 * }
 * ```
 */
var includePlatform: Boolean = true

/**
 * Defines the variants to keep during the "collectDependencies" step.
 *
 * ```
 * aboutLibraries {
 *   filterVariants = arrayOf("debug")
 * }
 * ```
 */
var filterVariants: Array<String> = emptyArray()

class DependencyCollector(
  private val includePlatform: Boolean = true,
  private val filterVariants: Array<String> = emptyArray(),
) {
  fun collect(project: Project): CollectedContainer {
    LOGGER.info("Collecting dependencies")

    val mutableCollectContainer: MutableMap<String, MutableMap<String, MutableSet<DependencyDefinition>>> =
      sortedMapOf(compareBy<String> { it })

    println("***** Collecting dependencies BETA *****")
    println("***** ${project.path} *****")

    project.configurations
      .filterNot { configuration ->
        configuration.shouldSkip() && !configuration.isProjectDependency()
      }
      .mapNotNull {
        val cn = it.name
        if (cn.endsWith("CompileClasspath", true)) {
          val variant = cn.removeSuffix("CompileClasspath")
          if (filterVariants.isEmpty() || filterVariants.contains(variant)) {
            LOGGER.info("Collecting dependencies for compile time variant $variant from config: ${it.name}")
            return@mapNotNull variant to it
          } else {
            LOGGER.info("Skipping compile time variant $variant from config: ${it.name}")
          }
        } else if (cn.endsWith("RuntimeClasspath", true)) {
          val variant = cn.removeSuffix("RuntimeClasspath")
          if (filterVariants.isEmpty() || filterVariants.contains(variant)) {
            LOGGER.info("Collecting dependencies for runtime variant $variant from config: ${it.name}")
            return@mapNotNull variant to it
          } else {
            LOGGER.info("Skipping compile time variant $variant from config: ${it.name}")
          }
        }

        null
      }
      .forEach { (variant, configuration) ->
        val variantSet = mutableCollectContainer.getOrPut(variant) {
          sortedMapOf(compareBy<String> { it })
        }
        val visitedDependencyNames = mutableSetOf<String>()

        println("Pre-fetching dependencies for $variant")
        configuration
          .resolvedConfiguration
          .lenientConfiguration
          .allModuleDependencies
          .getResolvedArtifacts(visitedDependencyNames)
          .forEach { resArtifact ->
            val identifier = "${resArtifact.moduleVersion.id.group.trim()}:${resArtifact.name.trim()}"
            val dependencyDefinition = if (isLocalProjectArtifact(resArtifact)) {
              val projectIdentifier = resArtifact.id.componentIdentifier as ProjectComponentIdentifier
              LocalProjectDependency(
                implementationType = "local",
                projectName = projectIdentifier.projectName,
                modulePath = projectIdentifier.projectPath,
              )
            } else {
              Dependency(
                implementationType = "external",
                group = resArtifact.moduleVersion.id.group,
                artifact = resArtifact.name,
                version = resArtifact.moduleVersion.id.version
              ).also {
                println(it)
              }
            }
            println("Retrieved for $variant :: $identifier ${resArtifact.moduleVersion.id}")
            val versions = variantSet.getOrPut(identifier) { LinkedHashSet() }
            versions.add(dependencyDefinition)
          }

        println("Completed-fetching dependencies for $variant")
      }
    return CollectedContainer(mutableCollectContainer)
  }

  private fun isLocalProjectArtifact(artifact: ResolvedArtifact): Boolean {
    val moduleVersion = artifact.id

    if (moduleVersion.componentIdentifier is ProjectComponentIdentifier) {
      val projectIdentifier = moduleVersion.componentIdentifier as ProjectComponentIdentifier
      println("Artifact is a local project: ${projectIdentifier.projectPath}")
      return true
    }

    return false
  }

  private fun Set<ResolvedDependency>.getResolvedArtifacts(
    visitedDependencyNames: MutableSet<String>,
  ): Set<ResolvedArtifact> {
    val resolvedArtifacts = mutableSetOf<ResolvedArtifact>()
    for (resolvedDependency in this) {
      var isLocal = false
      resolvedDependency.moduleArtifacts.forEach {
        if (isLocalProjectArtifact(it)) {
          resolvedArtifacts += it
          isLocal = true
        }
      }
      val name = resolvedDependency.name
      if (name !in visitedDependencyNames) {
        visitedDependencyNames += name

        println("handling resolved artifact :: $name")

        try {
          resolvedArtifacts += when {
            resolvedDependency.moduleVersion == "unspecified" -> {
              println("artifact has unspecified version ${resolvedDependency.toResolvedBomArtifact()}")
              resolvedDependency.children.getResolvedArtifacts(
                visitedDependencyNames = visitedDependencyNames,
              )
            }

            includePlatform && resolvedDependency.isPlatform -> {
              println("artifact is platform")
              setOf(resolvedDependency.toResolvedBomArtifact())
            }

            else -> {
              println("retrieve allModuleArtifacts from artifact")
              resolvedDependency.allModuleArtifacts
            }
          }
        } catch (e: Throwable) {
          LOGGER.warn("Found ambiguous variant - $resolvedDependency", e)
        }
      }
    }

    return resolvedArtifacts
  }

  private fun Configuration.shouldSkip(): Boolean {
    return !isCanBeResolved || isTest
  }

  private fun Configuration.isProjectDependency(): Boolean {
    return dependencies.any { it is DefaultProjectDependency }
  }

  private val testCompile = setOf("testCompile", "androidTestCompile")
  private val Configuration.isTest
    get() = name.startsWith("test", ignoreCase = true) ||
      name.startsWith("androidTest", ignoreCase = true) ||
      hierarchy.any { configurationHierarchy ->
        testCompile.any { configurationHierarchy.name.contains(it, ignoreCase = true) }
      }

  private val platform = "platform"

  private val ResolvedDependency.isPlatform
    get() = configuration.contains(platform)

  private companion object {
    private val LOGGER = LoggerFactory.getLogger(DependencyCollector::class.java)!!
  }
}

/**
 * Convenient helper to wrap a [ResolvedDependency] into a []ResolvedArtifact]
 * Required to handle `platform` dependencies.
 */
internal fun ResolvedDependency.toResolvedBomArtifact() = object : ResolvedArtifact {
  override fun getFile(): File = File("")
  override fun getModuleVersion(): ResolvedModuleVersion = module
  override fun getName(): String = this@toResolvedBomArtifact.moduleName
  override fun getType(): String = "platform"
  override fun getExtension(): String = "pom"
  override fun getClassifier(): String? = null
  override fun getId() = object : ComponentArtifactIdentifier {
    override fun getComponentIdentifier() = object : ModuleComponentIdentifier {
      override fun getDisplayName(): String = this@toResolvedBomArtifact.module.id.toString()
      override fun getGroup(): String = this@toResolvedBomArtifact.module.id.module.group
      override fun getModule(): String = this@toResolvedBomArtifact.module.id.module.name
      override fun getVersion(): String = this@toResolvedBomArtifact.module.id.version
      override fun getModuleIdentifier(): ModuleIdentifier =
        this@toResolvedBomArtifact.module.id.module
    }

    override fun getDisplayName(): String = module.id.toString()
    override fun toString(): String = displayName
  }

  override fun toString(): String = id.displayName
}
