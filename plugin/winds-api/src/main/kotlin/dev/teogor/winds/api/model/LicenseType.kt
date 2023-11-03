package dev.teogor.winds.api.model

enum class LicenseType(
  val title: String,
  val url: String,
  val distribution: String,
) {
    APACHE_2_0(
        "The Apache License, Version 2.0",
        "http://www.apache.org/licenses/LICENSE-2.0.txt",
        "http://www.apache.org/licenses/LICENSE-2.0.txt"
    ),
    MIT(
        "The MIT License",
        "https://opensource.org/licenses/MIT",
        "https://opensource.org/licenses/MIT"
    ),
    GPL_3_0(
        "GNU General Public License v3.0",
        "https://www.gnu.org/licenses/gpl-3.0.html",
        "https://www.gnu.org/licenses/gpl-3.0.html"
    ),
    BSD_3_CLAUSE(
        "BSD 3-Clause License",
        "https://opensource.org/licenses/BSD-3-Clause",
        "https://opensource.org/licenses/BSD-3-Clause"
    ),
    MPL_2_0(
        "Mozilla Public License 2.0",
        "https://www.mozilla.org/en-US/MPL/2.0/",
        "https://www.mozilla.org/en-US/MPL/2.0/"
    );
}
