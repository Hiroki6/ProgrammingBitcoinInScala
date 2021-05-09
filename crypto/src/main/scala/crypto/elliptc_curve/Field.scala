package crypto.elliptc_curve

import scala.math.BigInt.int2bigInt

case class FieldElementException(message: String) extends Exception {
  override def getMessage: String =
    s"FieldElementException: $message"
}

trait Field[F <: Field[F]] {
  val num: BigInt
  val prime: BigInt

  override def equals(obj: Any): Boolean = obj match {
    case f: Field[F] => num == f.num && prime == f.prime
    case i: Int => num == i
    case b: BigInt => num == b
    case _ => false
  }

  def create(num: BigInt, prime: BigInt): F

  def +(other: F): F = {
    if (prime != other.prime) {
      throw FieldElementException("cannot add two numbers in different .")
    } else {
      val newNum = (num + other.num) % prime
      create(newNum, prime)
    }
  }

  def -(other: F): F = {
    if (prime != other.prime) {
      throw FieldElementException("cannot add two numbers in different .")
    } else {
      val newNum = (num - other.num).mod(prime)
      create(newNum, prime)
    }
  }

  def /(other: F): F = {
    if (prime != other.prime) {
      throw FieldElementException("cannot add two numbers in different .")
    } else {
      val newNum = (num * other.num.modPow(prime - 2, prime)) % prime
      create(newNum, prime)
    }
  }

  def *(num: Int): F = {
    create((this.num * num).mod(prime), prime)
  }

  def *(other: F): F = {
    if (prime != other.prime) {
      throw FieldElementException("cannot add two numbers in different .")
    } else {
      val newNum = (num * other.num).mod(prime)
      create(newNum, prime)
    }
  }

  def **(exponent: Int): F = {
    val n = exponent % (prime - BigInt(1))
    val newNum = num.modPow(n, prime)
    create(newNum, prime)
  }

  def **(exponent: BigInt): F = {
    val n = exponent.mod(prime - BigInt(1))
    val newNum = num.modPow(n, prime)
    create(newNum, prime)
  }

}
