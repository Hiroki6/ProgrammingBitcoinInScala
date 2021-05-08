package crypto.clliptc_curve.s256
import crypto.elliptc_curve.FieldElementException
import crypto.elliptc_curve.s256.S256Field
import org.scalacheck.{Gen, Prop}
import org.specs2.ScalaCheck
import org.specs2.mutable

class S256FieldSpec extends mutable.Specification with ScalaCheck {
  "apply" >> Prop.forAll(Gen.choose(-1000, 1000)) { (num: Int) =>
    if (num >= 0) {
      val s256Field = S256Field(num)
      s256Field.num must_== num
      s256Field.prime must_== S256Field.P
    } else {
      S256Field(num) must throwA[FieldElementException]
    }
  }
}
