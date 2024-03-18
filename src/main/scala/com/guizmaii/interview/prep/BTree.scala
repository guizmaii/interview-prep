package com.guizmaii.interview.prep

import com.guizmaii.interview.prep.BTree._

import scala.collection.mutable

sealed abstract class BTree[T]
object BTree {
  final case class Leaf[T](value: T)                                  extends BTree[T]
  final case class Node[T](value: T, left: BTree[T], right: BTree[T]) extends BTree[T]

  /**
   * Stack unsafe version of the in order traversal of a binary tree.
   */
  def stackUnsafeInOrderTraversal[T](node: BTree[T]): Unit =
    node match {
      case BTree.Leaf(value)              => println(value)
      case BTree.Node(value, left, right) =>
        stackUnsafeInOrderTraversal(left)
        println(value)
        stackUnsafeInOrderTraversal(right)
    }

  /**
   * Stack safe version of the in order traversal of a binary tree.
   */
  def stackSafeInOrderTraversal[T](root: BTree[T]): Unit = {
    val st = mutable.Stack[BTree[T]]()
    st.push(root)
    while (st.nonEmpty) {
      st.pop() match {
        case Node(v, left, right) =>
          st.push(right)
          st.push(Leaf(v))
          st.push(left)
        case Leaf(v)              =>
          println(v)
      }
    }
  }
}

object BTreeMain extends App {
  val t = Node(4, Node(2, Leaf(1), Leaf(3)), Node(6, Leaf(5), Leaf(7)))

  println("stack unsafe in order traversal:")
  stackUnsafeInOrderTraversal(t)
  println("\nstack safe in order traversal:")
  stackSafeInOrderTraversal(t)
}
