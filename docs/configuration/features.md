# Features Configuration

The `features` block allows you to toggle various functionalities of the Winds plugin.

## Properties

| Property | Description | Default |
| :--- | :--- | :--- |
| `mavenPublishing` | Enables Maven publishing for the project. | `false` |
| `docsGenerator` | Enables the internal documentation generator. | `false` |
| `workflowSynthesizer` | Enables creation of automated workflows. | `false` |
| `apiValidator` | Enables binary compatibility validation. | `false` |
| `dokka` | Enables Dokka documentation generation. | `false` |
| `spotless` | Enables code style formatting with Spotless. | `false` |

## Usage

```kotlin
winds {
    features {
        mavenPublishing = true
        spotless = true
        dokka = true
    }
}
```
