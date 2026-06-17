# Maven Publishing

Winds simplifies the process of publishing your libraries to Maven Central or other repositories, primarily by integrating with the Vanniktech Maven Publish plugin.

## Setup

1. Enable the feature:
   ```kotlin
   winds {
       features {
           mavenPublishing = true
       }
   }
   ```

2. Configure publication details:
   ```kotlin
   winds {
       publishing {
           enabled = true
           // ...
       }
   }
   ```

## Publishing Tasks

Once configured, you can use standard publishing tasks:

- `./gradlew publish`: Publishes all defined publications.
- `./gradlew publishToMavenLocal`: Publishes artifacts to your local Maven repository.
