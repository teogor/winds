import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
  alias(libs.plugins.jetbrains.kotlin.multiplatform)
  id("dev.teogor.winds")
}

kotlin {
  jvm()
  linuxX64()
  linuxArm64()
  iosX64()
  iosArm64()
  @OptIn(ExperimentalWasmDsl::class)
  wasmJs {
    browser()
    nodejs()
  }
}

winds {
  moduleMetadata {
    artifactDescriptor {
      name = "Common"
    }
  }
}
