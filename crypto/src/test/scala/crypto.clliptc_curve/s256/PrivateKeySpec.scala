package crypto.clliptc_curve.s256
import crypto.elliptc_curve.s256.PrivateKey
import crypto.elliptc_curve.s256.S256Point.N
import org.specs2.{mutable, ScalaCheck}

import scala.util.Random

class PrivateKeySpec extends mutable.Specification with ScalaCheck {

  "signing" >> {
    "valid sinature" >> {
      val privKey = new PrivateKey(Random.nextInt(N.intValue().abs))
      val z = Random.nextInt(math.pow(2, 256).toInt)
      val signature = privKey.sign(z)
      privKey.point.verify(z, signature) must_== true
    }
  }
}
