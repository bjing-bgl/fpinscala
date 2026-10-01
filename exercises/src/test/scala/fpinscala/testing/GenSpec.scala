package fpinscala.testing

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 8: testing - Gen / Prop
 *
 * `Gen.scala` is only the shell for the property-testing library that is
 * developed across the chapter. The data types (Gen, Prop, SGen) are
 * iterated on as you work through the exercises, so the stub exposes just
 * a minimal surface. These tests pin down the pieces that already have a
 * signature; add assertions as you grow the API.
 */
class GenSpec extends AnyFunSuite {

  // Exercise 8.4+ : Gen.unit should produce a generator that always yields
  // the given value. The internal representation of Gen is up to you, so we
  // only check that `unit` is callable and that `map`/`flatMap` compose.
  test("Gen.unit is defined and chainable") {
    val g: Gen[Int] = Gen.unit(1)
    val mapped: Gen[Int] = g.map[Int, Int](_ + 1)
    val chained: Gen[Int] = g.flatMap[Int, Int](a => Gen.unit(a * 2))
    // We can't observe sample values until you implement the underlying
    // representation; reaching this point means the API shape holds.
    assert(g != null && mapped != null && chained != null)
  }

  // Exercise 8.3+ : Prop.forAll should build a Prop from a Gen and predicate.
  test("Prop.forAll is defined") {
    val p: Prop = Prop.forAll(Gen.unit(1))(_ > 0)
    assert(p != null)
  }
}
