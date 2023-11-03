package dev.teogor.winds.api.provider

interface Scm {

  /**
   * The connection URL of this SCM.
   */
  val connection: String

  /**
   * The developer connection URL of this SCM.
   */
  val developerConnection: String

  /**
   * The browsable repository URL of this SCM.
   */
  val url: String

  data class Git(
    val repo: String,
    val owner: String,
  ) : Scm {
    override val connection: String = "https://github.com/$owner/$repo.git"
    override val developerConnection: String = "ssh://git@github.com/$owner/$repo.git"
    override val url: String = "https://github.com/$owner/$repo"
  }

  data class Bitbucket(
    val repo: String,
    val owner: String
  ) : Scm {
    override val connection: String = "https://bitbucket.org/$owner/$repo.git"
    override val developerConnection: String = "ssh://git@bitbucket.org/$owner/$repo.git"
    override val url: String = "https://bitbucket.org/$owner/$repo"
  }

  data class SVN(
    val repo: String,
    val owner: String,
    val domain: String
  ) : Scm {
    override val connection: String = "https://$domain/$owner/$repo/trunk"
    override val developerConnection: String = "svn+ssh://$domain/$owner/$repo/trunk"
    override val url: String = "https://$domain/$owner/$repo"
  }

  data class Local(
    val path: String
  ) : Scm {
    override val connection: String = "file://$path"
    override val developerConnection: String = "file://$path"
    override val url: String = "file://$path"
  }

  data class Custom(
    val repo: String,
    val owner: String,
    val domain: String
  ) : Scm {
    override val connection: String = "https://$domain/$owner/$repo"
    override val developerConnection: String = "ssh://$domain/$owner/$repo"
    override val url: String = "https://$domain/$owner/$repo"
  }

  data class CustomExplicit(
    override val connection: String,
    override val developerConnection: String,
    override val url: String
  ) : Scm
}

