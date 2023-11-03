package dev.teogor.winds.gradle.tasks.impl

import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.getValue
import dev.teogor.winds.api.impl.DocsGeneratorImpl
import dev.teogor.winds.api.impl.MavenPublishImpl
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.api.model.Version
import dev.teogor.winds.api.model.WindsFeature
import dev.teogor.winds.gradle.utils.getAllDependencies
import dev.teogor.winds.gradle.utils.isWindsApplied
import dev.teogor.winds.gradle.utils.lazy
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

fun Project.configureDocsGenerator() {
  lazy {
    val winds: Winds by extensions
    val docsGeneratorFeatureEnabled = winds isEnabled WindsFeature.DOCS_GENERATOR
    val docsGenerator: DocsGenerator by winds
    val docsGeneratorImpl = docsGenerator as DocsGeneratorImpl
    docsGeneratorImpl.task?.isEnabled = docsGeneratorFeatureEnabled
    if (docsGeneratorFeatureEnabled) {
      subprojects {
        if (docsGenerator.excludedModules.contains(this.path)) {
          return@subprojects
        }
        val projectBase = this
        isWindsApplied(lazyStart = true) {
          lazy {
            val windsBase = projectBase.extensions.getByType<Winds>()
            val mavenPublishFeatureEnabled = windsBase isEnabled WindsFeature.MAVEN_PUBLISH
            val mavenPublish = windsBase.mavenPublish
            val moduleInfo = ModuleInfo(
              completeName = mavenPublish.completeName,
              name = mavenPublish.name ?: "",
              displayName = mavenPublish.displayName ?: "",
              description = mavenPublish.description ?: "",
              groupId = mavenPublish.groupId ?: "",
              artifactId = mavenPublish.artifactId ?: "",
              version = mavenPublish.version ?: Version(0, 0, 0),
              path = path,
              dependencies = getAllDependencies(),
              canBePublished = mavenPublish.canBePublished,
              names = (mavenPublish as MavenPublishImpl).gets { displayName },
            )
            (docsGeneratorImpl.task as? DocsGeneratorTask)?.addLibrary(moduleInfo)
          }
        }
      }
    }
  }
}
