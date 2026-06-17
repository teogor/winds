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

package dev.teogor.winds.api.impl

import dev.teogor.winds.api.DocumentationBuilder
import dev.teogor.winds.api.model.DependencyType
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

open class DocumentationBuilderImpl @Inject constructor(
  objects: ObjectFactory,
) : DocumentationBuilder {
  override val htmlPath: Property<String> = objects.property(String::class.java).convention("")
  override val mkdocsEnabled: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(false)

  override val createApiReference: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(true)
  override val generateReleaseTable: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(true)

  override val includeReleaseCandidate: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(true)
  override val includeBetaRelease: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(true)
  override val includeAlphaRelease: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(true)

  override val alertOnDependentModules: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(true)
  override val dependencyGatheringType: Property<DependencyType> = objects.property(
    DependencyType::class.java,
  ).convention(DependencyType.NONE)

  override val isCompiler: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(false)
  override val isOptional: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(false)

  override val pluginIds: ListProperty<String> = objects.listProperty(
    String::class.java,
  ).convention(emptyList())

  override val markdownNewlineSeparator: Property<String> = objects.property(
    String::class.java,
  ).convention("  ")

  override fun copy(from: DocumentationBuilder): DocumentationBuilder {
    htmlPath.set(from.htmlPath)
    mkdocsEnabled.set(from.mkdocsEnabled)

    createApiReference.set(from.createApiReference)
    generateReleaseTable.set(from.generateReleaseTable)

    includeReleaseCandidate.set(from.includeReleaseCandidate)
    includeBetaRelease.set(from.includeBetaRelease)
    includeAlphaRelease.set(from.includeAlphaRelease)

    alertOnDependentModules.set(from.alertOnDependentModules)
    dependencyGatheringType.set(from.dependencyGatheringType)

    isCompiler.set(from.isCompiler)
    isOptional.set(from.isOptional)

    pluginIds.set(from.pluginIds)

    markdownNewlineSeparator.set(from.markdownNewlineSeparator)

    return this
  }
}
