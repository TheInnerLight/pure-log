package org.novelfs.pure.log.mdc

import cats.data.ReaderT
import cats.effect.IO
import cats.effect.concurrent.MVar
import org.log4s.{MDC, Logger => Log4sLogger}
import org.novelfs.pure.log.{ApplicativeLogger, LogLevel}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}
import org.slf4j.{Marker, Logger => Slf4jLogger}
import cats.mtl.implicits._

class MdcLoggingSpec extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  implicit val contextSwitch = IO.contextShift(scala.concurrent.ExecutionContext.global)
  implicit val concurrentEffect = IO.ioConcurrentEffect

  private trait MdcLoggingSpecContext {

    val traceLogCalls = MVar.empty[IO, (String, Map[String, String])].unsafeRunSync()
    val debugLogCalls = MVar.empty[IO, (String, Map[String, String])].unsafeRunSync()
    val infoLogCalls = MVar.empty[IO, (String, Map[String, String])].unsafeRunSync()
    val warnLogCalls = MVar.empty[IO, (String, Map[String, String])].unsafeRunSync()
    val errorLogCalls = MVar.empty[IO, (String, Map[String, String])].unsafeRunSync()

    val traceThrowableLogCalls = MVar.empty[IO, (String, Throwable, Map[String, String])].unsafeRunSync()
    val debugThrowableLogCalls = MVar.empty[IO, (String, Throwable, Map[String, String])].unsafeRunSync()
    val infoThrowableLogCalls = MVar.empty[IO, (String, Throwable, Map[String, String])].unsafeRunSync()
    val warnThrowableLogCalls = MVar.empty[IO, (String, Throwable, Map[String, String])].unsafeRunSync()
    val errorThrowableLogCalls = MVar.empty[IO, (String, Throwable, Map[String, String])].unsafeRunSync()

    // look at the interface segregation principle in action! ;)
    val mockSlf4jLogger = new Slf4jLogger {

      override def getName: String = ???

      override def debug(msg: String): Unit = debugLogCalls.put((msg, MDC.toMap[String, String])).unsafeRunSync()

      override def debug(format: String, arg: scala.Any): Unit = ???

      override def debug(format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def debug(format: String, arguments: AnyRef*): Unit = ???

      override def debug(msg: String, t: Throwable): Unit = debugThrowableLogCalls.put((msg, t, MDC.toMap[String, String])).unsafeRunSync()

      override def debug(marker: Marker, msg: String): Unit = ???

      override def debug(marker: Marker, format: String, arg: scala.Any): Unit = ???

      override def debug(marker: Marker, format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def debug(marker: Marker, format: String, arguments: AnyRef*): Unit = ???

      override def debug(marker: Marker, msg: String, t: Throwable): Unit = ???

      override def isWarnEnabled: Boolean = true

      override def isWarnEnabled(marker: Marker): Boolean = true

      override def error(msg: String): Unit = errorLogCalls.put((msg, MDC.toMap[String, String])).unsafeRunSync()

      override def error(format: String, arg: scala.Any): Unit = ???

      override def error(format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def error(format: String, arguments: AnyRef*): Unit = ???

      override def error(msg: String, t: Throwable): Unit = errorThrowableLogCalls.put((msg, t, MDC.toMap[String, String])).unsafeRunSync()

      override def error(marker: Marker, msg: String): Unit = ???

      override def error(marker: Marker, format: String, arg: scala.Any): Unit = ???

      override def error(marker: Marker, format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def error(marker: Marker, format: String, arguments: AnyRef*): Unit = ???

      override def error(marker: Marker, msg: String, t: Throwable): Unit = ???

      override def warn(msg: String): Unit = warnLogCalls.put((msg, MDC.toMap[String, String])).unsafeRunSync()

      override def warn(format: String, arg: scala.Any): Unit = ???

      override def warn(format: String, arguments: AnyRef*): Unit = ???

      override def warn(format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def warn(msg: String, t: Throwable): Unit = warnThrowableLogCalls.put((msg, t, MDC.toMap[String, String])).unsafeRunSync()

      override def warn(marker: Marker, msg: String): Unit = ???

      override def warn(marker: Marker, format: String, arg: scala.Any): Unit = ???

      override def warn(marker: Marker, format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def warn(marker: Marker, format: String, arguments: AnyRef*): Unit = ???

      override def warn(marker: Marker, msg: String, t: Throwable): Unit = ???

      override def trace(msg: String): Unit = traceLogCalls.put((msg, MDC.toMap[String, String])).unsafeRunSync()

      override def trace(format: String, arg: scala.Any): Unit = ???

      override def trace(format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def trace(format: String, arguments: AnyRef*): Unit = ???

      override def trace(msg: String, t: Throwable): Unit = traceThrowableLogCalls.put((msg, t, MDC.toMap[String, String])).unsafeRunSync()

      override def trace(marker: Marker, msg: String): Unit = ???

      override def trace(marker: Marker, format: String, arg: scala.Any): Unit = ???

      override def trace(marker: Marker, format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def trace(marker: Marker, format: String, argArray: AnyRef*): Unit = ???

      override def trace(marker: Marker, msg: String, t: Throwable): Unit = ???

      override def isInfoEnabled: Boolean = true

      override def isInfoEnabled(marker: Marker): Boolean = true

      override def isErrorEnabled: Boolean = true

      override def isErrorEnabled(marker: Marker): Boolean = true

      override def isTraceEnabled: Boolean = true

      override def isTraceEnabled(marker: Marker): Boolean = true

      override def isDebugEnabled: Boolean = true

      override def isDebugEnabled(marker: Marker): Boolean = true

      override def info(msg: String): Unit = infoLogCalls.put((msg, MDC.toMap[String, String])).unsafeRunSync()

      override def info(format: String, arg: scala.Any): Unit = ???

      override def info(format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def info(format: String, arguments: AnyRef*): Unit = ???

      override def info(msg: String, t: Throwable): Unit = infoThrowableLogCalls.put((msg, t, MDC.toMap[String, String])).unsafeRunSync()

      override def info(marker: Marker, msg: String): Unit = ???

      override def info(marker: Marker, format: String, arg: scala.Any): Unit = ???

      override def info(marker: Marker, format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def info(marker: Marker, format: String, arguments: AnyRef*): Unit = ???

      override def info(marker: Marker, msg: String, t: Throwable): Unit = ???

    }

    val logger = new Log4sLogger(mockSlf4jLogger)

    implicit val stringMapToMdc = new ToMdc[Map[String, String]] {
      override def toMdc(item: Map[String, String]): Map[String, String] = item
    }

    implicit val mdcLogger = new MdcLogger[ReaderT[IO, Map[String, String], ?], Map[String, String]](logger)
  }

  "mdc logging at log level trace" should "call the underlying logger trace method with the correct message and get the MDC" in new MdcLoggingSpecContext {
    forAll { (expectedLogMessage: String, expectedMdc : Map[String, String]) =>
      ApplicativeLogger[ReaderT[IO, Map[String, String], ?]].log(LogLevel.Trace)(expectedLogMessage).run(expectedMdc).unsafeRunSync()
      val (actualLogMessage, actualMdc) = traceLogCalls.take.unsafeRunSync()
      actualMdc shouldBe expectedMdc
      actualLogMessage shouldBe expectedLogMessage
    }
  }

  "mdc logging at log level debug" should "call the underlying logger debug method with the correct message and get the MDC" in new MdcLoggingSpecContext {
    forAll { (expectedLogMessage: String, expectedMdc : Map[String, String]) =>
      ApplicativeLogger[ReaderT[IO, Map[String, String], ?]].log(LogLevel.Debug)(expectedLogMessage).run(expectedMdc).unsafeRunSync()
      val (actualLogMessage, actualMdc) = debugLogCalls.take.unsafeRunSync()
      actualMdc shouldBe expectedMdc
      actualLogMessage shouldBe expectedLogMessage
    }
  }

  "mdc logging at log level info" should "call the underlying logger info method with the correct message and get the MDC" in new MdcLoggingSpecContext {
    forAll { (expectedLogMessage: String, expectedMdc : Map[String, String]) =>
      val expectedMdc = Map("a" -> "1")
      ApplicativeLogger[ReaderT[IO, Map[String, String], ?]].log(LogLevel.Info)(expectedLogMessage).run(expectedMdc).unsafeRunSync()
      val (actualLogMessage, actualMdc) = infoLogCalls.take.unsafeRunSync()
      actualMdc shouldBe expectedMdc
      actualLogMessage shouldBe expectedLogMessage
    }
  }

  "mdc logging at log level warn" should "call the underlying logger warn method with the correct message and get the MDC" in new MdcLoggingSpecContext {
    forAll { (expectedLogMessage: String, expectedMdc : Map[String, String]) =>
      ApplicativeLogger[ReaderT[IO, Map[String, String], ?]].log(LogLevel.Warn)(expectedLogMessage).run(expectedMdc).unsafeRunSync()
      val (actualLogMessage, actualMdc) = warnLogCalls.take.unsafeRunSync()
      actualMdc shouldBe expectedMdc
      actualLogMessage shouldBe expectedLogMessage
    }
  }

  "mdc logging at log level error" should "call the underlying logger error method with the correct message and get the MDC" in new MdcLoggingSpecContext {
    forAll { (expectedLogMessage: String, expectedMdc : Map[String, String]) =>
      ApplicativeLogger[ReaderT[IO, Map[String, String], ?]].log(LogLevel.Error)(expectedLogMessage).run(expectedMdc).unsafeRunSync()
      val (actualLogMessage, actualMdc) = errorLogCalls.take.unsafeRunSync()
      actualMdc shouldBe expectedMdc
      actualLogMessage shouldBe expectedLogMessage
    }
  }

  "throwable mdc logging at log level trace" should "call the underlying logger trace method with the correct message and get the MDC" in new MdcLoggingSpecContext {
    forAll { (expectedLogMessage: String, expectedMdc : Map[String, String], e: Throwable) =>
      ApplicativeLogger[ReaderT[IO, Map[String, String], ?]].logThrowable(LogLevel.Trace)(e)(expectedLogMessage).run(expectedMdc).unsafeRunSync()
      val (actualLogMessage, throwable, actualMdc) = traceThrowableLogCalls.take.unsafeRunSync()
      actualMdc shouldBe expectedMdc
      actualLogMessage shouldBe expectedLogMessage
      throwable shouldBe e
    }
  }

  "throwable mdc logging at log level debug" should "call the underlying logger trace method with the correct message and get the MDC" in new MdcLoggingSpecContext {
    forAll { (expectedLogMessage: String, expectedMdc : Map[String, String], e: Throwable) =>
      ApplicativeLogger[ReaderT[IO, Map[String, String], ?]].logThrowable(LogLevel.Debug)(e)(expectedLogMessage).run(expectedMdc).unsafeRunSync()
      val (actualLogMessage, throwable, actualMdc) = debugThrowableLogCalls.take.unsafeRunSync()
      actualMdc shouldBe expectedMdc
      actualLogMessage shouldBe expectedLogMessage
      throwable shouldBe e
    }
  }

  "throwable mdc logging at log level info" should "call the underlying logger trace method with the correct message and get the MDC" in new MdcLoggingSpecContext {
    forAll { (expectedLogMessage: String, expectedMdc: Map[String, String], e: Throwable) =>
      ApplicativeLogger[ReaderT[IO, Map[String, String], ?]].logThrowable(LogLevel.Info)(e)(expectedLogMessage).run(expectedMdc).unsafeRunSync()
      val (actualLogMessage, throwable, actualMdc) = infoThrowableLogCalls.take.unsafeRunSync()
      actualMdc shouldBe expectedMdc
      actualLogMessage shouldBe expectedLogMessage
      throwable shouldBe e
    }
  }

  "throwable mdc logging at log level warn" should "call the underlying logger trace method with the correct message and get the MDC" in new MdcLoggingSpecContext {
    forAll { (expectedLogMessage: String, expectedMdc: Map[String, String], e: Throwable) =>
      ApplicativeLogger[ReaderT[IO, Map[String, String], ?]].logThrowable(LogLevel.Warn)(e)(expectedLogMessage).run(expectedMdc).unsafeRunSync()
      val (actualLogMessage, throwable, actualMdc) = warnThrowableLogCalls.take.unsafeRunSync()
      actualMdc shouldBe expectedMdc
      actualLogMessage shouldBe expectedLogMessage
      throwable shouldBe e
    }
  }

  "throwable mdc logging at log level error" should "call the underlying logger trace method with the correct message and get the MDC" in new MdcLoggingSpecContext {
    forAll { (expectedLogMessage: String, expectedMdc: Map[String, String], e: Throwable) =>
      ApplicativeLogger[ReaderT[IO, Map[String, String], ?]].logThrowable(LogLevel.Error)(e)(expectedLogMessage).run(expectedMdc).unsafeRunSync()
      val (actualLogMessage, throwable, actualMdc) = errorThrowableLogCalls.take.unsafeRunSync()
      actualMdc shouldBe expectedMdc
      actualLogMessage shouldBe expectedLogMessage
      throwable shouldBe e
    }
  }
}
