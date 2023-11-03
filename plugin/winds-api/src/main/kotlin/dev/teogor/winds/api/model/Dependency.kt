package dev.teogor.winds.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Dependency(
  val implementationType: String,
  val group: String,
  val artifact: String,
  val version: String,
)
