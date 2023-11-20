package dev.teogor.winds.common.dependencies

import dev.teogor.winds.api.model.DependencyDefinition
import java.io.Serializable
import org.gradle.api.tasks.Input

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
