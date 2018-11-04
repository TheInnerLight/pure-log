package org.novelfs.pure.log

import cats.Monad
import simulacrum.typeclass

@typeclass
trait MonadLogger[F[_]] {
  def monad : Monad[F]
  def log(logLevel: LogLevel)(msg : String) : F[Unit]
}
