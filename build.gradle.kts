buildscript {
  repositories {
    google()
    mavenCentral()
  }
}

// Lists all plugins used throughout the project without applying them.
plugins {
  // Kotlin Suite
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.serialization) apply false

  // Android Plugins
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false

  // Firebase
  alias(libs.plugins.firebase.crashlytics) apply false
  alias(libs.plugins.firebase.perf) apply false

  alias(libs.plugins.gms) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.dokka) apply false
  alias(libs.plugins.spotless) apply false
  alias(libs.plugins.vanniktech.maven) apply false
}
