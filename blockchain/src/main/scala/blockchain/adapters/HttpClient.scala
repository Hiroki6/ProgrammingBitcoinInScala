package blockchain.adapters

import cats.effect.IO
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.{EntityDecoder, Method, Request, Uri}

trait HttpClient {
  def get[A](url: Uri)(implicit entityDecoder: EntityDecoder[IO, A]): IO[A] =
    BlazeClientBuilder[IO].withExecutionContext(GlobalResources.blockingEC).resource.use {
      client =>
        val request = Request[IO](Method.GET, url)
        client.expect[A](request)
    }
}

trait UsesHttpClient {
  val httpClient: HttpClient
}

private object HttpClientImpl extends HttpClient

trait MixInHttpClient {
  val httpClient: HttpClient = HttpClientImpl
}
