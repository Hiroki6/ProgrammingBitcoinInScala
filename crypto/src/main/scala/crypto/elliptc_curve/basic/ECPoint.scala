package crypto.elliptc_curve.basic

import crypto.elliptc_curve.{Point, PointException}

case class ECPoint(x: ECField, y: ECField, a: ECField, b: ECField)
    extends Point[ECField, ECPoint] {
  override def create(x: ECField, y: ECField, a: ECField, b: ECField): ECPoint =
    ECPoint(x, y, a, b)
}

object ECPoint {
  def apply(x: ECField, y: ECField, a: ECField, b: ECField): ECPoint = {
    // want to use !=
    if (!x.equals(0) && !y.equals(0)) {
      if ((y ** 2) != ((x ** 3) + (a * x) + b)) {
        throw PointException(s"Points ($x, $y) are not on the same curve")
      }
    }
    new ECPoint(x, y, a, b) {}
  }
}
