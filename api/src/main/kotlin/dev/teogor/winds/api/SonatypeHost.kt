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

/**
 * Describes the various hosts for Sonatype OSSRH. Depending on when a user signed up with Sonatype
 * they have to use different hosts.
 *
 * See [New Users on S01.oss.sonatype.org](https://central.sonatype.org/articles/2021/Feb/23/new-users-on-s01osssonatypeorg/) for more information.
 */
enum class SonatypeHost(
  /** The root URL for the Sonatype host. */
  val rootUrl: String,
  /** Whether this host is the Sonatype Central Portal. */
  val isCentralPortal: Boolean,
) {
  /**
   * The default host for users who signed up before February 2021.
   *
   * Root URL: https://oss.sonatype.org
   * Central Portal: False
   */
  DEFAULT("https://oss.sonatype.org", false),

  /**
   * The host for new users who signed up after February 2021.
   *
   * Root URL: https://s01.oss.sonatype.org
   * Central Portal: False
   */
  S01("https://s01.oss.sonatype.org", false),

  /**
   * The Sonatype Central Portal for users with specific access.
   *
   * Root URL: https://central.sonatype.com
   * Central Portal: True
   */
  CENTRAL_PORTAL("https://central.sonatype.com", true),
  ;

  /**
   * Converts this SonatypeHost enum instance to a [com.vanniktech.maven.publish.SonatypeHost]
   * instance.
   *
   * @return A new [com.vanniktech.maven.publish.SonatypeHost] instance.
   */
  fun toVanniktechSonatypeHost(): com.vanniktech.maven.publish.SonatypeHost {
    return when (this) {
      DEFAULT -> com.vanniktech.maven.publish.SonatypeHost.DEFAULT
      S01 -> com.vanniktech.maven.publish.SonatypeHost.S01
      CENTRAL_PORTAL -> com.vanniktech.maven.publish.SonatypeHost.CENTRAL_PORTAL
    }
  }
}
