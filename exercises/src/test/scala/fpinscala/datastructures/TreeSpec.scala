package fpinscala.datastructures

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 3: datastructures - Tree
 *
 * The exercise stub `Tree.scala` only defines the data type; the Tree
 * exercises (size, maximum, depth, map, fold) are implemented by the
 * reader. These tests exercise the data type and provide a place to add
 * assertions as you implement each exercise's companion-object function.
 */
class TreeSpec extends AnyFunSuite {

  val tree: Tree[Int] = Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))

  test("Tree data constructors build the expected structure") {
    assert(tree == Branch(Branch(Leaf(1), Leaf(2)), Leaf(3)))
  }

  test("Leaf holds its value") {
    assert(Leaf(42).value == 42)
  }

  // As you implement the Tree exercises (3.25 size, 3.26 maximum,
  // 3.27 depth, 3.28 map, 3.29 fold) in the Tree companion object,
  // add assertions here, e.g.:
  //
  //   assert(Tree.size(tree) == 5)
  //   assert(Tree.maximum(tree) == 3)
  //   assert(Tree.depth(tree) == 2)
  //   assert(Tree.map(tree)(_ + 1) == Branch(Branch(Leaf(2), Leaf(3)), Leaf(4)))
}
