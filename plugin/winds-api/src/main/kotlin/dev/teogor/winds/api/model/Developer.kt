package dev.teogor.winds.api.model

interface Developer {
  val id: String
  val name: String
  val url: String
  val roles: List<String>
  val timezone: String
  val organization: String
  val organizationUrl: String
}

data class DeveloperImpl(
  override var id: String = "",
  override var name: String = "",
  override var url: String = "",
  override var roles: List<String> = emptyList(),
  override var timezone: String = "",
  override var organization: String = "",
  override var organizationUrl: String = "",
): Developer
