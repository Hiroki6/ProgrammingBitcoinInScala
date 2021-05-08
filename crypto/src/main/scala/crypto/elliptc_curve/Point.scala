package crypto.elliptc_curve

case class PointException(message: String) extends Exception {
  override def getMessage: String =
    s"ECPointException: $message"
}

trait Point[F <: Field[F], P <: Point[F, P]] {
  point: P =>
  val x: F
  val y: F
  val a: F
  val b: F

  def create(x: F, y: F, a: F, b: F): P

  def +(other: P): P = {
    if (a != other.a || b != other.b) {
      throw PointException(s"Points ($this, $other) are not on the same curve")
    } else {
      if (x == 0) other
      else if (other.x == 0) point
      else if (x == other.x && y != other.y)
        create(this.x.create(0, this.x.prime), this.y.create(0, this.y.prime), a, b)
      else if (x != other.x) {
        val s = (other.y - y) / (other.x - x)
        val newX = s ** 2 - x - other.x
        val newY = s * (x - newX) - y
        create(newX, newY, a, b)
      } else if (this == other && y == (x * 0))
        create(this.x.create(0, this.x.prime), this.y.create(0, this.y.prime), a, b)
      else {
        val s = (x ** 2 * 3 + a) / (y * 2)
        val newX = (s ** 2) - (other.x * 2)
        val newY = s * (x - newX) - y
        create(newX, newY, a, b)
      }
    }
  }

  def *(coefficient: Int): P = {
    *(BigInt(coefficient))
  }

  def *(coefficient: BigInt): P = {
    var coef = coefficient
    var current = point
    var result = create(this.x.create(0, this.x.prime), this.y.create(0, this.y.prime), a, b)
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
