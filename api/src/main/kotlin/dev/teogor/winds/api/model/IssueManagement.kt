/*
 * Copyright 2024 teogor (Teodor Grigor)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.teogor.winds.api.model

import org.gradle.api.publish.maven.MavenPomIssueManagement

/**
 * Represents the issue management system of a Maven publication.
 *
 * This interface provides properties for the name and URL of the issue management
 * system used for tracking and managing issues related to the publication.
 *
 * @see MavenPomIssueManagement for the original Gradle API interface definition.
 */
interface IssueManagement {

  /**
   * The name of the issue management system.
   */
  val system: String

  /**
   * The URL of the issue management system for this publication.
   */
  val url: String

  /**
   * Represents a GitHub repository as the issue management system.
   *
   * @param repo The name of the GitHub repository (e.g., "teogor/winds").
   * @param owner The owner of the GitHub repository (e.g., "teogor").
   */
  data class Git(
    val repo: String,
    val owner: String,
  ) : IssueManagement {
    override val system: String = "GitHub Issues"
    override val url: String = "https://github.com/$owner/$repo/issues"
  }

  /**
   * Represents a GitLab project as the issue management system.
   *
   * @param project The name of the GitLab project (e.g., "winds").
   * @param group The group or organization containing the project (optional).
   */
  data class GitLab(
    val project: String,
    val group: String = "",
  ) : IssueManagement {
    override val system: String = "GitLab Issues"
    override val url: String = "https://gitlab.com/$group/$project/issues"
  }

  /**
   * Represents a Jira project as the issue management system.
   *
   * @param projectKey The key of the Jira project (e.g., "WINDS").
   * @param serverUrl The URL of the Jira server (optional, defaults to Jira Cloud).
   */
  data class Jira(
    val projectKey: String,
    val serverUrl: String = "https://issues.atlassian.com",
  ) : IssueManagement {
    override val system: String = "Jira"
    override val url: String = "$serverUrl/browse/$projectKey"
  }

  /**
   * Represents a Bugzilla product as the issue management system.
   *
   * @param product The name of the Bugzilla product (e.g., "Winds").
   * @param component The component within the product (optional).
   * @param serverUrl The URL of the Bugzilla server.
   */
  data class Bugzilla(
    val product: String,
    val component: String = "",
    val serverUrl: String,
  ) : IssueManagement {
    override val system: String = "Bugzilla"
    override val url: String = "$serverUrl/buglist.cgi?product=$product&component=$component"
  }

  /**
   * Represents a Redmine project as the issue management system.
   *
   * @param projectId The ID or alias of the Redmine project.
   * @param baseUrl The base URL of the Redmine server.
   */
  data class Redmine(
    val projectId: String,
    val baseUrl: String,
  ) : IssueManagement {
    override val system: String = "Redmine"
    override val url: String = "$baseUrl/projects/$projectId/issues"
  }
}
