package crypto.clliptc_curve.basic

import crypto.elliptc_curve.PointException
import crypto.elliptc_curve.basic.{ECField, ECPoint}
import org.specs2.mutable

class ECPointSpec extends mutable.Specification {
  "apply" >> {
    val prime = 223
    val fA = ECField(0, prime)
    val fB = ECField(7, prime)
    "valid points" >> {
      val validPoints = List((192, 105), (17, 56), (1, 193))
      validPoints.map {
        case (x, y) =>
          val fX = ECField(x, prime)
          val fY = ECField(y, prime)
          val point = ECPoint(fX, fY, fA, fB)
          point.x must_== fX
          point.y must_== fY
      }
    }

    "invalid points" >> {
      val validPoints = List((200, 119), (42, 99))
      validPoints.map {
        case (x, y) =>
          val fX = ECField(x, prime)
          val fY = ECField(y, prime)
          ECPoint(fX, fY, fA, fB) must throwA[PointException]
      }
    }
  }

  "Additions" >> {
    val prime = 223
    val fA = ECField(0, prime)
    val fB = ECField(7, prime)
    val additions = List(
      (192, 105, 17, 56, 170, 142),
      (47, 71, 117, 141, 60, 139),
      (143, 98, 76, 66, 47, 71)
    )
    additions.map {
      case (x1, y1, x2, y2, x3, y3) =>
        val fX1 = ECField(x1, prime)
        val fY1 = ECField(y1, prime)
        val point1 = ECPoint(fX1, fY1, fA, fB)
        val fX2 = ECField(x2, prime)
        val fY2 = ECField(y2, prime)
        val point2 = ECPoint(fX2, fY2, fA, fB)
        val fX3 = ECField(x3, prime)
        val fY3 = ECField(y3, prime)
        val point3 = ECPoint(fX3, fY3, fA, fB)
        (point1 + point2) must_== point3
    }
  }
}
