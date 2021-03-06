package blockchain.transations
import blockchain.adapters.{MixInCacheInMemory, MixInHttpClient, UsesCache, UsesHttpClient}
import cats.effect.IO
import org.apache.commons.codec.binary.Hex
import org.http4s.Uri

trait TxFetcher extends UsesHttpClient with UsesCache[String, Transaction] {
  def getUrl(testnet: Boolean): String = {
    if (testnet) "http://testnet.programmingbitcoin.com"
    else "http://mainnet.programmingbitcoin.com"
  }

  def fetch(txId: String, testnet: Boolean = false, fresh: Boolean = false): IO[Transaction] = {
    cache.get(txId).flatMap { txOpt =>
      if (fresh || txOpt.isEmpty) {
        val url = Uri.unsafeFromString(s"${getUrl(testnet)}/tx/$txId.hex")
        for {
          res <- httpClient.get[String](url)
          raw = Hex.decodeHex(res)
          transaction <-
            if (raw(4) == 0) {
              Transaction.parse(raw.slice(0, 4) ++ raw.slice(6, raw.length), testnet)
            } else {
              Transaction.parse(raw, testnet)
            }
          id <- transaction.id()
          _ <-
            if (id != txId) {
              IO.raiseError(
                new IllegalArgumentException(
                  s"transaction id is not same: $txId vs ${transaction.id()}"))
            } else {
              cache.set(txId, transaction)
            }
        } yield transaction
      } else {
        txOpt.fold(throw new RuntimeException("something wrong.")) { transaction =>
          if (transaction.testnet != testnet) {
            val newTransaction = transaction.copy(testnet = testnet)
            cache.set(txId, newTransaction).map { _ => newTransaction }
          } else {
            IO(transaction)
          }
        }
      }
    }
  }
}

private object TxFetcherImpl
    extends TxFetcher
    with MixInHttpClient
    with MixInCacheInMemory[String, Transaction]

trait UsesTxFetcher {
  val txFetcher: TxFetcher
}

trait MixInTxFetcher {
  val txFetcher: TxFetcher = TxFetcherImpl
}
