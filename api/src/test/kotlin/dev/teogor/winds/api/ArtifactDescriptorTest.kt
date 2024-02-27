/*
 * Copyright 2024 teogor (Teodor Grigor)
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

package dev.teogor.winds.api

import dev.teogor.winds.api.model.Version
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArtifactDescriptorTest {

  @Test
  fun `should correctly initialize properties`() {
    val spec = ArtifactDescriptor(
      group = "com.example",
      name = "lib",
      version = Version.from("1.0.0")!!,
    )

    assertEquals("com.example", spec.group)
    assertEquals("lib", spec.name)
    assertEquals("lib", spec.artifactId)
    assertEquals(Version.from("1.0.0"), spec.version)
  }

  @Test
  fun `should create nested dependencies`() {
    val winds = ArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "winds",
      version = Version.from("1.0.0")!!,
    )
    val core = ArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "core",
      version = Version.from("1.2.3")!!,
    )
    val data = ArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "data",
      version = Version.from("4.5.6")!!,
    )
    data.nameFormat = NameFormat.FULL
    data.artifactIdFormat = ArtifactIdFormat.FULL
    data.addArtifact(core, winds)

    assertEquals("dev.teogor.winds", data.group)
    assertEquals("data", data.name)
    assertEquals("winds-core-data", data.artifactId)
    assertEquals(Version.from("4.5.6"), data.version)
    assertEquals(listOf(data, core, winds), data.artifacts)
  }

  @Test
  fun `should create nested dependencies and test artifactId formats`() {
    val data = ArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "data",
      version = Version.from("4.5.6")!!,
    )

    // Test each ArtifactIdFormat
    val specs = ArtifactIdFormat.values().map { format ->
      val dataSpecs = data.copy()
      val root = ArtifactDescriptor(
        group = "dev.teogor.winds",
        name = "winds",
        version = Version.from("1.0.0")!!,
      )
      val core = ArtifactDescriptor(
        "dev.teogor.winds",
        name = "core",
        version = Version.from("1.2.3")!!,
      )
      dataSpecs.artifactIdFormat = format
      dataSpecs.addArtifact(core, root)
      dataSpecs
    }

    for (spec in specs) {
      val expectedArtifactId = when (spec.artifactIdFormat) {
        ArtifactIdFormat.FULL -> "winds-core-data"
        ArtifactIdFormat.GROUP_MODULE_NAME -> "winds-core-data"
        ArtifactIdFormat.MODULE_NAME_ONLY -> "core-data"
        ArtifactIdFormat.NAME_ONLY -> "data"
      }

      assertEquals("dev.teogor.winds", spec.group)
      assertEquals("data", spec.name)
      assertEquals(
        expectedArtifactId,
        spec.artifactId,
        "Failed for ArtifactIdFormat: ${spec.artifactIdFormat}",
      )
      assertEquals(Version.from("4.5.6"), spec.version)
    }
  }

  @Test
  fun `should generate expected artifactId based on format settings`() {
    val winds = ArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "winds",
      version = Version.from("1.0.0")!!,
    )
    val core = ArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "core",
      version = Version.from("1.2.3")!!,
    )
    val data = ArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "data",
      version = Version.from("4.5.6")!!,
    )
    data.addArtifact(core, winds)

    data.artifactIdFormat = ArtifactIdFormat.FULL
    assertEquals("winds-core-data", data.artifactId)

    data.artifactIdFormat = ArtifactIdFormat.GROUP_MODULE_NAME
    assertEquals("winds-core-data", data.artifactId)

    data.artifactIdFormat = ArtifactIdFormat.MODULE_NAME_ONLY
    assertEquals("core-data", data.artifactId)

    data.artifactIdFormat = ArtifactIdFormat.NAME_ONLY
    assertEquals("data", data.artifactId)
  }

  @Test
  fun `should generate expected completeName based on format settings`() {
    val winds = ArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "winds",
      version = Version.from("1.0.0")!!,
    )
    val core = ArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "core",
      version = Version.from("1.2.3")!!,
    )
    val data = ArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "data",
      version = Version.from("4.5.6")!!,
    )
    data.addArtifact(core, winds)

    data.nameFormat = NameFormat.FULL
    assertEquals("Winds Core Data", data.completeName)

    data.nameFormat = NameFormat.GROUP_MODULE_NAME
    assertEquals("Winds Core Data", data.completeName)

    data.nameFormat = NameFormat.MODULE_NAME_ONLY
    assertEquals("Core Data", data.completeName)

    data.nameFormat = NameFormat.NAME_ONLY
    assertEquals("Data", data.completeName)
  }
}
