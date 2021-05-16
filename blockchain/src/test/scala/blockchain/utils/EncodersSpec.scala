package blockchain.utils

import org.apache.commons.codec.binary.Hex
import org.specs2.mutable

class EncodersSpec extends mutable.Specification {
  "littleEndianToInt" >> {
    val hex1 = Hex.decodeHex("99c3980000000000")
    val expected1 = 10011545
    val hex2 = Hex.decodeHex("a135ef0100000000")
    val expected2 = 32454049
    Encoders.littleEndianToInt(hex1) must_== expected1
    Encoders.littleEndianToInt(hex2) must_== expected2
  }

  "intToLittleEndian" >> {
    val n1 = 1
    val expected1 = Array(0x01, 0x00, 0x00, 0x00).map(_.byteValue())
    val n2 = 10011545
    val expected2 = Array(0x99, 0xc3, 0x98, 0x00, 0x00, 0x00, 0x00, 0x00).map(_.byteValue())
    Encoders.intToLittleEndian(n1, 4) must_== expected1
    Encoders.intToLittleEndian(n2, 8) must_== expected2
  }
}
