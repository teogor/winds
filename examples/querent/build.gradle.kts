import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.License
import dev.teogor.winds.api.NameFormat
import dev.teogor.winds.api.Person
import dev.teogor.winds.api.Scm
import dev.teogor.winds.api.TicketSystem
import dev.teogor.winds.ktx.createVersion
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

  id("dev.teogor.winds") version "1.0.4-SNAPSHOT"

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
    mavenPublishing.set(true)
    docsGenerator.set(true)
    workflowSynthesizer.set(true)
  }

  moduleMetadata {
    name.set("Querent")
    description.set("""
    |Querent lays the groundwork for your project's resource management, fostering consistency and efficiency across your development workflow.
    """.trimMargin())
    yearCreated.set(2024)
    websiteUrl.set("https://source.teogor.dev/querent/")
    apiDocsUrl.set("https://source.teogor.dev/querent/html/")

    artifactDescriptor {
      group.set("dev.teogor.querent")
      name.set("querent")
      version.set(createVersion(1, 0, 0) {
        alphaRelease(3)
      })
      nameFormat.set(NameFormat.FULL)
      artifactIdFormat.set(ArtifactIdFormat.NAME_ONLY)
    }

    // Providing SCM (Source Control Management)
    scm<Scm.GitLab> {
      owner = "teogor"
      repository = "querent"
    }

    // Providing Ticket System
    ticketSystem<TicketSystem.GitHub> {
      owner = "teogor"
      repository = "querent"
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
    enabled.set(false)
    cascade.set(true)
    enablePublicationSigning.set(true)
    optInForVanniktechPlugin.set(true)
  }

  documentationBuilder {
    htmlPath.set("html/")
  }
}

subprojects {
  apply<DokkaPlugin>()
}
