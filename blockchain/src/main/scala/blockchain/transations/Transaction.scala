package blockchain.transations

import blockchain.utils.Encoders
import cats.effect.IO
import crypto.elliptc_curve.utils.Hash
import org.apache.commons.codec.binary.Hex
import cats.syntax.traverse._

case class Transaction(
    version: Int,
    inputs: Seq[TxInput],
    outputs: Seq[TxOutput],
    lockTime: Int,
    testnet: Boolean) {
  def hash(): IO[Array[Byte]] =
    serialize().map(data => Hash.sha256(data.reverse))

  def id(): IO[String] =
    hash().map(Hex.encodeHexString)

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

  def fee(): IO[Int] = {
    val inputSum = inputs.traverse { input => input.value() }.map(_.sum)
    val outputSum = outputs.foldLeft(0) { (curr, output) => curr + output.amount }
    inputSum.map { _ - outputSum }
  }
}

object Transaction {
  def parse(serializeData: Array[Byte], testnet: Boolean): IO[Transaction] = IO.raiseError {
    new UnsupportedOperationException
  }
}
