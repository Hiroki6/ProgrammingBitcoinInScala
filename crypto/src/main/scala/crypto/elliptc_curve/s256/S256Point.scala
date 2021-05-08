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

object S256PointSample {

  def main(args: Array[String]): Unit = {
    println(test1())
    test2()
    println(test3())
  }

  def test1() = {
    val point: S256Point = S256Point(
      toBigInt("04519fac3d910ca7e7138f7013706f619fa8f033e6ec6e09370ea38cee6a7574"),
      toBigInt("82b51eab8c27c66e26c858a079bcdf4f1ada34cec420cafc7eac1a42216fb6c4")
    )

    val z = toBigInt("bc62d4b80d9e36da29c16c5d4d9f11731f36052c72401a76c23c0fb5a9b74423")
    val r = toBigInt("37206a0610995c58074999cb9767b87af4c4978db68c06e8e6e81d282047a7c6")
    val s = toBigInt("8ca63759c1157ebeaec0d03cecca119fc9a75bf8e6d0fa65c841c8e2738cdaec")
    val sInv = s.modPow(N - 2, N)
    val u = (z * sInv).mod(N)
    val v = (r * sInv).mod(N)
    (G * u + point * v).x.num == r
  }

  def test2() = {
    val x1 = S256Field(143)
    val y1 = S256Field(98)
    val x2 = S256Field(76)
    val y2 = S256Field(66)
    val p1 = S256Point(x1, y1)
    val p2 = S256Point(x2, y2)
    assert((p1 + p1) == p1 * 2)
    assert((p2 * 2 + p2) == (p2 * 3))
  }

  def test3() = {
    val point: S256Point = S256Point(
      toBigInt("887387e452b8eacc4acfde10d9aaf7f6d9a0f975aabb10d006e4da568744d06c"),
      toBigInt("61de6d95231cd89026e286df3b6ae4a894a3378e393e93a0f45b666329a0ae34")
    )

    val z = toBigInt("ec208baa0fc1c19f708a9ca96fdeff3ac3f230bb4a7ba4aede4942ad003c0f60")
    val r = toBigInt("ac8d1c87e51d0d441be8b3dd5b05c8795b48875dffe00b7ffcfac23010d3a395")
    val s = toBigInt("68342ceff8935ededd102dd876ffd6ba72d6a427a3edb13d26eb0781cb423c4")
    val sInv = s.modPow(N - 2, N)
    val u = (z * sInv).mod(N)
    val v = (r * sInv).mod(N)
    println((G * u + point * v))
    println(r)
    (G * u + point * v).x.num == r
  }
}
