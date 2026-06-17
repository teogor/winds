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

package dev.teogor.winds.api.impl

import dev.teogor.winds.api.ArtifactDescriptor
import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.NameFormat
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.ktx.validateAndFormatArtifactId
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import java.util.Locale
import javax.inject.Inject

open class ArtifactDescriptorImpl @Inject constructor(
  objects: ObjectFactory,
) : ArtifactDescriptor {
  override val group: Property<String> = objects.property(String::class.java).convention("")
  override val name: Property<String> = objects.property(String::class.java).convention("")
  override val version: Property<Version> = objects.property(
    Version::class.java,
  ).convention(Version(0, 0, 0))
  override val artifactIdFormat: Property<ArtifactIdFormat> = objects.property(
    ArtifactIdFormat::class.java,
  ).convention(ArtifactIdFormat.FULL)
  override val nameFormat: Property<NameFormat> = objects.property(
    NameFormat::class.java,
  ).convention(NameFormat.FULL)

  private val parents: ListProperty<ArtifactDescriptor> = objects.listProperty(
    ArtifactDescriptor::class.java,
  ).convention(emptyList())

  override val artifacts: List<ArtifactDescriptor>
    get() = (parents.get().asReversed() + this).reversed()

  override fun addArtifact(vararg artifact: ArtifactDescriptor) {
    parents.addAll(*artifact)
  }

  override val artifactNames: List<String>
    get() = (parents.get().map { it.name.get() }.asReversed() + name.get())

  override val completeName: String
    get() {
      val names = artifacts.asReversed().map { it.name.get() }

      return when (nameFormat.get()) {
        NameFormat.FULL,
        NameFormat.GROUP_MODULE_NAME,
        NameFormat.MODULE_NAME_ONLY,
        -> {
          val elementsToUse: List<String> = when (nameFormat.get()) {
            NameFormat.FULL -> names
            NameFormat.GROUP_MODULE_NAME -> names.takeLast(3)
            NameFormat.MODULE_NAME_ONLY -> names.takeLast(2)
            else -> error("Unexpected format: ${nameFormat.get()}")
          }

          elementsToUse.distinct().joinToString(separator = " ").capitalizeWords()
        }

        NameFormat.NAME_ONLY -> {
          names.lastOrNull()?.capitalizeWords() ?: "NO_NAME"
        }
      }
    }

  private fun String.capitalizeWords(): String {
    return split(" ").joinToString(" ") {
      it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.US) else it.toString() }
    }
  }

  override val artifactId: String
    get() = buildArtifactId()

  override val module: String
    get() = "${group.get()}:$artifactId"

  override val coordinates: String
    get() = "${group.get()}:$artifactId:${version.get()}"

  private fun buildArtifactId(
    separator: String = "-",
  ): String {
    val safeArtifacts = artifactNames.map {
      it.replace(" ", separator)
        .replace(".", separator)
    }.map { it.validateAndFormatArtifactId() }

    return when (artifactIdFormat.get()) {
      ArtifactIdFormat.FULL,
      ArtifactIdFormat.GROUP_MODULE_NAME,
      ArtifactIdFormat.MODULE_NAME_ONLY,
      -> {
        val elementsToUse: List<String> = when (artifactIdFormat.get()) {
          ArtifactIdFormat.FULL -> safeArtifacts
          ArtifactIdFormat.GROUP_MODULE_NAME -> safeArtifacts.takeLast(3)
          ArtifactIdFormat.MODULE_NAME_ONLY -> safeArtifacts.takeLast(2)
          else -> error("Unexpected format: ${artifactIdFormat.get()}")
        }

        elementsToUse.distinct().joinToString(separator = separator)
      }

      ArtifactIdFormat.NAME_ONLY -> {
        safeArtifacts.lastOrNull() ?: "NO_ARTIFACT"
      }
    }.lowercase()
  }

  override fun copy(): ArtifactDescriptor {
    // TODO properly implement copy
    return this
  }
}
