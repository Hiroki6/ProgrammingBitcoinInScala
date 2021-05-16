package blockchain.transations
import blockchain.utils.Encoders
import cats.effect.IO

case class TxInput(prevTx: Array[Byte], prevIndex: Int, scriptSig: Script, sequence: Int) {
  def serialize(): Array[Byte] = {
    prevTx.reverse ++
      Encoders.intToLittleEndian(prevIndex, 4) ++
      scriptSig.serialize() ++
      Encoders.intToLittleEndian(sequence, 4)
  }
}

object TxInput {
  def parse(input: Array[Byte]): IO[TxInput] = IO {
    val prevTx = input.slice(0, 32).reverse
    val prevIndex = Encoders.littleEndianToInt(input.slice(0, 4))
    val scriptSig = Script.parse(input)
    val sequence = Encoders.littleEndianToInt(input.slice(0, 4))
    TxInput(prevTx, prevIndex, scriptSig, sequence)
  }
}
