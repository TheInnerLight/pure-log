package org.novelfs.pure.log

import cats.Monad

trait MonadLogger[F[_]] {
  val monad : Monad[F]
  def log(logLevel: LogLevel)(msg : String) : F[Unit]
}
