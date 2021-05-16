package blockchain.adapters

import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

object GlobalResources {
  val blockingPool: ExecutorService = Executors.newFixedThreadPool(10)
  val blockingEC: ExecutionContextExecutor = ExecutionContext.fromExecutor(blockingPool)
}
