package concurrent_algebra.caches

import cats.MonadError
import cats.effect.{Concurrent, Ref}
import cats.syntax.functor._
import com.typesafe.scalalogging.{LazyLogging, Logger}
import concurrent_algebra.data_structures.{DoubleLinkedList, Node}

import scala.collection.mutable
import scala.language.higherKinds

sealed trait LRUCache[F[_], K, V] {

  /**
   * save the key and value in the cache
   */
  def put(key: K, value: V): F[Unit]

  /**
   * get value by the key
   */
  def get(key: K): F[Option[V]]

  /**
   * delete the key and the corresponding value from the cache
   */
  def remove(key: K): F[Unit]

  /**
   * size used of the cache
   */
  def size(): F[Long]
}

object LRUCache extends LazyLogging {
  override lazy val logger: Logger = Logger("LRUCache")
  def of[F[_], K, V](capacity: Int)(
      implicit S: Concurrent[F],
      ME: MonadError[F, Throwable]): F[LRUCache[F, K, V]] = {
    Ref.of[F, mutable.Map[K, Node[K, V]]](mutable.Map.empty[K, Node[K, V]]).map { ref =>
      val linkedList = new DoubleLinkedList[K, V](capacity)
      new LRUCache[F, K, V] {
        def put(key: K, value: V): F[Unit] = ref.update { cache =>
          cache.get(key) match {
            case Some(node) =>
              // update value when this value is different
              if (node.value != value) {
                linkedList.remove(node)
                val (newNode, _) = linkedList.add(key, value)
                cache.updated(key, newNode)
              } else {
                cache
              }
            case None =>
              val (newNode, deletedValue) = linkedList.add(key, value)
              deletedValue.map { k =>
                logger.info("key is removed from the cache. {}", k)
                cache.remove(k)
              }
              cache.updated(key, newNode)
          }
        }

        /**
         * When the key is found, the key comes to head in the linked list
         */
        def get(key: K): F[Option[V]] = ref.get.map { cache =>
          cache.get(key).map { node =>
            val newNode = linkedList.moveToHead(node)
            cache.update(key, newNode)
            node.value
          }
        }

        def remove(key: K): F[Unit] = ref.get.map { cache =>
          cache.get(key) match {
            case Some(node) =>
              linkedList.remove(node)
              cache - key
            case None =>
              logger.debug("key not found: {}", key)
          }
        }

        def size(): F[Long] = ref.get.map { cache => cache.size }
      }
    }
  }
}
