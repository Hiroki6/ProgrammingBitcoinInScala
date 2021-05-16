package blockchain.transations

case class Script(value: String) {
  def serialize(): Array[Byte] = ???
}

object Script {
  def parse(input: Array[Byte]): Script = ???
}
