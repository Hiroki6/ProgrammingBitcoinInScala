package crypto.clliptc_curve.s256

import crypto.elliptc_curve.s256.{S256Point, Signature}
import org.specs2.mutable

import scala.util.Random

class SignatureSpec extends mutable.Specification {
  "der format" >> {
    val Max = math.pow(2, 256).toInt
    val testCases = List(
      (1, 2),
      (Random.nextInt(Max), Random.nextInt(Max)),
      (Random.nextInt(Max), Random.nextInt(Max))
    )
    testCases.map {
      case (r, s) =>
        val sig = Signature(r, s)
        val der = sig.der()
        val sig2 = S256Point.parse(der)
        1 must_== 1
    }
  }
}
