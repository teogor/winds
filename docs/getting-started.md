# Getting Started with Winds 🍃

Winds is a Gradle plugin designed to streamline the build and publication process for Android and Kotlin projects.

## Installation

Add the Winds plugin to your root `build.gradle.kts` file:

```kotlin
plugins {
    id("dev.teogor.winds") version "1.0.3"
}
```

## Basic Configuration

The most basic configuration involves enabling the features you need and providing module metadata.

```kotlin
winds {
    features {
        mavenPublishing = true
        docsGenerator = true
    }

    moduleMetadata {
        name = "My Awesome Library"
        description = "A brief description of what this library does."
        yearCreated = 2024

        artifactDescriptor {
            group = "dev.example"
            name = "my-library"
        }
    }
}
```

## Next Steps

- Explore [Configuration Overview](configuration/index.md) for more details.
- Learn about [Maven Publishing](features/maven-publishing.md).
- See how to [Generate Documentation](features/documentation.md).
