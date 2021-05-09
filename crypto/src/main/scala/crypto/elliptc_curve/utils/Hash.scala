package crypto.elliptc_curve.utils

import org.bouncycastle.jcajce.provider.digest.RIPEMD160

import java.security.MessageDigest

object Hash {
  def sha256(input: Array[Byte]): Array[Byte] = {
    val md = MessageDigest.getInstance("SHA-256")
    md.digest(input)
  }

  def hash160(input: Array[Byte]): Array[Byte] = {
    val md = new RIPEMD160.Digest
    md.digest(sha256(input))
  }
}
