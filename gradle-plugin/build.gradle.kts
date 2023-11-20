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

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
  alias(libs.plugins.gradle.publish)
  alias(libs.plugins.build.config)
  kotlin("plugin.serialization") version "1.9.10"
  alias(libs.plugins.vanniktech.maven)
}

repositories {
  mavenLocal()
  maven(url = "https://maven.google.com/")
  mavenCentral()
  maven(url = "https://storage.googleapis.com/android-ci/mvn/")
  maven(url = "https://plugins.gradle.org/m2/")
}

val javaVersion = JavaVersion.VERSION_1_8
java {
  sourceCompatibility = javaVersion
  targetCompatibility = javaVersion
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  languageVersion = "1.8"
  jvmTarget = javaVersion.toString()
}

dependencies {
  implementation(project(":api"))
  implementation(project(":common"))

  implementation(libs.kotlinx.serialization.core)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.gson)

  compileOnly(gradleApi())
  compileOnly(libs.kotlin.gradlePlugin)
  compileOnly(libs.ksp.gradlePlugin)
}

@Suppress("UnstableApiUsage")
gradlePlugin {
  website.set("https://source.teogor.dev/winds")
  vcsUrl.set("https://github.com/teogor/winds")

  plugins {
    register("windsPlugin") {
      id = "dev.teogor.winds"
      implementationClass = "dev.teogor.winds.gradle.WindsPlugin"
      displayName = "Winds Plugin"
      description = "Automates project workflows, Maven publishing, and documentation generation."
      tags = listOf("workflow", "Maven", "documentation", "automation", "project-management", "publishing", "reporting")
    }
  }
}

buildConfig {
  packageName("dev.teogor.winds")

  buildConfigField("String", "NAME", "\"${group}\"")
  buildConfigField("String", "VERSION", "\"${version}\"")
}
