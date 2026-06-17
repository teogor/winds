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

import dev.teogor.winds.api.BinaryCompatibility
import dev.teogor.winds.api.CodebaseOptions
import dev.teogor.winds.api.DokkaOptions
import dev.teogor.winds.api.SpotlessOptions
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

open class CodebaseOptionsImpl @Inject constructor(
  objects: ObjectFactory,
) : CodebaseOptions {
  override val dokka: DokkaOptions = objects.newInstance(DokkaOptionsImpl::class.java)
  override val spotless: SpotlessOptions = objects.newInstance(SpotlessOptionsImpl::class.java)
  override val binaryCompatibility: BinaryCompatibility = objects.newInstance(
    BinaryCompatibilityImpl::class.java,
  )

  override fun dokka(configure: DokkaOptions.() -> Unit) {
    dokka.apply(configure)
  }

  override fun spotless(configure: SpotlessOptions.() -> Unit) {
    spotless.apply(configure)
  }

  override fun binaryCompatibility(configure: BinaryCompatibility.() -> Unit) {
    binaryCompatibility.apply(configure)
  }
}

open class DokkaOptionsImpl @Inject constructor(
  objects: ObjectFactory,
) : DokkaOptions {
  override val enabled: Property<Boolean> = objects.property(Boolean::class.java).convention(false)
  override val outputDirectory: Property<String> = objects.property(
    String::class.java,
  ).convention("")
  override val sourceDirectories: ListProperty<String> = objects.listProperty(
    String::class.java,
  ).convention(emptyList())
  override val additionalIncludes: ListProperty<String> = objects.listProperty(
    String::class.java,
  ).convention(emptyList())
}

open class SpotlessOptionsImpl @Inject constructor(
  objects: ObjectFactory,
) : SpotlessOptions {
  override val enabled: Property<Boolean> = objects.property(Boolean::class.java).convention(false)
  override val format: Property<String> = objects.property(String::class.java).convention("")
  override val includeFiles: ListProperty<String> = objects.listProperty(
    String::class.java,
  ).convention(emptyList())
  override val excludeFiles: ListProperty<String> = objects.listProperty(
    String::class.java,
  ).convention(emptyList())
}

open class BinaryCompatibilityImpl @Inject constructor(
  objects: ObjectFactory,
) : BinaryCompatibility {
  override val enabled: Property<Boolean> = objects.property(Boolean::class.java).convention(false)
  override val baselineVersion: Property<String> = objects.property(
    String::class.java,
  ).convention("")
  override val failOnIncompatibleChanges: Property<Boolean> = objects.property(
    Boolean::class.java,
  ).convention(false)
}
