# Publishing Configuration

The `publishing` block defines how your artifacts are published to Maven repositories.

## Properties

| Property | Description | Default |
| :--- | :--- | :--- |
| `enabled` | Whether publishing is enabled for the current project. | `false` |
| `cascade` | If true, triggers publishing for child projects. | `false` |
| `enablePublicationSigning` | Enables signing of published artifacts. | `false` |
| `optInForVanniktechPlugin` | Opts into using the Vanniktech Maven Publish plugin. | `false` |
| `automaticPublishing` | Automatically triggers publishing upon build. | `false` |

## Usage

```kotlin
winds {
    publishing {
        enabled = true
        cascade = true
        enablePublicationSigning = true
    }
}
```
