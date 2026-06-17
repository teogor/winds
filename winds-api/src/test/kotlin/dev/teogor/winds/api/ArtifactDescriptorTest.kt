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

import dev.teogor.winds.api.impl.ArtifactDescriptorImpl
import dev.teogor.winds.api.model.Version
import org.gradle.api.model.ObjectFactory
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArtifactDescriptorTest {

  private lateinit var objects: ObjectFactory

  @BeforeEach
  fun setup() {
    val project = ProjectBuilder.builder().build()
    objects = project.objects
  }

  private fun createArtifactDescriptor(
    group: String,
    name: String,
    version: Version,
  ): ArtifactDescriptor {
    return objects.newInstance(ArtifactDescriptorImpl::class.java).apply {
      this.group.set(group)
      this.name.set(name)
      this.version.set(version)
    }
  }

  @Test
  fun `should correctly initialize properties`() {
    val spec = createArtifactDescriptor(
      group = "com.example",
      name = "lib",
      version = Version.from("1.0.0")!!,
    )

    assertEquals("com.example", spec.group.get())
    assertEquals("lib", spec.name.get())
    assertEquals("lib", spec.artifactId)
    assertEquals(Version.from("1.0.0"), spec.version.get())
  }

  @Test
  fun `should create nested dependencies`() {
    val winds = createArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "winds",
      version = Version.from("1.0.0")!!,
    )
    val core = createArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "core",
      version = Version.from("1.2.3")!!,
    )
    val data = createArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "data",
      version = Version.from("4.5.6")!!,
    )
    data.nameFormat.set(NameFormat.FULL)
    data.artifactIdFormat.set(ArtifactIdFormat.FULL)
    data.addArtifact(core, winds)

    assertEquals("dev.teogor.winds", data.group.get())
    assertEquals("data", data.name.get())
    assertEquals("winds-core-data", data.artifactId)
    assertEquals(Version.from("4.5.6"), data.version.get())
    assertEquals(listOf(data, core, winds), data.artifacts)
  }

  @Test
  fun `should create nested dependencies and test artifactId formats`() {
    val data = createArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "data",
      version = Version.from("4.5.6")!!,
    )

    // Test each ArtifactIdFormat
    val specs = ArtifactIdFormat.values().map { format ->
      val dataSpecs = createArtifactDescriptor(
        data.group.get(),
        data.name.get(),
        data.version.get(),
      )
      val root = createArtifactDescriptor(
        group = "dev.teogor.winds",
        name = "winds",
        version = Version.from("1.0.0")!!,
      )
      val core = createArtifactDescriptor(
        "dev.teogor.winds",
        name = "core",
        version = Version.from("1.2.3")!!,
      )
      dataSpecs.artifactIdFormat.set(format)
      dataSpecs.addArtifact(core, root)
      dataSpecs
    }

    for (spec in specs) {
      val expectedArtifactId = when (spec.artifactIdFormat.get()) {
        ArtifactIdFormat.FULL -> "winds-core-data"
        ArtifactIdFormat.GROUP_MODULE_NAME -> "winds-core-data"
        ArtifactIdFormat.MODULE_NAME_ONLY -> "core-data"
        ArtifactIdFormat.NAME_ONLY -> "data"
      }

      assertEquals("dev.teogor.winds", spec.group.get())
      assertEquals("data", spec.name.get())
      assertEquals(
        expectedArtifactId,
        spec.artifactId,
        "Failed for ArtifactIdFormat: ${spec.artifactIdFormat.get()}",
      )
      assertEquals(Version.from("4.5.6"), spec.version.get())
    }
  }

  @Test
  fun `should generate expected artifactId based on format settings`() {
    val winds = createArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "winds",
      version = Version.from("1.0.0")!!,
    )
    val core = createArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "core",
      version = Version.from("1.2.3")!!,
    )
    val data = createArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "data",
      version = Version.from("4.5.6")!!,
    )
    data.addArtifact(core, winds)

    data.artifactIdFormat.set(ArtifactIdFormat.FULL)
    assertEquals("winds-core-data", data.artifactId)

    data.artifactIdFormat.set(ArtifactIdFormat.GROUP_MODULE_NAME)
    assertEquals("winds-core-data", data.artifactId)

    data.artifactIdFormat.set(ArtifactIdFormat.MODULE_NAME_ONLY)
    assertEquals("core-data", data.artifactId)

    data.artifactIdFormat.set(ArtifactIdFormat.NAME_ONLY)
    assertEquals("data", data.artifactId)
  }

  @Test
  fun `should generate expected completeName based on format settings`() {
    val winds = createArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "winds",
      version = Version.from("1.0.0")!!,
    )
    val core = createArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "core",
      version = Version.from("1.2.3")!!,
    )
    val data = createArtifactDescriptor(
      group = "dev.teogor.winds",
      name = "data",
      version = Version.from("4.5.6")!!,
    )
    data.addArtifact(core, winds)

    data.nameFormat.set(NameFormat.FULL)
    assertEquals("Winds Core Data", data.completeName)

    data.nameFormat.set(NameFormat.GROUP_MODULE_NAME)
    assertEquals("Winds Core Data", data.completeName)

    data.nameFormat.set(NameFormat.MODULE_NAME_ONLY)
    assertEquals("Core Data", data.completeName)

    data.nameFormat.set(NameFormat.NAME_ONLY)
    assertEquals("Data", data.completeName)
  }
}
