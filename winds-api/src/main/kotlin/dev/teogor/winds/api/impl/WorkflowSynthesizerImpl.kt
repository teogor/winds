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

import dev.teogor.winds.api.WorkflowSynthesizer
import org.gradle.api.DefaultTask
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import javax.inject.Inject

open class WorkflowSynthesizerImpl @Inject constructor(
  objects: ObjectFactory,
) : WorkflowSynthesizer {

  override val name: Property<String> = objects.property(String::class.java).convention("")

  override val identifier: Property<String> = objects.property(String::class.java).convention("")

  override var task: DefaultTask? = null

  override val excludedModules: ListProperty<String> = objects.listProperty(
    String::class.java,
  ).convention(emptyList())

  override fun excludeModules(block: () -> List<String>) {
    excludedModules.addAll(block())
  }
}
