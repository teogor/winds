package dev.teogor.winds.gradle.tasks.impl

import dev.teogor.winds.api.WorkflowSynthesizer
import dev.teogor.winds.api.model.ModuleInfo
import dev.teogor.winds.gradle.tasks.BaseGeneratorTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.provideDelegate

abstract class WorkflowSynthesizerTask : BaseGeneratorTask(
  description = "Generates and orchestrates workflows for the project to publish to maven.",
) {

  private lateinit var workflowSynthesizer: WorkflowSynthesizer
  private val libraries = mutableListOf<ModuleInfo>()

  private fun MutableList<ModuleInfo>.bom() = this.firstOrNull {
    it.isBoM
  }

  private val alphaEmoji = "\uD83E\uDDEA"
  private val betaEmoji = "\uD83D\uDEE0\uFE0F"
  private val deprecatedEmoji = "\uD83D\uDEA7"

  private val bomLibrary by lazy {
    libraries.bom() ?: error("attempting to get bom when it is not available")
  }

  private val hasBoM by lazy { libraries.bom() != null }

  @TaskAction
  override fun action() {
    libraries
      .filter {
        it.canBePublished
      }
      .forEach {
        if (it.isBoM) {
          val name = it.names.last()
            .replace(" ", "")
            .replaceFirstChar { it.titlecase() }
          val taskName = "publish${name}ToMaven"

          // BoM
          // println("BoM($name) @ ${taskName} ${it.names}")
        } else {
          val name = if (it.names.size >= 2) {
            it.names[it.names.size - 2]
          } else {
            it.names.last()
          }.replace(" ", "")
            .replaceFirstChar { it.titlecase() }
          val taskName = "publish${name}ToMaven"

          // Module pr Library
          // println("Lib($name) @ ${taskName}")
        }
      }
  }

  fun addLibrary(data: ModuleInfo) {
    libraries.add(data)
  }

  @Internal
  fun getLibs() = libraries

  fun provideWorkflowSynthesizer(workflowSynthesizer: WorkflowSynthesizer) {
    this.workflowSynthesizer = workflowSynthesizer
  }
}
