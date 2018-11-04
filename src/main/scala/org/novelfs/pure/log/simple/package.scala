package org.novelfs.pure.log

import cats.Monad
import cats.effect.LiftIO
import org.log4s.getLogger

package object simple {
  implicit def simpleLogger[F[_] : LiftIO : Monad] : MonadLogger[F]  = new SimpleLogger[F](getLogger("pure-log-simple-logger"))
}
