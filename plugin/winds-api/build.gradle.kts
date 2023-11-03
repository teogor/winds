import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  // TODO dsl -> can we publish this as API?
  `kotlin-dsl`
  kotlin("plugin.serialization") version "1.9.10"
}

group = "dev.teogor.winds"
version = "1.0.0-alpha01"

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
