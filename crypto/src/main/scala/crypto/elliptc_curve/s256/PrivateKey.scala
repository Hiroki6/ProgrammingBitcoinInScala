package crypto.elliptc_curve.s256
import crypto.elliptc_curve.s256.S256Point.{G, N}
import crypto.elliptc_curve.utils.{Encoders, Numbers}

import java.security.SecureRandom

class PrivateKey(secret: BigInt) {
  val point: S256Point = secret * G

  private val ranGen = new SecureRandom()

  def hex: String = Numbers.toHex(secret)

  def sign(z: BigInt): Signature = {
    val k = ranGen.nextInt(N.intValue().abs)
    val r = (k * G).x.num
    val kInv = BigInt(k).modPow(N - 2, N)
    var s = (z + r * secret) * kInv % N
    if (s > N / 2) {
      s = N - s
    }
    Signature(r, s)
  }

  /**
   * This method returns the private key as wallet import format
   */
  def wif(compressed: Boolean = true, testnet: Boolean = false): String = {
    val secretBytes = Numbers.toByteWithoutNull(secret)
    (compressed, testnet) match {
      case (true, true) =>
        Encoders.base58Checksum(
          Array(0xef.byteValue()) ++ secretBytes ++ Array(0x01.byteValue()))
      case (true, false) =>
        Encoders.base58Checksum(
          Array(0x80.byteValue()) ++ secretBytes ++ Array(0x01.byteValue()))
      case (false, true) => Encoders.base58Checksum(Array(0xef.byteValue()) ++ secretBytes)
      case (false, false) => Encoders.base58Checksum(Array(0x80.byteValue()) ++ secretBytes)
    }
  }
}
