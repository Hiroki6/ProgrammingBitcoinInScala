package crypto.elliptc_curve.s256

case class Signature(r: BigInt, s: BigInt) {
  def der(): Array[Byte] = {
    val rBin = r.toByteArray
    val sBin = s.toByteArray
    val tmp = Array(0x02.byteValue(), rBin.length.byteValue()) ++ rBin ++ Array(
      0x02.byteValue(),
      sBin.length.byteValue()) ++ sBin
    Array(0x30.byteValue(), tmp.length.byteValue()) ++ tmp
  }
}
