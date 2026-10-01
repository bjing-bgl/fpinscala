package fpinscala.parsing

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 9: parsing - Parsers
 *
 * `Parsers.scala` is the shell for the parser-combinator library you design
 * through the chapter: the `Parsers[Parser]` trait, its `ParserOps`, and
 * `Laws` are intentionally empty for you to fill in, and the concrete
 * `Parser` representation is up to you. Because the trait exposes no
 * primitives yet, we test the provided concrete support types `Location`
 * and `ParseError`. As you build out your `Parsers` instance, add a test
 * object implementing the trait and assert on `run` results.
 */
class ParsersSpec extends AnyFunSuite {

  test("Location computes line and column from an offset") {
    val input = "abc\ndef\nghi"
    // offset 5 points into the second line ('e')
    val loc = Location(input, 5)
    assert(loc.line == 2)
    assert(loc.col == 2)
  }

  test("Location at the very start is line 1") {
    val loc = Location("hello", 0)
    assert(loc.line == 1)
  }

  test("Location.advanceBy moves the offset forward") {
    val loc = Location("abcdef", 1).advanceBy(3)
    assert(loc.offset == 4)
  }

  test("Location.toError produces a ParseError carrying the location") {
    val loc = Location("abc", 1)
    val err = loc.toError("expected something")
    assert(err.stack == List((loc, "expected something")))
  }

  test("currentLine returns the line containing the offset") {
    val loc = Location("first\nsecond\nthird", 7)
    assert(loc.currentLine == "second")
  }
}
