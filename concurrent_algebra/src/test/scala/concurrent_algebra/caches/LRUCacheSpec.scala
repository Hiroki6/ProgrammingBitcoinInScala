package concurrent_algebra.caches

import cats.effect.IO
import cats.effect.testing.specs2.CatsEffect
import cats.effect.unsafe.implicits.global
import org.specs2.mutable.Specification

class LRUCacheSpec extends Specification with CatsEffect {
  "LRUCache methods" >> {
    "success to put values" >> {
      val result =
        for {
          cache <- LRUCache.of[IO, String, String](10)
          _ <- cache.put("key1", "value1")
          _ <- cache.put("key2", "value2")
          _ <- cache.put("key3", "value3")
          k <- cache.get("key1")
        } yield {
          k
        }
      result.unsafeRunSync() must beSome("value1")
    }
    "value not found when the key already deleted" >> {
      val result =
        for {
          cache <- LRUCache.of[IO, String, String](2)
          _ <- cache.put("key1", "value1")
          _ <- cache.put("key2", "value2")
          _ <- cache.put("key3", "value3")
          k <- cache.get("key1")
        } yield {
          k
        }
      result.unsafeRunSync() must beNone
    }
  }
}
