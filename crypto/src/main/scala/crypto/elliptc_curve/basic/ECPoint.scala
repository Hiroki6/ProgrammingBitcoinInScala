package crypto.elliptc_curve.basic

import crypto.elliptc_curve.{Point, PointException}

import scala.util.{Failure, Success, Try}

case class ECPoint(x: ECField, y: ECField, a: ECField, b: ECField)
    extends Point[ECField, ECPoint] {
  override def create(x: ECField, y: ECField, a: ECField, b: ECField): ECPoint =
    ECPoint(x, y, a, b)
}

object ECPoint {
  def apply(x: ECField, y: ECField, a: ECField, b: ECField): ECPoint = {
    if (x != 0 && y != 0) {
      if ((y ** 2) != ((x ** 3) + (a * x) + b)) {
        throw PointException(s"Points ($x, $y) are not on the same curve")
      }
    }
    new ECPoint(x, y, a, b) {}
  }
}

object ECPointSample {
  def main(args: Array[String]): Unit = {
    Try(test2()) match {
      case Success(point) => println(point)
      case Failure(ex) => println(ex.getMessage)
    }
    test3()
  }

  def test1() = {
    val a = ECField(0, 223)
    val b = ECField(7, 223)
    val x = ECField(192, 223)
    val y = ECField(105, 223)
    ECPoint(x, y, a, b)
  }

  def test2() = {
    val a = ECField(0, 223)
    val b = ECField(7, 223)
    val x1 = ECField(143, 223)
    val y1 = ECField(98, 223)
    val x2 = ECField(76, 223)
    val y2 = ECField(66, 223)
    val p1 = ECPoint(x1, y1, a, b)
    val p2 = ECPoint(x2, y2, a, b)
    p1 + p2
  }

  def test3() = {
    val a = ECField(0, 223)
    val b = ECField(7, 223)
    val x1 = ECField(143, 223)
    val y1 = ECField(98, 223)
    val p1 = ECPoint(x1, y1, a, b)
    assert((p1 + p1) == p1 * 2)
    assert((p1 * 2 + p1) == p1 * 3)
  }
}
