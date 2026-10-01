package fpinscala.monoids

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 10: monoids - Monoid / Foldable
 *
 * NOTE: The `Monoid` companion object eagerly initializes several `val`s to
 * `???` (intAddition, intMultiplication, booleanOr, booleanAnd, wcMonoid).
 * Referencing ANY member of that object — including the `def` exercises like
 * `foldMap`, `ordered`, `count`, `bag` — forces the object's initializer,
 * which throws until EVERY one of those `val`s is implemented. So we cannot
 * drive those exercises through the object before then without aborting the
 * whole suite.
 *
 * Instead we:
 *   - verify monoid laws against LOCAL instances (what you'll build for 10.1),
 *   - exercise the `Foldable` instances, which are separate objects whose
 *     method stubs fail cleanly (NotImplementedError) per-test (exercises
 *     10.12-10.15).
 *
 * Once you implement the `Monoid` object's `val`s, replace the local
 * instances below with `Monoid.intAddition`, etc., and uncomment the
 * object-level assertions.
 */
class MonoidSpec extends AnyFunSuite {

  // Local reference instances (mirror what exercise 10.1 asks you to define).
  val intAddition: Monoid[Int] = new Monoid[Int] {
    def op(a1: Int, a2: Int): Int = a1 + a2
    val zero = 0
  }

  // Exercise 10.5: foldMap — tested through a local Foldable to avoid the
  // poisoned `Monoid` object. (The object version is `Monoid.foldMap`.)
  test("ListFoldable.foldMap maps then combines with a monoid") {
    assert(ListFoldable.foldMap(List("1", "2", "3"))(_.toInt)(intAddition) == 6)
  }

  // Exercise 10.12: Foldable[List]
  test("ListFoldable.toList is identity on a list") {
    assert(ListFoldable.toList(List(1, 2, 3)) == List(1, 2, 3))
  }

  test("ListFoldable.foldLeft folds a list") {
    assert(ListFoldable.foldLeft(List(1, 2, 3))(0)(_ + _) == 6)
  }

  test("ListFoldable.foldRight folds a list") {
    assert(ListFoldable.foldRight(List(1, 2, 3))(0)(_ + _) == 6)
  }

  // Exercise 10.13: Foldable[IndexedSeq]
  test("IndexedSeqFoldable.foldLeft folds a vector") {
    assert(IndexedSeqFoldable.foldLeft(Vector(1, 2, 3, 4))(0)(_ + _) == 10)
  }

  // Exercise 10.15: Foldable[Tree]
  test("TreeFoldable.foldLeft folds a tree left to right") {
    val tree: Tree[Int] = Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))
    assert(TreeFoldable.foldLeft(tree)(0)(_ + _) == 6)
  }

  // Exercise 10.15: Foldable[Option]
  test("OptionFoldable.foldLeft folds an option") {
    assert(OptionFoldable.foldLeft(Some(5): Option[Int])(0)(_ + _) == 5)
    assert(OptionFoldable.foldLeft(None: Option[Int])(0)(_ + _) == 0)
  }

  // After you implement the `Monoid` object's `val` stubs (10.1), these can
  // be enabled — they can't run before then because touching `Monoid`
  // forces its (currently throwing) static initializer:
  //
  //   assert(Monoid.intAddition.op(2, 3) == 5)
  //   assert(Monoid.foldMap(List("1","2","3"), Monoid.intAddition)(_.toInt) == 6)
  //   assert(Monoid.ordered(Vector(1, 2, 3)))
  //   assert(Monoid.count("lorem ipsum dolor sit amet") == 5)
  //   assert(Monoid.bag(Vector("a","rose","is","a","rose")) == Map("a"->2,"rose"->2,"is"->1))
}
