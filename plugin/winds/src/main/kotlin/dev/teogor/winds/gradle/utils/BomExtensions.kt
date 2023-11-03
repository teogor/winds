package dev.teogor.winds.gradle.utils

import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.Winds
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyConstraintHandler
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

fun Project.configureBomModule(
  publishOptions: MavenPublish,
) {
  collectBomConstraints(publishOptions)
}

private fun Project.collectBomConstraints(
  publishOptions: MavenPublish,
) {
  val bomConstraints: DependencyConstraintHandler = dependencies.constraints
  val bomOptions = publishOptions.bomOptions ?: error("internal error")

  val bomName = name
  rootProject.subprojects {
    val subproject = this

    if (subproject.name != bomName) {
      subproject.lazy {
        val winds: Winds by extensions
        lazy {
          if (winds.mavenPublish.canBePublished) {
            bomConstraints.api(subproject)
          }
        }
        // todo
        //  val isValidBomLibrary = bomOptions.acceptedModules.contains(subproject.path) ||
        //    bomOptions.acceptedPaths.any { subproject.path.startsWith(it) }
        //  if (subproject.plugins.hasPlugin("dev.teogor.winds")) {
        //    val windsInfo = subproject.extensions.getByType<WindsInfo>()
        //    println("valid subproject ${subproject.path} ${windsInfo.licenses}")
        //  }
        //  if (subproject.hasPublishPlugin()) {
        //    bomConstraints.api(subproject)
        //    println("hasPublishPlugin : ${subproject.path}")
        //  }
        //  if (isValidBomLibrary) {
        //    bomConstraints.api(subproject)
        //    println("subproject : ${subproject.path}")
        //  }
      }
    }
  }
}

private fun DependencyConstraintHandler.api(
  constraintNotation: Any,
) = add("api", constraintNotation)
