# Getting Started with Winds 🍃

Winds is a Gradle plugin designed to streamline the build and publication process for Android and Kotlin projects.

## Installation

Add the Winds plugin to your root `build.gradle.kts` file:

```kotlin
plugins {
    id("dev.teogor.winds") version "1.0.4-SNAPSHOT"
}
```

## Basic Configuration

The most basic configuration involves enabling the features you need and providing module metadata. Note that Winds now uses Gradle's Lazy Configuration API, so you should use `.set()` for properties.

```kotlin
winds {
    features {
        mavenPublishing.set(true)
        docsGenerator.set(true)
    }

    moduleMetadata {
        name.set("My Awesome Library")
        description.set("A brief description of what this library does.")
        yearCreated.set(2024)

        artifactDescriptor {
            group.set("dev.example")
            name.set("my-library")
        }
    }
}
```

## Next Steps

- Explore [Configuration Overview](configuration/index.md) for more details.
- Learn about [Maven Publishing](features/maven-publishing.md).
- See how to [Generate Documentation](features/documentation.md).
