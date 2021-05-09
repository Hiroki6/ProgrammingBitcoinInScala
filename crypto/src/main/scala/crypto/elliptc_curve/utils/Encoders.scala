package crypto.elliptc_curve.utils
import java.security.MessageDigest

object Encoders {
  private val BASE58_ALPHABET: Array[Char] =
    "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toArray
  private val ENCODED_ZERO = BASE58_ALPHABET(0)

  /**
   * This is a copy of the original code.
   * [[https://github.com/bitcoinj/bitcoinj/blob/master/core/src/main/java/org/bitcoinj/core/Base58.java]]
   */
  def base58(input: Array[Byte]): String = {
    import java.util
    if (input.length == 0) return ""
    // Count leading zeros.
    var zeros = 0
    while ({ zeros < input.length && (input(zeros) == 0) }) zeros += 1
    // Convert base-256 digits to base-58 digits (plus conversion to ASCII characters)
    val tmpInput = util.Arrays.copyOf(input, input.length) // since we modify it in-place

    val encoded = new Array[Char](tmpInput.length * 2) // upper bound
    var outputStart = encoded.length
    var inputStart = zeros
    while ({ inputStart < tmpInput.length }) {
      encoded { outputStart -= 1; outputStart } = BASE58_ALPHABET(
        divmod(tmpInput, inputStart, 256, 58))
      if (tmpInput(inputStart) == 0) inputStart += 1 // optimization - skip leading zeros
    }
    // Preserve exactly as many leading encoded zeros in output as there were leading zeros in input.
    while ({ outputStart < encoded.length && encoded(outputStart) == ENCODED_ZERO })
      outputStart += 1
    while ({ { zeros -= 1; zeros } >= 0 }) encoded {
      outputStart -= 1; outputStart
    } = ENCODED_ZERO
    // Return encoded string (including encoded leading zeros).
    new String(encoded, outputStart, encoded.length - outputStart)
  }

  def base58Checksum(input: Array[Byte]): String = {
    val hash = Hash.sha256(Hash.sha256(input))
    base58(input ++ hash.slice(0, 4))
  }

  private def divmod(number: Array[Byte], firstDigit: Int, base: Int, divisor: Int) = { // this is just long division which accounts for the base of the input digits
    var remainder = 0
    for (i <- firstDigit until number.length) {
      val digit = number(i).toInt & 0xff
      val temp = remainder * base + digit
      number(i) = (temp / divisor).toByte
      remainder = temp % divisor
    }
    remainder.toByte
  }
}
