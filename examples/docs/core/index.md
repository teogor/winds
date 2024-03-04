# Core

[//]: # (REGION-DEPENDENCIES)

## Getting Started with Core

**Adding Dependencies:**

* **Manual Setup:**  This section guides you through adding Core dependencies directly to your project's `build.gradle` files. ([Link to Manual Dependency Setup Section](#adding-Core-dependencies-manually))
* **Version Catalog (Recommended):** For a more streamlined approach, consider integrating a version catalog. This allows for centralized version management and easier updates. ([Link to Version Catalog Section](#managing-Core-versions-with-version-catalog-recommended))

**Note:** If you prefer manual dependency setup, follow the instructions in the "Manual Setup" section. Otherwise, jump to the "Version Catalog" section for centralized management.

For information on using the KAPT plugin, see the [KAPT documentation](https://kotlinlang.org/docs/kapt.html).
For information on using the KSP plugin, see the [KSP quick-start documentation](https://kotlinlang.org/docs/ksp-quickstart.html).
For more information about dependencies, see [Add Build Dependencies](https://developer.android.com/studio/build/dependencies).

### Adding Core Dependencies Manually

To use Core in your app, add the following dependencies to your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        def teogorExampleCore = "1.0.0-beta03"
        
        implementation "dev.teogor.winds.example:example-core-common:$teogorExampleCore"
        implementation "dev.teogor.winds.example:example-core-design-system:$teogorExampleCore"
        implementation "dev.teogor.winds.example:example-core-domain:$teogorExampleCore"
        implementation "dev.teogor.winds.example:example-core-ktx:$teogorExampleCore"
        implementation "dev.teogor.winds.example:example-core-network:$teogorExampleCore"
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        val teogorExampleCore = "1.0.0-beta03"
        
        implementation("dev.teogor.winds.example:example-core-common:$teogorExampleCore")
        implementation("dev.teogor.winds.example:example-core-design-system:$teogorExampleCore")
        implementation("dev.teogor.winds.example:example-core-domain:$teogorExampleCore")
        implementation("dev.teogor.winds.example:example-core-ktx:$teogorExampleCore")
        implementation("dev.teogor.winds.example:example-core-network:$teogorExampleCore")
    }
    ```

### Managing Core Versions with Version Catalog (Recommended)

This section guides you through utilizing a version catalog for centralized management of Core dependencies in your project. This approach simplifies updates and ensures consistency.

First, define the dependencies in the `libs.versions.toml` file:

- **Group-Name Based:** This approach is used for declaring libraries referenced by group and artifact name.
- **Module Based:** This approach is used for declaring libraries referenced by their module.

=== "Group-Name Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example-core = "1.0.0-beta03"
    
    [libraries]
    teogor-example-core-common = { group = "dev.teogor.winds.example", name = "example-core-common", version.ref = "teogor-example-core" }
    teogor-example-core-design-system = { group = "dev.teogor.winds.example", name = "example-core-design-system", version.ref = "teogor-example-core" }
    teogor-example-core-domain = { group = "dev.teogor.winds.example", name = "example-core-domain", version.ref = "teogor-example-core" }
    teogor-example-core-ktx = { group = "dev.teogor.winds.example", name = "example-core-ktx", version.ref = "teogor-example-core" }
    teogor-example-core-network = { group = "dev.teogor.winds.example", name = "example-core-network", version.ref = "teogor-example-core" }
    ```

=== "Module Based"

    ```toml title="gradle/libs.versions.toml"
    [versions]
    teogor-example-core = "1.0.0-beta03"
    
    [libraries]
    teogor-example-core-common = { module = "dev.teogor.winds.example:example-core-common", version.ref = "teogor-example-core" }
    teogor-example-core-design-system = { module = "dev.teogor.winds.example:example-core-design-system", version.ref = "teogor-example-core" }
    teogor-example-core-domain = { module = "dev.teogor.winds.example:example-core-domain", version.ref = "teogor-example-core" }
    teogor-example-core-ktx = { module = "dev.teogor.winds.example:example-core-ktx", version.ref = "teogor-example-core" }
    teogor-example-core-network = { module = "dev.teogor.winds.example:example-core-network", version.ref = "teogor-example-core" }
    ```

Then, add these dependencies in your app's `build.gradle` file:

=== "Groovy"

    ```groovy title="build.gradle"
    dependencies {
        implementation libs.teogor.example.core.common
        implementation libs.teogor.example.core.design.system
        implementation libs.teogor.example.core.domain
        implementation libs.teogor.example.core.ktx
        implementation libs.teogor.example.core.network
    }
    ```

=== "Kotlin"

    ```kotlin title="build.gradle.kts"
    dependencies {
        implementation(libs.teogor.example.core.common)
        implementation(libs.teogor.example.core.design.system)
        implementation(libs.teogor.example.core.domain)
        implementation(libs.teogor.example.core.ktx)
        implementation(libs.teogor.example.core.network)
    }
    ```

[//]: # (REGION-DEPENDENCIES)

