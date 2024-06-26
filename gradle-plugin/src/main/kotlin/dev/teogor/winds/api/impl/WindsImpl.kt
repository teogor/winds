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

import com.vanniktech.maven.publish.SonatypeHost
import dev.teogor.winds.api.ArtifactDescriptor
import dev.teogor.winds.api.CodebaseOptions
import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.DocumentationBuilder
import dev.teogor.winds.api.Features
import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.api.Publishing
import dev.teogor.winds.api.PublishingOptions
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.WindsFeatures
import dev.teogor.winds.api.WorkflowSynthesizer
import dev.teogor.winds.gradle.WindsPlugin
import dev.teogor.winds.ktx.processProjectsWithWinds
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType

abstract class WindsImpl(
  private val project: Project,
) : Winds {
  override val allSpecs: MutableList<ArtifactDescriptor> = mutableListOf()

  override var features: Features = FeaturesImpl()

  override fun features(action: Features.() -> Unit) {
    features = features.apply(action)
  }

  @Suppress("DEPRECATION")
  @Deprecated(
    message = "Use features instead.",
    replaceWith = ReplaceWith("features"),
  )
  override var windsFeatures: WindsFeatures = WindsFeaturesImpl()

  @Suppress("DEPRECATION")
  @Deprecated(
    message = "Use features(action) instead.",
    replaceWith = ReplaceWith("features(action)"),
  )
  override fun windsFeatures(action: WindsFeatures.() -> Unit) {
    windsFeatures = windsFeatures.apply(action)
    with(windsFeatures) {
      features.mavenPublishing = mavenPublishing
      features.docsGenerator = docsGenerator
      features.workflowSynthesizer = workflowSynthesizer
    }
  }

  override var moduleMetadata: ModuleMetadata = ModuleMetadataImpl()

  override fun moduleMetadata(action: ModuleMetadata.() -> Unit) {
    moduleMetadata = moduleMetadata.apply(action)
  }

  override var publishing: Publishing = PublishingImpl()

  override fun publishing(action: Publishing.() -> Unit) {
    publishing = publishing.apply(action)
  }

  @Suppress("DEPRECATION")
  @Deprecated(
    message = "Use publishing instead.",
    replaceWith = ReplaceWith("publishing"),
  )
  override var publishingOptions: PublishingOptions = PublishingOptionsImpl()

  @Suppress("DEPRECATION")
  @Deprecated(
    message = "Use publishing(action) instead.",
    replaceWith = ReplaceWith("publishing(action)"),
  )
  override fun publishingOptions(action: PublishingOptions.() -> Unit) {
    publishingOptions = publishingOptions.apply(action)
    with(publishingOptions) {
      publishing.enabled = publish
      publishing.cascade = cascadePublish
      publishing.sonatypeHost = when (sonatypeHost) {
        SonatypeHost.CENTRAL_PORTAL -> dev.teogor.winds.api.SonatypeHost.CENTRAL_PORTAL
        SonatypeHost.S01 -> dev.teogor.winds.api.SonatypeHost.S01
        else -> dev.teogor.winds.api.SonatypeHost.DEFAULT
      }
      publishing.optInForVanniktechPlugin = optInForVanniktechPlugin
      publishing.enablePublicationSigning = enablePublicationSigning
    }
  }

  override var docsGenerator: DocsGenerator = DocsGeneratorImpl()

  override fun docsGenerator(action: DocsGenerator.() -> Unit) {
    docsGenerator = docsGenerator.apply(action)
  }

  override var documentationBuilder: DocumentationBuilder = DocumentationBuilderImpl()

  override fun documentationBuilder(configure: DocumentationBuilder.() -> Unit) {
    documentationBuilder = documentationBuilder.apply(configure)
  }

  override var workflowSynthesizer: WorkflowSynthesizer = WorkflowSynthesizerImpl()

  override fun workflowSynthesizer(action: WorkflowSynthesizer.() -> Unit) {
    workflowSynthesizer = workflowSynthesizer.apply(action)
  }

  override var codebaseOptions: CodebaseOptions = CodebaseOptionsImpl()

  override fun codebaseOptions(configure: CodebaseOptions.() -> Unit) {
    codebaseOptions = codebaseOptions.apply(configure)
  }

  override fun configureProjects(
    includeRoot: Boolean,
    action: Project.(Winds) -> Unit,
  ) {
    project.processProjectsWithWinds(includeRoot) {
      plugins.withType<WindsPlugin> {
        if (state.executed) {
          val winds: Winds by extensions
          action(winds)
        } else {
          afterEvaluate {
            val winds: Winds by extensions
            action(winds)
          }
        }
      }
    }
  }
}
