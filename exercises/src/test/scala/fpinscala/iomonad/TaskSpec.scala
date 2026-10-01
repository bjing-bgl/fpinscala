package fpinscala.iomonad

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 13: iomonad - Task
 *
 * `Task` is wired to the `iomonad` package object, whose
 * `implicit val ioMonad = IO3.freeMonad[Par]` is initialized eagerly. Because
 * `freeMonad` is an exercise stub (`???`), loading the package object throws,
 * and ANY reference to `Task` (even `Task.now`) triggers that initializer and
 * would abort the suite. So Task-based tests cannot run until you implement
 * the free monad (Exercise 13.1) and the interpreter (`run`, Exercise 13.3).
 *
 * These tests are therefore marked `ignore`. Once `IO3.freeMonad` is
 * implemented, change `ignore` to `test` and fill in the bodies, e.g.:
 *
 *   import java.util.concurrent.Executors
 *   implicit val es = Executors.newFixedThreadPool(2)
 *   assert(Task.now(1).map(_ + 1).run == 2)
 *
 * In the meantime, the IO monad is covered by `IOSpec` (via the concrete
 * `IO1.IO`) and the general Monad combinators by `iomonad.MonadSpec`.
 */
class TaskSpec extends AnyFunSuite {

  ignore("Task.now/map/flatMap compose and run (needs IO3.freeMonad, ex. 13.1/13.3)") {
    // pending implementation of the free-monad interpreter
  }

  ignore("Task.handle recovers from failure (needs IO3 interpreter)") {
    // pending implementation of the free-monad interpreter
  }
}
