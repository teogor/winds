package dev.teogor.winds.api.model

import kotlinx.serialization.Serializable

/**
 * Represents a module in a project.

 * @param name The name of the module.
 * @param displayName The display name of the module.
 * @param description The description of the module.
 * @param groupId The group ID of the module.
 * @param artifactId The artifact ID of the module.
 * @param version The version of the module.
 * @param path The path to the module directory.
 * @param dependencies The dependencies of the module.
 */
@Serializable
data class ModuleInfo(
  val completeName: String,
  val name: String,
  val displayName: String,
  val description: String,
  val groupId: String,
  val artifactId: String,
  val version: Version,
  val path: String,
  val dependencies: List<Dependency>,
  val canBePublished: Boolean,
  val names: List<String>,
) {
  val gradleDependency = "$groupId:$artifactId"
  val coordinates = "$groupId:$artifactId:$version"

  val localPath = path.replace(":", "/")
  val isBoM = artifactId.contains("bom")
  val module = path.split(":")[1]
}
