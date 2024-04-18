import dev.teogor.winds.api.SonatypeHost
import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.License
import dev.teogor.winds.api.Person
import dev.teogor.winds.api.Scm
import dev.teogor.winds.api.TicketSystem
import dev.teogor.winds.ktx.person
import dev.teogor.winds.ktx.scm
import dev.teogor.winds.ktx.ticketSystem
import org.jetbrains.dokka.gradle.DokkaPlugin

buildscript {
  repositories {
    google()
    mavenCentral()
  }
}

// Lists all plugins used throughout the project without applying them.
plugins {
  // Kotlin Suite
  alias(libs.plugins.jetbrains.kotlin.jvm) apply true
  alias(libs.plugins.jetbrains.kotlin.android) apply false
  alias(libs.plugins.jetbrains.kotlin.serialization) apply false

  // Android Plugins
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false

  alias(libs.plugins.vanniktech.maven) apply true

  id("dev.teogor.winds")

  // API Documentation and Validation Plugins
  alias(libs.plugins.jetbrains.dokka) apply true
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(11))
  }
}
tasks.withType<JavaCompile>().configureEach {
  sourceCompatibility = JavaVersion.VERSION_11.toString()
  targetCompatibility = JavaVersion.VERSION_11.toString()
}

winds {
  features {
    mavenPublishing = true
    docsGenerator = true
    workflowSynthesizer = true
  }

  windsFeatures {
    mavenPublishing = true
    docsGenerator = true
    workflowSynthesizer = true
  }

  moduleMetadata {
    name = "Ceres"
    description = """
    |Querent lays the groundwork for your project's resource management, fostering consistency and efficiency across your development workflow.
    """.trimMargin()
    yearCreated = 2024
    websiteUrl = "https://source.teogor.dev/ceres/"
    apiDocsUrl = "https://source.teogor.dev/ceres/html/"

    artifactDescriptor {
      group = "dev.teogor.ceres"
      name = "ceres"
      artifactIdFormat = ArtifactIdFormat.MODULE_NAME_ONLY
    }

    // Providing SCM (Source Control Management)
    scm<Scm.GitLab> {
      owner = "teogor"
      repository = "ceres"
    }

    // Providing Ticket System
    ticketSystem<TicketSystem.GitHub> {
      owner = "teogor"
      repository = "ceres"
    }

    // Providing Licenses
    licensedUnder(License.Apache2())

    // Providing Persons
    person<Person.DeveloperContributor> {
      id = "teogor"
      name = "Teodor Grigor"
      email = "open-source@teogor.dev"
      url = "https://teogor.dev"
      roles = listOf("Code Owner", "Developer", "Designer", "Maintainer")
      timezone = "UTC+2"
      organization = "Teogor"
      organizationUrl = "https://github.com/teogor"
    }
  }

  publishing {
    enabled = false
    cascade = true

    enablePublicationSigning = true
    optInForVanniktechPlugin = true
    sonatypeHost = SonatypeHost.S01
  }

  publishingOptions {
    publish = false
    cascadePublish = true

    enablePublicationSigning = true
    optInForVanniktechPlugin = true
    sonatypeHost = com.vanniktech.maven.publish.SonatypeHost.S01
  }

  documentationBuilder {
    htmlPath = "html/"
    markdownNewlineSeparator = "  "
  }
}

subprojects {
  apply<DokkaPlugin>()
}
