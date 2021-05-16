package concurrent_algebra.data_structures

import org.specs2.mutable.Specification

class DoubleLinkedListSpec extends Specification {
  "add method" >> {
    "succeed to add values" >> {
      val doubleLinkedList = new DoubleLinkedList[Int, String](3)
      doubleLinkedList.add(1, "test")
      doubleLinkedList.add(2, "test1")
      doubleLinkedList.add(3, "test2")
      doubleLinkedList.toList() must_== List("test2", "test1", "test")
    }
    "remove the first element when the list reaches out the capacity" >> {
      val doubleLinkedList = new DoubleLinkedList[Int, String](2)
      doubleLinkedList.add(1, "test")
      doubleLinkedList.add(2, "test1")
      doubleLinkedList.add(3, "test2")
      doubleLinkedList.toList() must_== List("test2", "test1")
    }
  }
}
