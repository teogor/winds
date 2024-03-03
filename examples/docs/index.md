# Example

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Example

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Example dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-Example-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-Example-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).

### Adding Example Dependencies Manually

To use Example in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorExample = "1.0.0-alpha01"
        
        implementation "dev.teogor.winds.example:bom:$teogorExample"
        implementation "dev.teogor.winds.example:example-demo:$teogorExample"
        implementation "dev.teogor.winds.example:example-demo-1:$teogorExample"
        implementation "dev.teogor.winds.example:example-demo-2:$teogorExample"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorExample = "1.0.0-alpha01"
        
        implementation("dev.teogor.winds.example:bom:$teogorExample")
        implementation("dev.teogor.winds.example:example-demo:$teogorExample")
        implementation("dev.teogor.winds.example:example-demo-1:$teogorExample")
        implementation("dev.teogor.winds.example:example-demo-2:$teogorExample")
    }
    ```

### Managing Example Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Example dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example = "1.0.0-alpha01"
    
    [libraries]
    teogor-example-bom = { group = "dev.teogor.winds.example", name = "bom", version.ref = "teogor-example" }
    teogor-example-demo = { group = "dev.teogor.winds.example", name = "example-demo", version.ref = "teogor-example" }
    teogor-example-demo-1 = { group = "dev.teogor.winds.example", name = "example-demo-1", version.ref = "teogor-example" }
    teogor-example-demo-2 = { group = "dev.teogor.winds.example", name = "example-demo-2", version.ref = "teogor-example" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example = "1.0.0-alpha01"
    
    [libraries]
    teogor-example-bom = { module = "dev.teogor.winds.example:bom", version.ref = "teogor-example" }
    teogor-example-demo = { module = "dev.teogor.winds.example:example-demo", version.ref = "teogor-example" }
    teogor-example-demo-1 = { module = "dev.teogor.winds.example:example-demo-1", version.ref = "teogor-example" }
    teogor-example-demo-2 = { module = "dev.teogor.winds.example:example-demo-2", version.ref = "teogor-example" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.example.bom
        implementation libs.teogor.example.demo
        implementation libs.teogor.example.demo.1
        implementation libs.teogor.example.demo.2
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.example.bom)
        implementation(libs.teogor.example.demo)
        implementation(libs.teogor.example.demo.1)
        implementation(libs.teogor.example.demo.2)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

