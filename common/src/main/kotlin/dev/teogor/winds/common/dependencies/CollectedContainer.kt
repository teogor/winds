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

package dev.teogor.winds.common.dependencies

import dev.teogor.winds.api.model.DependencyDefinition
import org.gradle.api.tasks.Input
import java.io.Serializable

data class CollectedContainer(
  @get:Input val dependencies: Map<String, Map<String, Set<DependencyDefinition>>>,
) : Serializable {

  /**
   * Flattens the nested map structure of dependencies into a list
   * of unique DependencyDefinition elements.
   *
   * The function iterates over the nested maps and sets within the
   * 'dependencies' property of the CollectedContainer, collecting each
   * DependencyDefinition into a list. The resulting list contains only
   * unique elements.
   *
   * @return A list of unique DependencyDefinition elements.
   */
  fun flattenDependencies(): List<DependencyDefinition> {
    val flattenedList = mutableListOf<DependencyDefinition>()

    dependencies.values.forEach { realDependencies ->
      realDependencies.values.forEach { versions ->
        flattenedList.addAll(versions)
      }
    }

    return flattenedList.distinct()
  }
}
