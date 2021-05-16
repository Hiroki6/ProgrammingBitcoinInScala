package blockchain.utils
import crypto.elliptc_curve.utils.Numbers.toBigInt

import java.nio.{ByteBuffer, ByteOrder}
import scala.util.Try

object Encoders {
  def encodeVarInt(value: Int): Try[Array[Byte]] = Try {
    if (value < 0xfd) {
      Array(value.byteValue())
    } else if (value < 0x10000) {
      Array(0xfd.byteValue()) ++ intToLittleEndian(value, 2)
    } else if (value < toBigInt("100000000")) {
      Array(0xfe.byteValue()) ++ intToLittleEndian(value, 4)
    } else if (value < toBigInt("10000000000000000")) {
      Array(0xff.byteValue()) ++ intToLittleEndian(value, 8)
    } else {
      throw new IllegalArgumentException(s"integer too large: ${value}")
    }
  }

  def decodeVarInt(bytes: Array[Byte]): Try[Int] = Try {
    if (bytes.length < 1) {
      throw new IllegalArgumentException("bytes is empty.")
    } else {
      val prefix = bytes(0)
      if (prefix == 0xfd) {
        littleEndianToInt(bytes.slice(2, bytes.length))
      } else if (prefix == 0xfe) {
        littleEndianToInt(bytes.slice(4, bytes.length))
      } else if (prefix == 0xff) {
        littleEndianToInt(bytes.slice(8, bytes.length))
      } else prefix.toInt
    }
  }

  def intToLittleEndian(i: Int, length: Int): Array[Byte] = {
    val bb = ByteBuffer.allocate(length)
    bb.order(ByteOrder.LITTLE_ENDIAN)
    bb.putInt(i)
    bb.array()
  }

  def littleEndianToInt(bytes: Array[Byte]): Int = {
    val bb = ByteBuffer.wrap(bytes)
    bb.order(ByteOrder.LITTLE_ENDIAN)
    bb.getInt
  }
}
