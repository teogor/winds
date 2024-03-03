# Stitch

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Stitch

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Stitch dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-Stitch-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-Stitch-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).

### Adding Stitch Dependencies Manually

First, add the `stitch-gradle-plugin` to your project's root `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        id 'dev.teogor.stitch' version '1.2.1' apply false
        id 'dev.teogor.stitch.args' version '1.2.1' apply false
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        id("dev.teogor.stitch") version "1.2.1" apply false
        id("dev.teogor.stitch.args") version "1.2.1" apply false
    }
    ```

Then, apply the Gradle plugin and add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        id 'dev.teogor.stitch'
        id 'dev.teogor.stitch.args'
    }

    dependencies {
        def teogorExampleStitch = "1.2.1"
        
        implementation "dev.teogor.winds.example:stitch-common:$teogorExampleStitch"
        
        // To use Kotlin annotation processing tool (kapt)
        kapt "dev.teogor.winds.example:stitch-ksp:$teogorExampleStitch"
        // To use Kotlin Symbol Processing (KSP)
        ksp "dev.teogor.winds.example:stitch-ksp:$teogorExampleStitch"
        
        // optional - Plugin API support for Stitch
        implementation "dev.teogor.winds.example:stitch-plugin-api:$teogorExampleStitch"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        id("dev.teogor.stitch")
        id("dev.teogor.stitch.args")
    }

    dependencies {
        val teogorExampleStitch = "1.2.1"
        
        implementation("dev.teogor.winds.example:stitch-common:$teogorExampleStitch")
        
        // To use Kotlin annotation processing tool (kapt)
        kapt("dev.teogor.winds.example:stitch-ksp:$teogorExampleStitch")
        // To use Kotlin Symbol Processing (KSP)
        ksp("dev.teogor.winds.example:stitch-ksp:$teogorExampleStitch")
        
        // optional - Plugin API support for Stitch
        implementation("dev.teogor.winds.example:stitch-plugin-api:$teogorExampleStitch")
    }
    ```

### Managing Stitch Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Stitch dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example-stitch = "1.2.1"
    
    [libraries]
    teogor-example-stitch-common = { group = "dev.teogor.winds.example", name = "stitch-common", version.ref = "teogor-example-stitch" }
    teogor-example-stitch-ksp = { group = "dev.teogor.winds.example", name = "stitch-ksp", version.ref = "teogor-example-stitch" }
    teogor-example-stitch-plugin-api = { group = "dev.teogor.winds.example", name = "stitch-plugin-api", version.ref = "teogor-example-stitch" }
    
    [plugins]
    teogor-example-stitch = { id = "dev.teogor.stitch", version.ref = "teogor-example-stitch" }
    teogor-example-stitch-args = { id = "dev.teogor.stitch.args", version.ref = "teogor-example-stitch" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example-stitch = "1.2.1"
    
    [libraries]
    teogor-example-stitch-common = { module = "dev.teogor.winds.example:stitch-common", version.ref = "teogor-example-stitch" }
    teogor-example-stitch-ksp = { module = "dev.teogor.winds.example:stitch-ksp", version.ref = "teogor-example-stitch" }
    teogor-example-stitch-plugin-api = { module = "dev.teogor.winds.example:stitch-plugin-api", version.ref = "teogor-example-stitch" }
    
    [plugins]
    teogor-example-stitch = { id = "dev.teogor.stitch", version.ref = "teogor-example-stitch" }
    teogor-example-stitch-args = { id = "dev.teogor.stitch.args", version.ref = "teogor-example-stitch" }
    ```

Then, add the `stitch-gradle-plugin` to your project's root `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        alias libs.plugins.teogor.example.stitch apply false
        alias libs.plugins.teogor.example.stitch.args apply false
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        alias(libs.plugins.teogor.example.stitch) apply false
        alias(libs.plugins.teogor.example.stitch.args) apply false
    }
    ```

Lastly, apply the Gradle plugin and add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    plugins {
        alias libs.plugins.teogor.example.stitch
        alias libs.plugins.teogor.example.stitch.args
    }

    dependencies {
        implementation libs.teogor.example.stitch.common
        
        // To use Kotlin annotation processing tool (kapt)
        kapt libs.teogor.example.stitch.ksp
        // To use Kotlin Symbol Processing (KSP)
        ksp libs.teogor.example.stitch.ksp
        
        // optional - Plugin API support for Stitch
        implementation libs.teogor.example.stitch.plugin.api
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    plugins {
        alias(libs.plugins.teogor.example.stitch)
        alias(libs.plugins.teogor.example.stitch.args)
    }

    dependencies {
        implementation(libs.teogor.example.stitch.common)
        
        // To use Kotlin annotation processing tool (kapt)
        kapt(libs.teogor.example.stitch.ksp)
        // To use Kotlin Symbol Processing (KSP)
        ksp(libs.teogor.example.stitch.ksp)
        
        // optional - Plugin API support for Stitch
        implementation(libs.teogor.example.stitch.plugin.api)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

