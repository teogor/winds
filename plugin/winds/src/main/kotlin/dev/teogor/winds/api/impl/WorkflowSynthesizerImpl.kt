package dev.teogor.winds.api.impl

import dev.teogor.winds.api.WorkflowSynthesizer
import org.gradle.api.DefaultTask

class WorkflowSynthesizerImpl : WorkflowSynthesizer {

  override var name: String = ""

  override var identifier: String = ""

  override var task: DefaultTask? = null

  override var excludedModules: MutableList<String> = mutableListOf()

  override fun excludeModules(block: () -> List<String>) {
    excludedModules.addAll(block())
  }
}
