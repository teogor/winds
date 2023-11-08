import com.vanniktech.maven.publish.SonatypeHost
import dev.teogor.winds.api.MavenPublish
import dev.teogor.winds.api.getValue
import dev.teogor.winds.api.model.Developer
import dev.teogor.winds.api.model.LicenseType
import dev.teogor.winds.api.provider.Scm
import dev.teogor.winds.gradle.utils.afterWindsPluginConfiguration
import dev.teogor.winds.gradle.utils.attachTo

buildscript {
  repositories {
    google()
    mavenCentral()
  }
}

// Lists all plugins used throughout the project without applying them.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.firebase.crashlytics) apply false
  alias(libs.plugins.firebase.perf) apply false
  alias(libs.plugins.gms) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.ksp) apply false
  alias(libs.plugins.about.libraries) apply false
  alias(libs.plugins.vanniktech.maven) apply true

  id("dev.teogor.winds")
}

winds {
  buildFeatures {
    mavenPublish = true

    docsGenerator = true

    workflowSynthesizer = false
  }

  mavenPublish {
    displayName = "Winds"
    name = "winds"

    canBePublished = false

    description = "\uD83C\uDF43 Winds build and publish libraries and applications for multiple platforms, simple and efficient."


    groupId = "dev.teogor.winds"

    artifactIdElements = 2

    inceptionYear = 2023

    sourceControlManagement(
      Scm.Git(
        repo = "winds",
        owner = "teogor",
      ),
    )

    addLicense(LicenseType.APACHE_2_0)

    addDeveloper(TeogorDeveloper())
  }

  docsGenerator {
    name = "Winds"
    identifier = "winds"
    alertOnDependentModules = true

    excludeModules {
      listOf(
        ":app",
      )
    }
  }
}

afterWindsPluginConfiguration { winds ->
  val mavenPublish: MavenPublish by winds
  if (mavenPublish.canBePublished) {
    mavenPublishing {
      publishToMavenCentral(SonatypeHost.S01)
      signAllPublications()

      @Suppress("UnstableApiUsage")
      pom {
        coordinates(
          groupId = mavenPublish.groupId!!,
          artifactId = mavenPublish.artifactId!!,
          version = mavenPublish.version!!.toString(),
        )
        mavenPublish attachTo this
      }
    }
  }
}

data class TeogorDeveloper(
  override val id: String = "teogor",
  override val name: String = "Teodor Grigor",
  override val email: String = "open-source@teogor.dev",
  override val url: String = "https://teogor.dev",
  override val roles: List<String> = listOf("Code Owner", "Developer", "Designer", "Maintainer"),
  override val timezone: String = "UTC+2",
  override val organization: String = "Teogor",
  override val organizationUrl: String = "https://github.com/teogor",
) : Developer
