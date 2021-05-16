package crypto.clliptc_curve.s256
import crypto.elliptc_curve.s256.PrivateKey
import crypto.elliptc_curve.s256.S256Point.N
import crypto.elliptc_curve.utils.Numbers.toBigInt
import org.specs2.{mutable, ScalaCheck}

import scala.util.Random

class PrivateKeySpec extends mutable.Specification with ScalaCheck {

  "signing" >> {
    val privKey = new PrivateKey(Random.nextInt(N.intValue.abs))
    val z = Random.nextInt(math.pow(2, 256).toInt)
    val signature = privKey.sign(z)
    privKey.point.verify(z, signature) must_== true
  }

  "wif" >> {
    val privateKey1 = new PrivateKey(BigInt(2).pow(256) - BigInt(2).pow(199))
    privateKey1.wif(
      compressed = true,
      testnet = false) must_== "L5oLkpV3aqBJ4BgssVAsax1iRa77G5CVYnv9adQ6Z87te7TyUdSC"

    val privateKey2 = new PrivateKey(BigInt(2).pow(256) - BigInt(2).pow(201))
    privateKey2.wif(
      compressed = false,
      testnet = true) must_== "93XfLeifX7Jx7n7ELGMAf1SUR6f9kgQs8Xke8WStMwUtrDucMzn"

    val privateKey3 = new PrivateKey(
      toBigInt("0dba685b4511dbd3d368e5c4358a1277de9486447af7b3604a69b8d9d8b7889d"))
    privateKey3.wif(
      compressed = false,
      testnet = false) must_== "5HvLFPDVgFZRK9cd4C5jcWki5Skz6fmKqi1GQJf5ZoMofid2Dty"

    val privateKey4 = new PrivateKey(
      toBigInt("1cca23de92fd1862fb5b76e5f4f50eb082165e5191e116c18ed1a6b24be6a53f"))
    privateKey4.wif(
      compressed = true,
      testnet = true) must_== "cNYfWuhDpbNM1JWc3c6JTrtrFVxU4AGhUKgw5f93NP2QaBqmxKkg"
  }
}
