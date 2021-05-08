package crypto.elliptc_curve.utils

object Numbers {
  def toBigInt(hexString: String): BigInt = {
    BigInt(hexString, 16)
  }

  def toHex(bigInt: BigInt): String = {
    bigInt.toString(16)
  }
}
