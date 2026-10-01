package fpinscala.parallelism

import java.util.concurrent.{CountDownLatch, Executors, TimeUnit}
import java.util.concurrent.atomic.AtomicInteger
import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 7: parallelism - Actor
 *
 * `Actor.scala` is provided infrastructure (no `???` exercises). This is a
 * smoke test confirming the actor processes every message it receives.
 */
class ActorSpec extends AnyFunSuite {

  test("an Actor processes all messages sent to it") {
    val es = Executors.newFixedThreadPool(2)
    val n = 1000
    val sum = new AtomicInteger(0)
    val latch = new CountDownLatch(n)
    val actor = Actor[Int](es) { i =>
      sum.addAndGet(i)
      latch.countDown()
    }

    (1 to n).foreach(actor ! _)

    assert(latch.await(5, TimeUnit.SECONDS), "actor did not process all messages in time")
    assert(sum.get() == n * (n + 1) / 2)
    es.shutdown()
  }

  test("Strategy.sequential evaluates in the calling thread") {
    val s = Strategy.sequential
    val thunk = s(21 + 21)
    assert(thunk() == 42)
  }
}
