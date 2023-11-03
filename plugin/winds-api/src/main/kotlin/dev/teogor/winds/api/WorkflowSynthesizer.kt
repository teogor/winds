package dev.teogor.winds.api

interface WorkflowSynthesizer : TaskBuilder {
  var name: String
  var identifier: String

  val excludedModules: List<String>
  fun excludeModules(block: () -> List<String>)
}
