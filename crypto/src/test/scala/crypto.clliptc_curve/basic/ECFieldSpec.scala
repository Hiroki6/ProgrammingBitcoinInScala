package crypto.clliptc_curve.basic
import crypto.elliptc_curve.FieldElementException
import crypto.elliptc_curve.basic.ECField
import org.scalacheck.{Gen, Prop}
import org.specs2.ScalaCheck
import org.specs2.mutable

class ECFieldSpec extends mutable.Specification with ScalaCheck {
  "apply" >> Prop.forAll(Gen.choose(-1000, 1000)) { (num: Int) =>
    val PRIME = 223
    if (num >= 0 && num < PRIME) {
      val ecField = ECField(num, PRIME)
      ecField.num must_== num
      ecField.prime must_== PRIME
    } else {
      ECField(num, PRIME) must throwA[FieldElementException]
    }
  }

  "Additions" >> {
    "same field after adding" >> {
      val a = ECField(7, 13)
      val b = ECField(12, 13)
      val c = ECField(6, 13)
      (a + b) must_== c
    }

    "different field after adding" >> {
      val a = ECField(7, 13)
      val b = ECField(11, 13)
      val c = ECField(6, 13)
      (a + b) must_!= c
    }
  }
}
