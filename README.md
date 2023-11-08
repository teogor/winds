## Winds 🍃

Winds aims to provide a simple and efficient way to build and publish libraries and applications for multiple platforms. It is designed to be flexible and extensible, so that it can be used to build a wide variety of libraries and applications, from simple utility libraries to complex composable libraries and applications.

### Features

* Simple and efficient build process
* Flexible and extensible library structure
* Support for multiple publication formats (Maven, AAR, etc.)
* Automatic documentation generation

### Requirements

* Android Gradle Plugin 7.0+
* Kotlin Gradle Plugin 1.7+

### Usage

**Plugin implementation**

To implement the Winds plugin, add the following plugin ID to your build.gradle file:

```kotlin
plugins {
  id "dev.teogor.winds" version "1.0.0-alpha02"
}
```

**Plugin usage**

Once you have implemented the Winds plugin, you can use it to build and publish your library or application as follows:

1. Configure the Winds plugin in your build.gradle file.
2. Build your library or application using the `gradlew build` command.
3. Publish your library or application using the `gradlew publish` command.

**Example build.gradle file**

The following is an example build.gradle file that uses the Winds plugin to build and publish a library:

```kotlin
plugins {
  id "dev.teogor.winds" version "1.0.0-alpha02"
}

winds {
  buildFeatures {
    mavenPublish = true
    docsGenerator = true
  }

  mavenPublish {
    groupId = "my.group.id"
    artifactId = "my-library"
    version = "1.0.0"
  }
}
```
