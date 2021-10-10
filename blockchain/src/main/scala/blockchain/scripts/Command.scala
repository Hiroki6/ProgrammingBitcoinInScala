package blockchain.scripts
import crypto.elliptc_curve.utils.Hash

import scala.collection.mutable

object Operations {
  def dup(stack: mutable.Stack[Array[Byte]]): Boolean = {
    operation(stack) {
      stack.append(stack.last)
    }
  }

  def hash256(stack: mutable.Stack[Array[Byte]]): Boolean = {
    operation(stack) {
      val element = stack.pop()
      stack.append(Hash.sha256(element))
    }
  }

  def hash160(stack: mutable.Stack[Array[Byte]]): Boolean = {
    operation(stack) {
      val element = stack.pop()
      stack.append(Hash.hash160(element))
    }
  }

  private def operation(stack: mutable.Stack[Array[Byte]])(sideEffect: => Unit): Boolean = {
    if (stack.length < 1) false
    else {
      sideEffect
      true
    }
  }

  def execute(code: OPCode.Value, stack: mutable.Stack[Array[Byte]]): Boolean = code match {
    case OPCode.OP_DUP => dup(stack)
    case OPCode.OP_HASH160 => hash160(stack)
    case OPCode.OP_HASH256 => hash256(stack)
  }
}

object OPCode extends Enumeration {
  val OP_DUP = Value(118)
  val OP_HASH160 = Value(169)
  val OP_HASH256 = Value(170)
}
