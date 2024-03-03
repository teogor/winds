# Data

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Data

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Data dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-Data-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-Data-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).

### Adding Data Dependencies Manually

To use Data in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorExampleData = "1.0.0-alpha02"
        
        implementation "dev.teogor.winds.example:data-database:$teogorExampleData"
        implementation "dev.teogor.winds.example:data-datastore:$teogorExampleData"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorExampleData = "1.0.0-alpha02"
        
        implementation("dev.teogor.winds.example:data-database:$teogorExampleData")
        implementation("dev.teogor.winds.example:data-datastore:$teogorExampleData")
    }
    ```

### Managing Data Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Data dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example-data = "1.0.0-alpha02"
    
    [libraries]
    teogor-example-data-database = { group = "dev.teogor.winds.example", name = "data-database", version.ref = "teogor-example-data" }
    teogor-example-data-datastore = { group = "dev.teogor.winds.example", name = "data-datastore", version.ref = "teogor-example-data" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example-data = "1.0.0-alpha02"
    
    [libraries]
    teogor-example-data-database = { module = "dev.teogor.winds.example:data-database", version.ref = "teogor-example-data" }
    teogor-example-data-datastore = { module = "dev.teogor.winds.example:data-datastore", version.ref = "teogor-example-data" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.example.data.database
        implementation libs.teogor.example.data.datastore
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.example.data.database)
        implementation(libs.teogor.example.data.datastore)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

