package crypto.elliptc_curve.s256

import crypto.elliptc_curve.{Field, FieldElementException}
import crypto.elliptc_curve.s256.S256Field.P

import scala.math.BigInt.int2bigInt

case class S256Field(num: BigInt) extends Field[S256Field] {
  val prime: BigInt = P
  override def create(num: BigInt, prime: BigInt): S256Field = S256Field(num)
}

object S256Field {
  implicit class S256FieldWrapper(intValue: Int) {
    def *(field: S256Field): S256Field = {
      S256Field((field.num * intValue) mod field.prime)
    }

    def **(field: S256Field): S256Field = {
      val n = intValue mod (field.prime - BigInt(1))
      val newNum = field.num.modPow(n, field.prime)
      S256Field(newNum)
    }
  }
  val P: BigInt = BigInt(2).pow(256) - BigInt(2).pow(32) - 977

  def apply(num: BigInt): S256Field = {
    if (num >= P || num < 0) {
      throw FieldElementException(s"num $num is not in field range 0 to ${P - 1}")
    } else {
      new S256Field(num)
    }
  }
}
