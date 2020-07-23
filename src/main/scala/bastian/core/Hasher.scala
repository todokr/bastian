package bastian.core

import java.security.MessageDigest

import javax.xml.bind.DatatypeConverter

object Hasher {
  private val hasher = MessageDigest.getInstance("SHA-1")

  def sha1(s: String): String = {
    val bytes = hasher.digest(s.getBytes)
    DatatypeConverter.printHexBinary(bytes)
  }
}
