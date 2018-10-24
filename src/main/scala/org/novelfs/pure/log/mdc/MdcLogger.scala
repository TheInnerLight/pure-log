package org.novelfs.pure.log.mdc

import cats.implicits._
import cats.Monad
import cats.effect.{IO, LiftIO}
import cats.mtl.ApplicativeAsk
import org.log4s.{MDC, getLogger}
import org.novelfs.pure.log.{LogLevel, MonadLogger, SideEffectingLogger}

private[this] class MdcLogger[F[_] : LiftIO : Monad, TContext](implicit toMdc : ToMdc[TContext], applicativeLocal : ApplicativeAsk[F, TContext]) extends MonadLogger[F] {
  private val logger = getLogger

  override val monad: Monad[F] = Monad[F]

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
