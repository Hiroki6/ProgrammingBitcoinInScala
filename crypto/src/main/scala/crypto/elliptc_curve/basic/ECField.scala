package crypto.elliptc_curve.basic

import crypto.elliptc_curve.{Field, FieldElementException}

case class ECField(num: BigInt, prime: BigInt) extends Field[ECField] {
  override def create(num: BigInt, prime: BigInt): ECField = ECField(num, prime)
}

object ECField {
  def apply(num: BigInt, prime: BigInt): ECField = {
    if (num >= prime || num < 0) {
      throw FieldElementException(s"num $num is not in field range 0 to ${prime - 1}")
    } else {
      new ECField(num, prime)
    }
  }
}
