package com.guizmaii.interview.prep

abstract class BTree[T]
object BTree {
  final case class Leaf[T](value: T)                                    extends BTree[T]
  final case class Branch[T](left: BTree[T], value: T, right: BTree[T]) extends BTree[T]

  def inOrderTraversal[T](node: BTree[T]): Unit =
    node match {
      case BTree.Leaf(value)                => println(value)
      case BTree.Branch(left, value, right) =>
        println(value)
        inOrderTraversal(left)
        inOrderTraversal(right)
    }
}
