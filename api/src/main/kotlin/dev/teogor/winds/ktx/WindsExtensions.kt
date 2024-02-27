/*
 * Copyright 2024 teogor (Teodor Grigor)
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

package dev.teogor.winds.ktx

import dev.teogor.winds.api.CodebaseOptions
import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.DocumentationBuilder
import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.api.PublishingOptions
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.WindsFeatures
import dev.teogor.winds.api.WorkflowSynthesizer
import dev.teogor.winds.api.model.Version
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import kotlin.reflect.KProperty

fun Project.hasWindsPlugin(): Boolean {
  return project.plugins.hasPlugin("dev.teogor.winds")
}

inline fun Project.processProjectsWithWinds(
  includeRoot: Boolean = false,
  crossinline action: Project.() -> Unit,
) {
  allprojects
    .toList()
    .filter { hasWindsPlugin() && (includeRoot || !it.isRootProject()) }
    .forEach {
      it.action()
    }
}

fun Project.inheritFromParentWinds(winds: Winds) {
  val parentWinds = parent?.extensions?.findByType<Winds>()
  if (parentWinds != null) {
    winds.moduleMetadata = winds.moduleMetadata.copy(
      fromObj = parentWinds.moduleMetadata,
    )

    val dependencySpec = parentWinds.moduleMetadata.artifactDescriptor
    winds.allSpecs.addAll(parentWinds.allSpecs.toList().asReversed())
    if (dependencySpec != null) {
      winds.allSpecs.add(0, dependencySpec)
    }
    winds.moduleMetadata.artifactDescriptor {
      addArtifact(*winds.allSpecs.toTypedArray())
    }
  }
}

inline operator fun <reified T> Winds.getValue(thisRef: Nothing?, property: KProperty<*>): T {
  return when (T::class) {
    WindsFeatures::class -> windsFeatures as T
    DocumentationBuilder::class -> documentationBuilder as T
    ModuleMetadata::class -> moduleMetadata as T
    PublishingOptions::class -> publishingOptions as T
    DocsGenerator::class -> docsGenerator as T
    WorkflowSynthesizer::class -> workflowSynthesizer as T
    CodebaseOptions::class -> codebaseOptions as T
    else -> throw IllegalArgumentException("Unsupported property type: ${property.returnType}")
  }
}

fun ModuleMetadata.copyVersion(
  block: Version.VersionBuilder.() -> Unit = {},
): Version {
  return artifactDescriptor?.version!!.toBuilder().apply(block).build()
}
