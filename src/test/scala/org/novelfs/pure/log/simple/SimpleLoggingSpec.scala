package org.novelfs.pure.log.simple

import cats.effect.IO
import cats.effect.concurrent.MVar
import org.log4s.{Logger => Log4sLogger}
import org.novelfs.pure.log.{LogLevel, ApplicativeLogger}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}
import org.slf4j.{Marker, Logger => Slf4jLogger}

class SimpleLoggingSpec extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  implicit val contextSwitch = IO.contextShift(scala.concurrent.ExecutionContext.global)
  implicit val concurrentEffect = IO.ioConcurrentEffect

  private trait SimpleLoggingSpecContext {

    val traceLogCalls = MVar.empty[IO, String].unsafeRunSync()
    val debugLogCalls = MVar.empty[IO, String].unsafeRunSync()
    val infoLogCalls = MVar.empty[IO, String].unsafeRunSync()
    val warnLogCalls = MVar.empty[IO, String].unsafeRunSync()
    val errorLogCalls = MVar.empty[IO, String].unsafeRunSync()

    // look at the interface segregation principle in action! ;)
    val mockSlf4jLogger = new Slf4jLogger {

      override def getName: String = ???

      override def debug(msg: String): Unit = debugLogCalls.put(msg).unsafeRunSync()

      override def debug(format: String, arg: scala.Any): Unit = ???

      override def debug(format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def debug(format: String, arguments: AnyRef*): Unit = ???

      override def debug(msg: String, t: Throwable): Unit = debugLogCalls.put(msg).unsafeRunSync()

      override def debug(marker: Marker, msg: String): Unit = ???

      override def debug(marker: Marker, format: String, arg: scala.Any): Unit = ???

      override def debug(marker: Marker, format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def debug(marker: Marker, format: String, arguments: AnyRef*): Unit = ???

      override def debug(marker: Marker, msg: String, t: Throwable): Unit = ???

      override def isWarnEnabled: Boolean = true

      override def isWarnEnabled(marker: Marker): Boolean = true

      override def error(msg: String): Unit = errorLogCalls.put(msg).unsafeRunSync()

      override def error(format: String, arg: scala.Any): Unit = ???

      override def error(format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def error(format: String, arguments: AnyRef*): Unit = ???

      override def error(msg: String, t: Throwable): Unit = errorLogCalls.put(msg).unsafeRunSync()

      override def error(marker: Marker, msg: String): Unit = ???

      override def error(marker: Marker, format: String, arg: scala.Any): Unit = ???

      override def error(marker: Marker, format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def error(marker: Marker, format: String, arguments: AnyRef*): Unit = ???

      override def error(marker: Marker, msg: String, t: Throwable): Unit = ???

      override def warn(msg: String): Unit = warnLogCalls.put(msg).unsafeRunSync()

      override def warn(format: String, arg: scala.Any): Unit = ???

      override def warn(format: String, arguments: AnyRef*): Unit = ???

      override def warn(format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def warn(msg: String, t: Throwable): Unit = warnLogCalls.put(msg).unsafeRunSync()

      override def warn(marker: Marker, msg: String): Unit = ???

      override def warn(marker: Marker, format: String, arg: scala.Any): Unit = ???

      override def warn(marker: Marker, format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def warn(marker: Marker, format: String, arguments: AnyRef*): Unit = ???

      override def warn(marker: Marker, msg: String, t: Throwable): Unit = ???

      override def trace(msg: String): Unit = traceLogCalls.put(msg).unsafeRunSync()

      override def trace(format: String, arg: scala.Any): Unit = ???

      override def trace(format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def trace(format: String, arguments: AnyRef*): Unit = ???

      override def trace(msg: String, t: Throwable): Unit = traceLogCalls.put(msg).unsafeRunSync()

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

      override def info(msg: String): Unit = infoLogCalls.put(msg).unsafeRunSync()

      override def info(format: String, arg: scala.Any): Unit = ???

      override def info(format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def info(format: String, arguments: AnyRef*): Unit = ???

      override def info(msg: String, t: Throwable): Unit = infoLogCalls.put(msg).unsafeRunSync()

      override def info(marker: Marker, msg: String): Unit = ???

      override def info(marker: Marker, format: String, arg: scala.Any): Unit = ???

      override def info(marker: Marker, format: String, arg1: scala.Any, arg2: scala.Any): Unit = ???

      override def info(marker: Marker, format: String, arguments: AnyRef*): Unit = ???

      override def info(marker: Marker, msg: String, t: Throwable): Unit = ???

    }

    val logger = new Log4sLogger(mockSlf4jLogger)

    implicit val simpleLogger = new SimpleLogger[IO](logger)
  }

  "simple logging at log level trace" should "call the underlying logger trace method with the correct message" in new SimpleLoggingSpecContext {
    forAll { (expectedLogMessage: String) =>
      ApplicativeLogger[IO].log(LogLevel.Trace)(expectedLogMessage).unsafeRunSync()
      val actualLogMessage = traceLogCalls.take.unsafeRunSync()
      actualLogMessage shouldBe expectedLogMessage
    }
  }

  "simple logging at log level debug" should "call the underlying logger debug method with the correct message" in new SimpleLoggingSpecContext {
    forAll { (expectedLogMessage: String) =>
      ApplicativeLogger[IO].log(LogLevel.Debug)(expectedLogMessage).unsafeRunSync()
      val actualLogMessage = debugLogCalls.take.unsafeRunSync()
      actualLogMessage shouldBe expectedLogMessage
    }
  }

  "simple logging at log level info" should "call the underlying logger info method with the correct message" in new SimpleLoggingSpecContext {
    forAll { (expectedLogMessage: String) =>
      ApplicativeLogger[IO].log(LogLevel.Info)(expectedLogMessage).unsafeRunSync()
      val actualLogMessage = infoLogCalls.take.unsafeRunSync()
      actualLogMessage shouldBe expectedLogMessage
    }
  }

  "simple logging at log level warn" should "call the underlying logger warn method with the correct message" in new SimpleLoggingSpecContext {
    forAll { (expectedLogMessage: String) =>
      ApplicativeLogger[IO].log(LogLevel.Warn)(expectedLogMessage).unsafeRunSync()
      val actualLogMessage = warnLogCalls.take.unsafeRunSync()
      actualLogMessage shouldBe expectedLogMessage
    }
  }

  "simple logging at log level error" should "call the underlying logger error method with the correct message" in new SimpleLoggingSpecContext {
    forAll { (expectedLogMessage: String) =>
      ApplicativeLogger[IO].log(LogLevel.Error)(expectedLogMessage).unsafeRunSync()
      val actualLogMessage = errorLogCalls.take.unsafeRunSync()
      actualLogMessage shouldBe expectedLogMessage
    }
  }

}
