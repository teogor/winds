# Kmp

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Kmp

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Kmp dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-Kmp-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-Kmp-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).

### Adding Kmp Dependencies Manually

To use Kmp in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorExampleKmp = "1.0.0-beta01"
        
        implementation "dev.teogor.winds.example:kmp-android:$teogorExampleKmp"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorExampleKmp = "1.0.0-beta01"
        
        implementation("dev.teogor.winds.example:kmp-android:$teogorExampleKmp")
    }
    ```

### Managing Kmp Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Kmp dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example-kmp = "1.0.0-beta01"
    
    [libraries]
    teogor-example-kmp-android = { group = "dev.teogor.winds.example", name = "kmp-android", version.ref = "teogor-example-kmp" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example-kmp = "1.0.0-beta01"
    
    [libraries]
    teogor-example-kmp-android = { module = "dev.teogor.winds.example:kmp-android", version.ref = "teogor-example-kmp" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.example.kmp.android
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.example.kmp.android)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

