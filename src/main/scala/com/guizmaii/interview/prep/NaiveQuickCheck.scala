package com.guizmaii.interview.prep

import scala.sys.exit
import scala.util.Random

trait Gen[T] {
  def sample(): T
}

object Gen {
  def apply[T](implicit gen: Gen[T]): Gen[T] = gen

  private val random = new Random()

  implicit val unit: Gen[Unit]          = () => ()
  implicit val intGen: Gen[Int]         = () => random.nextInt()
  implicit val longGen: Gen[Long]       = () => random.nextLong()
  implicit val booleanGen: Gen[Boolean] = () => random.nextBoolean()
  implicit val byteGen: Gen[Byte]       = () => (random.nextInt(256) - 128).toByte
  implicit val shortGen: Gen[Short]     = () => (random.nextInt(65536) - 32768).toShort
  implicit val charGen: Gen[Char]       = () => random.nextInt(65536).toChar
  implicit val floatGen: Gen[Float]     = () => random.nextFloat()
  implicit val doubleGen: Gen[Double]   = () => random.nextDouble()
  implicit val stringGen: Gen[String]   = () => random.alphanumeric.take(10).mkString
}

sealed abstract class Result[T]
object Result {
  final case object Passed                     extends Result[Any]
  final case class Falsified[T](failedWith: T) extends Result[T]

  def passed[T]: Result[T]                   = Passed.asInstanceOf[Result[T]]
  def falsified[T](failedWith: T): Result[T] = Falsified(failedWith)
}

trait Property[T] {
  def name: String
  def check: Result[T]
  def isUnitTest: Boolean
}

final case class PropertyOptions(sampleSize: Int)

object NaiveQuickCheck {
  implicit val defaultOptions: PropertyOptions = PropertyOptions(sampleSize = 100)

  def test(_name: => String)(test: => Boolean): Property[Unit] =
    new Property[Unit] {
      override def name: String        = _name
      override def isUnitTest: Boolean = true
      override def check: Result[Unit] = if (test) Result.passed else Result.falsified(())
    }

  def forAll[T](_name: => String)(test: T => Boolean)(implicit gen: Gen[T], options: PropertyOptions): Property[T] =
    new Property[T] {
      override def name: String        = _name
      override def isUnitTest: Boolean = false // is a property, not a unit-test
      override def check: Result[T]    = {
        var i = 0
        while (i < options.sampleSize) {
          val value = gen.sample()
          if (!test(value)) return Result.falsified(value)
          i += 1
        }
        Result.passed
      }
    }

  def suite(name: String)(properties: Property[_]*): Unit = {
    println(Console.YELLOW + s"🔬\tRunning suite: $name")

    var exitValue = 0
    properties.foreach { p =>
      p.check match {
        case Result.Passed                       =>
          println(Console.GREEN + s"✅\t${p.name}")
        case Result.Falsified(_) if p.isUnitTest =>
          println(Console.RED + s"❌\t${p.name} - Test failed")
          exitValue = 1
        case Result.Falsified(failedWith)        =>
          println(Console.RED + s"❌\t${p.name} - Property failed with value: $failedWith")
          exitValue = 1
      }
    }
    exit(exitValue)
  }
}

// Usage
object PropertiesTest extends App {
  import NaiveQuickCheck._

  val intProperty    = forAll[Int]("int + int <=> int * 2")(n => n + n == 2 * n)
  val stringProperty = forAll[String]("s0 + s0 => s0.length * 2")(s => (s + s).length == 2 * s.length)
  val addtitionTest  = test("1 + 1 == 2")(1 + 1 == 2)

  suite("My properties suite")(
    intProperty,
    stringProperty,
    addtitionTest,
  )
}
