package blockchain.transations
import blockchain.utils.Encoders
import cats.effect.IO

case class TxOutput(amount: Int, scriptPubKey: Script) {
  def serialize(): Array[Byte] = {
    Encoders.intToLittleEndian(amount, 8) ++ scriptPubKey.serialize()
  }
}

object TxOutput {
  def parse(input: Array[Byte]): IO[TxOutput] = IO {
    val amount = Encoders.littleEndianToInt(input.slice(0, 8))
    val scriptPubKey = Script.parse(input)
    TxOutput(amount, scriptPubKey)
  }
}
