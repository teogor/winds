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

import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

interface CodebaseOptions {
  val dokka: DokkaOptions
  val spotless: SpotlessOptions
  val binaryCompatibility: BinaryCompatibility

  fun dokka(configure: DokkaOptions.() -> Unit)
  fun spotless(configure: SpotlessOptions.() -> Unit)
  fun binaryCompatibility(configure: BinaryCompatibility.() -> Unit)
}

// Interfaces for specific tool options
interface DokkaOptions {
  val enabled: Property<Boolean>
  val outputDirectory: Property<String>
  val sourceDirectories: ListProperty<String>
  val additionalIncludes: ListProperty<String>
}

interface SpotlessOptions {
  val enabled: Property<Boolean>
  val format: Property<String>
  val includeFiles: ListProperty<String>
  val excludeFiles: ListProperty<String>
}

interface BinaryCompatibility {
  val enabled: Property<Boolean>
  val baselineVersion: Property<String>
  val failOnIncompatibleChanges: Property<Boolean>
}
