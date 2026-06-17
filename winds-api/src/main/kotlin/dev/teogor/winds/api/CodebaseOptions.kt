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

interface CodebaseOptions {
  val dokka: DokkaOptions
  val spotless: SpotlessOptions
  val binaryCompatibility: BinaryCompatibility

  fun dokka(configure: DokkaOptions.() -> Unit)
  fun spotless(configure: SpotlessOptions.() -> Unit)
  fun binaryCompatibility(configure: BinaryCompatibility.() -> Unit)
}

// Data classes for specific tool options
data class DokkaOptions(
  var enabled: Boolean = false,
  var outputDirectory: String = "",
  var sourceDirectories: List<String> = emptyList(),
  var additionalIncludes: List<String> = emptyList(),
)

data class SpotlessOptions(
  var enabled: Boolean = false,
  var format: String = "",
  var includeFiles: List<String> = emptyList(),
  var excludeFiles: List<String> = emptyList(),
)

data class BinaryCompatibility(
  var enabled: Boolean = false,
  var baselineVersion: String = "",
  var failOnIncompatibleChanges: Boolean = false,
)
