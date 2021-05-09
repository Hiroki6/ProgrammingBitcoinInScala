package crypto.clliptc_curve.utils

import crypto.elliptc_curve.utils.Encoders
import org.specs2.mutable
import org.apache.commons.codec.binary.Hex

class EncodersSpec extends mutable.Specification {
  "base58" >> {
    val case1 = "7c076ff316692a3d7eb3c3bb0f8b1488cf72e1afcd929e29307032997a838a3d"
    val case2 = "eff69ef2b1bd93a66ed5219add4fb51e11a840f404876325a1e8ffe0529a2c"
    val case3 = "c7207fee197d27c618aea621406f6bf5ef6fca38681d82b2f06fddbdce6feab6"
    Encoders.base58(Hex.decodeHex(case1)) must_== "9MA8fRQrT4u8Zj8ZRd6MAiiyaxb2Y1CMpvVkHQu5hVM6"
    Encoders.base58(Hex.decodeHex(case2)) must_== "4fE3H2E6XMp4SsxtwinF7w9a34ooUrwWe4WsW1458Pd"
    Encoders.base58(Hex.decodeHex(case3)) must_== "EQJsjkd6JaGwxrjEhfeqPenqHwrBmPQZjJGNSCHBkcF7"
  }
}
