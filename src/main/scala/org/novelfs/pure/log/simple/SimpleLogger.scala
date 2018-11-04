package org.novelfs.pure.log.simple

import cats.Monad
import cats.effect.{IO, LiftIO}
import org.log4s.Logger
import org.novelfs.pure.log.{LogLevel, MonadLogger, SideEffectingLogger}

private [this] class SimpleLogger[F[_] : LiftIO : Monad](logger : Logger) extends MonadLogger[F] {
  override def log(logLevel: LogLevel)(msg: String): F[Unit] =
    LiftIO[F].liftIO(IO { SideEffectingLogger.logWithLogger(logger)(logLevel)(msg) })

  override def monad: Monad[F] = Monad[F]
}
