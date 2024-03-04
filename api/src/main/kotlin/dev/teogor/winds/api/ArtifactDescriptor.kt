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

package dev.teogor.winds.api

import dev.teogor.winds.api.model.Version
import dev.teogor.winds.ktx.validateAndFormatArtifactId
import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
data class ArtifactDescriptor(
  var group: String,
  var name: String,
  var version: Version,
  var artifactIdFormat: ArtifactIdFormat = ArtifactIdFormat.FULL,
  var nameFormat: NameFormat = NameFormat.FULL,
) {
  private val _artifacts: MutableList<ArtifactDescriptor> = mutableListOf()
  val artifacts: List<ArtifactDescriptor>
    get() = (_artifacts.asReversed() + this).reversed()

  fun addArtifact(vararg artifact: ArtifactDescriptor) {
    _artifacts.addAll(artifact)
  }

  val artifactNames: List<String>
    get() = (_artifacts.map { it.name }.asReversed() + name)

  val completeName: String
    get() {
      val names = artifacts.asReversed().map { it.name }

      return when (nameFormat) {
        NameFormat.FULL,
        NameFormat.GROUP_MODULE_NAME,
        NameFormat.MODULE_NAME_ONLY,
        -> {
          val elementsToUse: List<String> = when (nameFormat) {
            NameFormat.FULL -> names
            NameFormat.GROUP_MODULE_NAME -> names.takeLast(3)
            NameFormat.MODULE_NAME_ONLY -> names.takeLast(2)
            else -> error("Unexpected format: $nameFormat")
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

  val artifactId: String
    get() = buildArtifactId()

  val module: String
    get() = "$group:$artifactId"

  val coordinates: String
    get() = "$group:$artifactId:$version"

  private fun buildArtifactId(
    separator: String = "-",
  ): String {
    val safeArtifacts = artifactNames.map {
      it.replace(" ", separator)
        .replace(".", separator)
    }.map { it.validateAndFormatArtifactId() }

    return when (artifactIdFormat) {
      ArtifactIdFormat.FULL,
      ArtifactIdFormat.GROUP_MODULE_NAME,
      ArtifactIdFormat.MODULE_NAME_ONLY,
      -> {
        val elementsToUse: List<String> = when (artifactIdFormat) {
          ArtifactIdFormat.FULL -> safeArtifacts
          ArtifactIdFormat.GROUP_MODULE_NAME -> safeArtifacts.takeLast(3)
          ArtifactIdFormat.MODULE_NAME_ONLY -> safeArtifacts.takeLast(2)
          else -> error("Unexpected format: $artifactIdFormat")
        }

        elementsToUse.distinct().joinToString(separator = separator)
      }

      ArtifactIdFormat.NAME_ONLY -> {
        safeArtifacts.lastOrNull() ?: "NO_ARTIFACT"
      }
    }.lowercase()
  }
}
