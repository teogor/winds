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

import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.model.DependencyType
import org.gradle.api.DefaultTask

class DocsGeneratorImpl : DocsGenerator {

  override var name: String = ""

  override var identifier: String = ""

  override var alertOnDependentModules: Boolean = true

  override var task: DefaultTask? = null

  override var excludedModules: MutableList<String> = mutableListOf()

  override var dependencyGatheringType: DependencyType = DependencyType.NONE

  override fun excludeModules(block: () -> List<String>) {
    excludedModules.addAll(block())
  }
}
