package org.novelfs.pure.log.simple

import cats.Applicative
import cats.effect.{IO, LiftIO}
import org.log4s.Logger
import org.novelfs.pure.log.{ApplicativeLogger, LogLevel, SideEffectingLogger}

private [this] class SimpleLogger[F[_] : LiftIO : Applicative](logger : Logger) extends ApplicativeLogger[F] {
  override def applicative: Applicative[F] = Applicative[F]

  override def log(logLevel: LogLevel)(msg: String): F[Unit] =
    LiftIO[F].liftIO(IO { SideEffectingLogger.logWithLogger(logger)(logLevel)(msg) })

  override def error(e: Throwable)(msg: String): F[Unit] =
    LiftIO[F].liftIO(IO { SideEffectingLogger.logErrorWithLogger(logger)(e)(msg) })
}
