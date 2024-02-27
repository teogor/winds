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

package dev.teogor.winds.api

import org.gradle.api.publish.maven.MavenPomIssueManagement
import kotlin.reflect.KClass

/**
 * Represents the ticket system of a Maven publication.
 *
 * This interface provides properties for the name and URL of the ticket system
 * used for tracking and managing issues related to the publication.
 *
 * @see MavenPomIssueManagement for the original Gradle API interface definition.
 */
sealed interface TicketSystem {

  /**
   * The name of the ticket system.
   */
  val system: String

  /**
   * The URL of the ticket system for this publication.
   */
  val url: String

  /**
   * Represents a GitHub repository as the ticket system.
   *
   * @param repository The name of the GitHub repository (e.g., "teogor/winds").
   * @param owner The owner of the GitHub repository (e.g., "teogor").
   */
  data class GitHub(
    var repository: String,
    var owner: String,
  ) : TicketSystem {
    override val system: String = "GitHub"
    override val url: String
      get() = "https://github.com/$owner/$repository/issues"
  }

  /**
   * Represents a GitLab project as the ticket system.
   *
   * @param project The name of the GitLab project (e.g., "winds").
   * @param group The group or organization containing the project (optional).
   */
  data class GitLab(
    var project: String,
    var group: String = "",
  ) : TicketSystem {
    override val system: String = "GitLab"
    override val url: String
      get() = "https://gitlab.com/$group/$project/issues"
  }

  /**
   * Represents a Jira project as the ticket system.
   *
   * @param projectKey The key of the Jira project (e.g., "WINDS").
   * @param serverUrl The URL of the Jira server (optional, defaults to Jira Cloud).
   */
  data class Jira(
    var projectKey: String,
    var serverUrl: String = "https://issues.atlassian.com",
  ) : TicketSystem {
    override val system: String = "Jira"
    override val url: String
      get() = "$serverUrl/browse/$projectKey"
  }

  /**
   * Represents a Bugzilla product as the ticket system.
   *
   * @param product The name of the Bugzilla product (e.g., "Winds").
   * @param component The component within the product (optional).
   * @param serverUrl The URL of the Bugzilla server.
   */
  data class Bugzilla(
    var product: String,
    var component: String = "",
    var serverUrl: String,
  ) : TicketSystem {
    override val system: String = "Bugzilla"
    override val url: String
      get() = "$serverUrl/buglist.cgi?product=$product&component=$component"
  }

  /**
   * Represents a Redmine project as the ticket system.
   *
   * @param projectId The ID or alias of the Redmine project.
   * @param baseUrl The base URL of the Redmine server.
   */
  data class Redmine(
    var projectId: String,
    var baseUrl: String,
  ) : TicketSystem {
    override val system: String = "Redmine"
    override val url: String
      get() = "$baseUrl/projects/$projectId/issues"
  }

  object Default : TicketSystem {
    override var system: String = ""
    override var url: String = ""
  }

  companion object {
    inline fun <reified T : TicketSystem> default() = default(T::class)

    fun default(type: KClass<out TicketSystem>): TicketSystem? {
      return when (type) {
        GitHub::class -> GitHub("", "")
        GitLab::class -> GitLab("", "")
        Jira::class -> Jira("", "")
        Bugzilla::class -> Bugzilla("", "", "")
        Redmine::class -> Redmine("", "")
        Default::class -> Default
        else -> null
      }
    }
  }
}
