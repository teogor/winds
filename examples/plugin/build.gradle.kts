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

plugins {
  `kotlin-dsl`
  alias(libs.plugins.gradle.publish)
  alias(libs.plugins.build.config)
  alias(libs.plugins.jetbrains.kotlin.serialization)
  id("dev.teogor.winds")
}

@Suppress("UnstableApiUsage")
gradlePlugin {
  plugins {
    register("windsPlugin") {
      id = "dev.teogor.demo"
      implementationClass = "dev.teogor.demo.DemoPlugin"
      displayName = "Winds Demo Plugin"
      description = "Automates project workflows, Maven publishing, and documentation generation."
      tags = listOf("workflow", "Maven", "documentation", "automation", "project-management", "publishing", "reporting")
    }
  }
}

buildConfig {
  packageName(group.toString())

  buildConfigField("String", "NAME", "\"${group}\"")
  buildConfigField("String", "VERSION", "\"${version}\"")
}
