package blockchain.adapters
import cats.effect.IO

import scala.collection.mutable

trait Cache[K, V] {
  def get(key: K): IO[Option[V]]
  def set(key: K, value: V): IO[Unit]
  def contains(key: K): IO[Boolean]
}

trait UsesCache[K, V] {
  val cache: Cache[K, V]
}

trait MixInCacheWithMap[K, V] extends Cache[K, V] {
  val cache: Cache[K, V] = new Cache[K, V] {
    val map = mutable.Map.empty[K, V]
    override def get(key: K): IO[Option[V]] = IO(map.get(key))
    override def set(key: K, value: V): IO[Unit] = IO(map.update(key, value))
    override def contains(key: K): IO[Boolean] = IO(map.contains(key))
  }
}
