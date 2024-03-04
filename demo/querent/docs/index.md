# Querent

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Querent

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Querent dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-querent-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-querent-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).<br>
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).<br>
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).<br>

### Adding Querent Dependencies Manually

First, add the `querent-gradle-plugin` to your project's root `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        id 'dev.teogor.querent' version '1.0.0-alpha03' apply false
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        id("dev.teogor.querent") version "1.0.0-alpha03" apply false
    }
    ```

Then, apply the Gradle plugin and add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        id 'dev.teogor.querent'
    }

    dependencies {
        def teogorQuerent = "1.0.0-alpha03"
        
        
        // optional - Gradle Plugin API support for Querent
        implementation "dev.teogor.querent:gradle-plugin-api:$teogorQuerent"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        id("dev.teogor.querent")
    }

    dependencies {
        val teogorQuerent = "1.0.0-alpha03"
        
        
        // optional - Gradle Plugin API support for Querent
        implementation("dev.teogor.querent:gradle-plugin-api:$teogorQuerent")
    }
    ```

### Managing Querent Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Querent dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-querent = "1.0.0-alpha03"
    
    [libraries]
    teogor-querent-gradle-plugin-api = { group = "dev.teogor.querent", name = "gradle-plugin-api", version.ref = "teogor-querent" }
    
    [plugins]
    teogor-querent = { id = "dev.teogor.querent", version.ref = "teogor-querent" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-querent = "1.0.0-alpha03"
    
    [libraries]
    teogor-querent-gradle-plugin-api = { module = "dev.teogor.querent:gradle-plugin-api", version.ref = "teogor-querent" }
    
    [plugins]
    teogor-querent = { id = "dev.teogor.querent", version.ref = "teogor-querent" }
    ```

Then, add the `querent-gradle-plugin` to your project's root `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        alias libs.plugins.teogor.querent apply false
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        alias(libs.plugins.teogor.querent) apply false
    }
    ```

Lastly, apply the Gradle plugin and add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        alias libs.plugins.teogor.querent
    }

    dependencies {
        
        // optional - Gradle Plugin API support for Querent
        implementation libs.teogor.querent.gradle.plugin.api
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        alias(libs.plugins.teogor.querent)
    }

    dependencies {
        
        // optional - Gradle Plugin API support for Querent
        implementation(libs.teogor.querent.gradle.plugin.api)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

