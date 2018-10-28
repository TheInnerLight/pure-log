package org.novelfs.pure.log

import cats.Monad
import cats.effect.LiftIO
import cats.mtl.ApplicativeAsk

package object mdc {
  implicit def mdcLogger[F[_] : LiftIO : Monad, TContext](implicit toMdc : ToMdc[TContext], applicativeAsk : ApplicativeAsk[F, TContext]) : MonadLogger[F] =
    new MdcLogger[F, TContext]()
}
