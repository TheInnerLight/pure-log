package org.novelfs.pure.log.simple

import cats.Applicative
import cats.effect.{IO, LiftIO}
import org.log4s.Logger
import org.novelfs.pure.log.{ApplicativeLogger, LogLevel, SideEffectingLogger}

private [this] class SimpleLogger[F[_] : LiftIO : Applicative](logger : Logger) extends ApplicativeLogger[F] {
  override def applicative: Applicative[F] = Applicative[F]

  override def log(logLevel: LogLevel)(msg: String): F[Unit] =
    LiftIO[F].liftIO(IO { SideEffectingLogger.logWithLogger(logger)(logLevel)(msg) })

  override def logThrowable(logLevel: LogLevel)(e: Throwable)(msg: String): F[Unit] =
    LiftIO[F].liftIO(IO { SideEffectingLogger.logThrowableWithLogger(logger)(logLevel)(e)(msg) })
}
