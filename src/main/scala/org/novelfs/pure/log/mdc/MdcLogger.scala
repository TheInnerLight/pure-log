package org.novelfs.pure.log.mdc

import cats.implicits._
import cats.{Applicative, Monad}
import cats.effect.{IO, LiftIO}
import cats.mtl.ApplicativeAsk
import org.log4s.{Logger, MDC}
import org.novelfs.pure.log.{ApplicativeLogger, LogLevel, SideEffectingLogger}

private[this] class MdcLogger[F[_] : LiftIO : Monad, TContext](logger : Logger)(implicit toMdc : ToMdc[TContext], applicativeLocal : ApplicativeAsk[F, TContext]) extends ApplicativeLogger[F] {
  override def applicative: Applicative[F] = Applicative[F]

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
