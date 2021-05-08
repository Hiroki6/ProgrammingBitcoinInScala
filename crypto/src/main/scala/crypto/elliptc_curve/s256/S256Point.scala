package crypto.elliptc_curve.s256

import crypto.elliptc_curve.Point
import crypto.elliptc_curve.s256.S256Point._
import crypto.elliptc_curve.utils.Numbers._
import crypto.elliptc_curve.s256.S256Point.BigIntWrapper

case class S256Point(x: S256Field, y: S256Field) extends Point[S256Field, S256Point] {
  val a: S256Field = S256Field(A)
  val b: S256Field = S256Field(B)

  override def create(x: S256Field, y: S256Field, a: S256Field, b: S256Field): S256Point =
    S256Point(x, y)

  override def *(coefficient: Int): S256Point = {
    val coef = coefficient % N
    super.*(coef.intValue())
  }

  override def *(coefficient: BigInt): S256Point = {
    val coef = coefficient.mod(N)
    super.*(coef)
  }

  def verify(z: BigInt, signature: Signature): Boolean = {
    val sInv = signature.s.modPow(N - 2, N)
    val u = (z * sInv).mod(N)
    val v = (signature.r * sInv).mod(N)
    (u * G + v * this).x.num == signature.r
  }
}

object S256Point {
  val A = 0
  val B = 7
  val N: BigInt = toBigInt("fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141")

  val G: S256Point = apply(
    toBigInt("79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798"),
    toBigInt("483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8")
  )

  def apply(x: BigInt, y: BigInt): S256Point = {
    S256Point(S256Field(x), S256Field(y))
  }

  implicit class IntWrapper(intValue: Int) {
    def *(point: S256Point): S256Point = {
      var coef = intValue % N
      var current = point
      var result = new S256Point(point.x.copy(num = 0), point.y.copy(num = 0))
      while (coef > 0) {
        if ((coef & 1) == 1) {
          result += current
        }
        current += current
        coef = coef / 2
      }
      result
    }
  }

  implicit class BigIntWrapper(bigIntValue: BigInt) {
    def *(point: S256Point): S256Point = {
      var coef = bigIntValue.mod(N)
      var current = point
      var result = new S256Point(point.x.copy(num = 0), point.y.copy(num = 0))
      while (coef > 0) {
        if ((coef & 1) == 1) {
          result += current
        }
        current += current
        coef = coef / 2
      }
      result
    }
  }
}
