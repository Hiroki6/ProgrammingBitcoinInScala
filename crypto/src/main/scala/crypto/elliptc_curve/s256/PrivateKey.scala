package crypto.elliptc_curve.s256
import crypto.elliptc_curve.s256.S256Point.{G, N}
import crypto.elliptc_curve.utils.Numbers

import java.security.SecureRandom

class PrivateKey(secret: BigInt) {
  val point = secret * G

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
}
