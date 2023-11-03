package dev.teogor.winds.api.model

data class BomOptions(
  var acceptedModules: List<String> = emptyList(),
  var acceptedPaths: List<String> = emptyList(),
)
