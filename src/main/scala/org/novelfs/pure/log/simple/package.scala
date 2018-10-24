package org.novelfs.pure.log

import cats.Monad
import cats.effect.{IO, LiftIO}
import org.log4s.getLogger

package object simple {
  implicit def simpleLogger[F[_] : LiftIO : Monad] = new MonadLogger[F] {
    private val logger = getLogger

    override def log(logLevel: LogLevel)(msg: String): F[Unit] =
      LiftIO[F].liftIO(IO { SideEffectingLogger.logWithLogger(logger)(logLevel)(msg) })

    override val monad: Monad[F] = Monad[F]
  }
}
