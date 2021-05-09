package crypto.elliptc_curve.utils

object Numbers {
  def toBigInt(hexString: String): BigInt = {
    BigInt(hexString, 16)
  }

  def toHex(bigInt: BigInt): String = {
    bigInt.toString(16)
  }

  def toByteWithoutNull(bigInt: BigInt): Array[Byte] = {
    var bin = bigInt.toByteArray
    if (bin.nonEmpty && bin(0) == 0) {
      val tmp = Array.ofDim[Byte](bin.length - 1)
      Array.copy(bin, 1, tmp, 0, tmp.length)
      bin = tmp
    }
    bin
  }
}
