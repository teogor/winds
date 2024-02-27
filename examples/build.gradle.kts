import com.vanniktech.maven.publish.SonatypeHost
import dev.teogor.winds.api.ArtifactIdFormat
import dev.teogor.winds.api.License
import dev.teogor.winds.api.NameFormat
import dev.teogor.winds.api.Person
import dev.teogor.winds.api.Scm
import dev.teogor.winds.api.TicketSystem
import dev.teogor.winds.api.model.DependencyType
import dev.teogor.winds.api.Distribution
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
  windsFeatures {
    mavenPublishing = true
    docsGenerator = true
    workflowSynthesizer = true
  }

  moduleMetadata {
    name = "Winds Examples"
    description = "Winds description example"
    yearCreated = 2024
    websiteUrl = "https://source.teogor.dev/winds/"
    apiDocsUrl = "https://source.teogor.dev/winds/html/"

    artifactDescriptor {
      group = "dev.teogor.winds.example"
      name = "example"
      version = createVersion(1, 0, 0) {
        alphaRelease(1)
      }
      nameFormat = NameFormat.FULL
      artifactIdFormat = ArtifactIdFormat.MODULE_NAME_ONLY
    }

    // Providing SCM (Source Control Management)
    scm<Scm.GitLab> {
      owner = "teogor"
      repository = "winds"
    }
    scm(Scm.GitHub::class) {
      owner = "teogor"
      repository = "winds"
    }

    // Providing Ticket System
    ticketSystem(TicketSystem.Default::class) {
      system = "teogor"
    }
    ticketSystem<TicketSystem.GitLab> {
      project = "winds"
      group = "teogor"
    }
    ticketSystem<TicketSystem.GitHub> {
      repository = "winds"
      owner = "teogor"
    }

    // Providing Licenses
    this licensedUnder License.MIT()
    licensedUnder(License.None)
    this += License.MIT()
    licenses += (License.Apache2())
    licenses register License.MIT()
    licenses {
      add(
        License.Apache2().apply {
          comments = "To be used with caution"
        },
      )
      register(License.MIT())
    }
    licenses(
      License.None,
      License.MIT(
        distribution = Distribution.Repo,
      ),
    )

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

  publishingOptions {
    publish = false
    enablePublicationSigning = true
    optInForVanniktechPlugin = true
    cascadePublish = true
    sonatypeHost = SonatypeHost.S01
  }

  documentationBuilder {
    htmlPath = "html/"
  }

  codebaseOptions {
    dokka {

    }
    spotless {

    }
    binaryCompatibility {

    }
  }

  // not required. just an example
  // configureProjects(
  //   includeRoot = true,
  // ) { winds ->
  //   configureMavenPublishing(winds) {
  //
  //   }
  // }
}

// todo context winds not windsOptions
windsLegacy {
  buildFeatures {
    mavenPublish = true

    docsGenerator = true

    workflowSynthesizer = false
  }

  docsGenerator {
    name = "Winds Examples"
    identifier = "winds-examples"
    alertOnDependentModules = true
    dependencyGatheringType = DependencyType.LOCAL
    mkdocsEnabled = true

    excludeModules {
      listOf(
        // ":app",
        // ":application",
      )
    }
  }
}

subprojects {
  apply<DokkaPlugin>()
}
