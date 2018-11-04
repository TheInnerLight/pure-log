package org.novelfs.pure.log.mdc

import cats.implicits._
import cats.Monad
import cats.effect.{IO, LiftIO}
import cats.mtl.ApplicativeAsk
import org.log4s.{Logger, MDC}
import org.novelfs.pure.log.{LogLevel, MonadLogger, SideEffectingLogger}

private[this] class MdcLogger[F[_] : LiftIO : Monad, TContext](logger : Logger)(implicit toMdc : ToMdc[TContext], applicativeLocal : ApplicativeAsk[F, TContext]) extends MonadLogger[F] {
  override def monad: Monad[F] = Monad[F]

  override def log(logLevel: LogLevel)(msg: String): F[Unit] =
    for {
      context <- applicativeLocal.ask
      mdcMap = toMdc.toMdc(context)
      result <- LiftIO[F].liftIO(IO {
        MDC.withCtx(mdcMap.toSeq : _*) {
          SideEffectingLogger.logWithLogger(logger)(logLevel)(msg)
        }
      })
    } yield result
}
