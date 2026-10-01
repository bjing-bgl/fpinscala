package fpinscala.iomonad

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 13: iomonad - IO
 *
 * `IO.scala` develops several IO formulations (IO0..IO3) and the free monad
 * exercises (freeMonad, runTrampoline, run, step, translate, runConsole).
 * Those are deeply embedded design exercises; here we test the concrete,
 * fully-implemented `IO1.IO` monad as a baseline, and leave notes for the
 * free-monad exercises. Assertions added here will fail once you change the
 * provided code only if you break it.
 */
class IOSpec extends AnyFunSuite {

  // IO1.IO is a concrete, lawful IO monad provided by the chapter.
  test("IO1.IO.unit wraps a value and run executes it") {
    assert(IO1.IO.unit(42).run == 42)
  }

  test("IO1.IO.map transforms the result") {
    assert(IO1.IO.unit(10).map(_ + 1).run == 11)
  }

  test("IO1.IO.flatMap sequences effects") {
    val io = IO1.IO.unit(2).flatMap(a => IO1.IO.unit(a * 3))
    assert(io.run == 6)
  }

  test("IO1 IORef supports get/set/modify") {
    val program = for {
      ref <- IO1.IO.ref(0)
      _   <- ref.set(5)
      _   <- ref.modify(_ + 1)
      v   <- ref.get
    } yield v
    assert(program.run == 6)
  }

  test("fahrenheitToCelsius converts correctly") {
    assert(IO0.fahrenheitToCelsius(32.0) == 0.0)
    assert(IO0.fahrenheitToCelsius(212.0) == 100.0)
  }

  // Exercise 13.1 (freeMonad), 13.2 (runTrampoline), 13.3 (run), 13.4 (step):
  // implemented in IO3. As you implement them, add assertions such as:
  //
  //   val m = IO3.freeMonad[Function0]
  //   assert(IO3.runTrampoline(m.unit(1)) == 1)
}
