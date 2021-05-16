package concurrent_algebra.data_structures

import scala.collection.mutable.ListBuffer

/**
 * Fixed Size double linked list
 * Ex.
 */
class DoubleLinkedList[K, V](maxSize: Int) {
  private var head: Option[Node[K, V]] = None
  private var tail: Option[Node[K, V]] = None
  private var currentSize = 0

  /**
   * Add new value into the linked list.
   * When the size of the list reaches out to the max size, the tail node is removed.
   * @param value: added value
   * @return (addedNode, deletedValue)
   */
  def add(key: K, value: V): (Node[K, V], Option[K]) = {
    val newNode = Node(key, value)
    head match {
      case None =>
        head = Some(newNode)
        tail = head
      case Some(n) =>
        n.prev = Some(newNode)
        newNode.next = head
        head = Some(newNode)
    }
    val deleted: Option[K] = if (currentSize < maxSize) {
      currentSize += 1
      None
    } else {
      val key = tail.map(_.key)
      tail.foreach { n => tail = n.prev }
      tail.foreach { n => n.next = None }
      key
    }
    (newNode, deleted)
  }

  /**
   * Remove the specified node from the list
   * @todo enable to delete tail node
   */
  def remove(node: Node[K, V]): Unit = {
    val nextNode = node.next
    val prevNode = node.prev

    nextNode.foreach(n => n.prev = prevNode)
    prevNode.foreach(n => n.next = nextNode)
    currentSize -= 1
  }

  // TODO: check if the deleted value is always None
  def moveToHead(node: Node[K, V]): Node[K, V] = {
    val newKey = node.key
    val newValue = node.value
    remove(node)
    add(newKey, newValue)._1
  }

  def toList(): List[V] = {
    val listBuffer = ListBuffer.empty[V]
    var curr = head
    while (curr.isDefined) {
      listBuffer.append(curr.get.value)
      curr = curr.flatMap(_.next)
    }
    listBuffer.toList
  }
}

case class Node[K, V](key: K, value: V) {
  var prev: Option[Node[K, V]] = None
  var next: Option[Node[K, V]] = None

  def update(newValue: V): Node[K, V] = {
    this.copy(value = newValue)
  }
}

object DoubleLinkedList1 extends App {
  val list = new DoubleLinkedList[Int, String](10)
  list.add(1, "test1")
  list.add(2, "test2")
  list.add(3, "test3")
  list.add(4, "test4")
  list.add(6, "test5")
  list.add(5, "test6")
  println(list.toList())
}
