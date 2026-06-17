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

import dev.teogor.winds.api.ArtifactDescriptor
import dev.teogor.winds.api.CodebaseOptions
import dev.teogor.winds.api.DocsGenerator
import dev.teogor.winds.api.DocumentationBuilder
import dev.teogor.winds.api.Features
import dev.teogor.winds.api.ModuleMetadata
import dev.teogor.winds.api.Publishing
import dev.teogor.winds.api.Winds
import dev.teogor.winds.api.WorkflowSynthesizer
import dev.teogor.winds.gradle.WindsPlugin
import dev.teogor.winds.ktx.processProjectsWithWinds
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import javax.inject.Inject

abstract class WindsImpl @Inject constructor(
  private val project: Project,
) : Winds {
  private val objects: ObjectFactory = project.objects

  override val allSpecs: ListProperty<ArtifactDescriptor> = objects.listProperty(
    ArtifactDescriptor::class.java,
  ).convention(emptyList())

  override val features: Features = objects.newInstance(FeaturesImpl::class.java)

  override fun features(action: Features.() -> Unit) {
    features.apply(action)
  }

  override val moduleMetadata: ModuleMetadata = objects.newInstance(ModuleMetadataImpl::class.java)

  override fun moduleMetadata(action: ModuleMetadata.() -> Unit) {
    moduleMetadata.apply(action)
  }

  override val publishing: Publishing = objects.newInstance(PublishingImpl::class.java)

  override fun publishing(action: Publishing.() -> Unit) {
    publishing.apply(action)
  }

  override val docsGenerator: DocsGenerator = objects.newInstance(DocsGeneratorImpl::class.java)

  override fun docsGenerator(action: DocsGenerator.() -> Unit) {
    docsGenerator.apply(action)
  }

  override val documentationBuilder: DocumentationBuilder = objects.newInstance(
    DocumentationBuilderImpl::class.java,
  )

  override fun documentationBuilder(configure: DocumentationBuilder.() -> Unit) {
    documentationBuilder.apply(configure)
  }

  override val workflowSynthesizer: WorkflowSynthesizer = objects.newInstance(
    WorkflowSynthesizerImpl::class.java,
  )

  override fun workflowSynthesizer(action: WorkflowSynthesizer.() -> Unit) {
    workflowSynthesizer.apply(action)
  }

  override val codebaseOptions: CodebaseOptions = objects.newInstance(
    CodebaseOptionsImpl::class.java,
  )

  override fun codebaseOptions(configure: CodebaseOptions.() -> Unit) {
    codebaseOptions.apply(configure)
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
