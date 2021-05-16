package blockchain.transations

import blockchain.utils.Encoders
import cats.effect.IO

case class Transaction(
    version: Int,
    inputs: Seq[TxInput],
    outputs: Seq[TxOutput],
    lockTime: Int,
    testnet: Boolean) {
  def hash() = ???

  def id(): String = ???

  def serialize(): IO[Array[Byte]] = IO.fromTry {
    for {
      in <- Encoders.encodeVarInt(inputs.length)
      out <- Encoders.encodeVarInt(outputs.length)
    } yield {
      val inputSum = inputs.foldLeft(Array.emptyByteArray) { (result, input) =>
        result ++ input.serialize()
      }
      val outputSum = outputs.foldLeft(Array.emptyByteArray) { (result, output) =>
        result ++ output.serialize()
      }
      Encoders.intToLittleEndian(version, 4) ++ in ++ inputSum ++ out ++ outputSum ++ Encoders
        .intToLittleEndian(lockTime, 4)
    }
  }
}

object Transaction {
  def parse(serializeData: Array[Byte], testnet: Boolean): IO[Transaction] = IO {
    throw UnsupportedOperationException
  }
}
