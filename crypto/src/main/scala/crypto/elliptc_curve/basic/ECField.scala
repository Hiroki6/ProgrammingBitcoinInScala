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

object ECFieldSample {
  def main(args: Array[String]): Unit = {
    initialization()
  }

  def initialization(): Unit = {
    val a = ECField(7, 13)
    val b = ECField(12, 13)
    println(a == b)
    println(a == a)
  }

  def additional(): Unit = {
    val a = ECField(7, 13)
    val b = ECField(12, 13)
    val c = ECField(6, 13)
    println(a + b)
    println(a + b == c)
  }

  def pow(): Unit = {
    val a = ECField(7, 13)
    val b = ECField(8, 13)
    val powed = a ** -3
    println(powed == b)
  }
}
