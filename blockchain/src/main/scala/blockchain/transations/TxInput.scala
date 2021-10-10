package blockchain.transations
import blockchain.scripts.Script
import blockchain.utils.Encoders
import cats.effect.IO

abstract class TxInput(prevTx: Array[Byte], prevIndex: Int, scriptSig: Script, sequence: Int)
    extends UsesTxFetcher {
  def serialize(): Array[Byte] = {
    prevTx.reverse ++
      Encoders.intToLittleEndian(prevIndex, 4) ++
      scriptSig.serialize() ++
      Encoders.intToLittleEndian(sequence, 4)
  }

  def fetchTx(testnet: Boolean = false): IO[Transaction] =
    txFetcher.fetch(Integer.toHexString(prevIndex), testnet)

  def value(): IO[Int] = fetchTx().map { transaction => transaction.outputs(prevIndex).amount }
}

class TxInputImpl(prevTx: Array[Byte], prevIndex: Int, scriptSig: Script, sequence: Int)
    extends TxInput(prevTx, prevIndex, scriptSig, sequence)
    with MixInTxFetcher

object TxInput {
  def parse(input: Array[Byte]): IO[TxInput] = Script.parse(input).map { scriptSig =>
    val prevTx = input.slice(0, 32).reverse
    val prevIndex = Encoders.littleEndianToInt(input.slice(0, 4))
    val sequence = Encoders.littleEndianToInt(input.slice(0, 4))
    new TxInputImpl(prevTx, prevIndex, scriptSig, sequence)
  }
}
