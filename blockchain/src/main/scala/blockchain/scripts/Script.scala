package blockchain.scripts
import blockchain.utils.Encoders
import cats.effect.IO

import scala.collection.mutable.ListBuffer

case class Script(commands: Seq[OPCode.Value]) {
  def serialize(): Array[Byte] = ???
}

object Script {
  def parse(input: Array[Byte]): IO[Script] = {
    IO.fromTry(Encoders.decodeVarInt(input)).map { length =>
      val commands = ListBuffer.empty[OPCode.Value]
      var count = 0
      var current: Byte = 0x00.byteValue
      while (count < length) {
        current = input(1)
        count += 1
      }
      ???
    }
  }
}
