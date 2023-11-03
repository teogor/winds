package dev.teogor.winds.api

// TODO spotless & api-validator & dokka
/**
 * A list of build features that can be disabled or enabled
 * in a project.
 *
 * This list applies to all plugin types.
 */
interface BuildFeatures {

  /**
   * Flag to enable Maven publishing feature.
   *
   * Setting the value to `true` enables Maven publishing.
   *
   * Default value is `false`.
   */
  var mavenPublish: Boolean

  /**
   * Flag to enable documentation generator feature.
   *
   * Setting the value to `true` enables the documentation generator.
   *
   * Default value is `false`.
   */
  var docsGenerator: Boolean

  /**
   * Flag to enable workflow synthesizer feature.
   *
   * Setting the value to `true` enables the workflow synthesizer.
   *
   * Default value is `false`.
   */
  var workflowSynthesizer: Boolean

}
