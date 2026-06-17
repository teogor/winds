# Configuration Overview

Winds provides a DSL (Domain Specific Language) to configure your project's build and publication lifecycle.

## Main Blocks

The `winds` extension consists of several primary configuration blocks:

- **[`features`](features.md)**: Enable or disable core Winds features (Maven publishing, documentation generation, etc.).
- **[`moduleMetadata`](module-metadata.md)**: Define metadata for your module, such as its name, description, SCM information, and licenses.
- **[`publishing`](publishing.md)**: Configure publication details, including cascade publishing and signing.

## Example Configuration

```kotlin
winds {
    features {
        // ... feature toggles
    }

    moduleMetadata {
        // ... module details
    }

    publishing {
        // ... publication settings
    }
}
```
