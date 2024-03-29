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

import dev.teogor.winds.api.model.ModuleDescriptor

fun Iterable<ModuleDescriptor>.groupByModule(): Map<String?, List<ModuleDescriptor>> {
  return groupBy {
    it.dependencies.firstOrNull()?.name
  }
}

fun Iterable<ModuleDescriptor>.groupModulesByDependency(): Map<ModuleDescriptor, List<ModuleDescriptor>> {
  return groupBy {
    it.dependencies.firstOrNull()?.module
  }.mapKeys { (key, _) ->
    if (key != null) {
      val baseModule = firstOrNull { it.artifact.module == key }
      baseModule
    } else {
      null
    }
  }.filterKeys {
    it != null
  }.mapKeys { (key, _) ->
    key!!
  }
}
