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
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  // TODO dsl -> can we publish this as API?
  `kotlin-dsl`
  kotlin("plugin.serialization") version "1.9.10"
  id("java-library")
  alias(libs.plugins.vanniktech.maven) apply true
}

group = "dev.teogor.winds"
version = "1.0.0-alpha02"

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
  implementation(libs.kotlinx.serialization.core)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.gson)
}

@Suppress("UnstableApiUsage")
mavenPublishing {
  publishToMavenCentral(SonatypeHost.S01)
  signAllPublications()

  pom {
    name.set("Winds API")
    description.set("\uD83C\uDF43 Winds build and publish libraries and applications for multiple platforms, simple and efficient.")
    url.set("https://github.com/teogor/winds")
    inceptionYear.set("2023")

    coordinates(
      groupId = "dev.teogor.winds",
      artifactId = "winds-api",
      version = "1.0.0-alpha01",
    )

    licenses {
      license {
        name.set("Apache-2.0") // Apache License
        url.set("https://www.apache.org/licenses/LICENSE-2.0.html") // License URL
      }
    }

    developers {
      developer {
        id.set("teogor")
        name.set("Teodor Grigor")
        email.set("open-source@teogor.dev")
        roles.addAll(
          "Code Owner",
          "Developer",
          "Designer",
          "Maintainer"
        )
        timezone.set("UTC+2")
        organization.set("Teogor")
        organizationUrl.set("https://github.com/teogor")
        url.set("https://teogor.dev")
      }
    }

    scm {
      url.set("https://github.com/teogor/winds")
      connection.set("scm:git:https://github.com/teogor/winds.git")
      developerConnection.set("scm:git:https://github.com/teogor/winds.git")
    }
  }
}
