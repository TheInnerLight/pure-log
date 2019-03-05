package org.novelfs.pure.log

import cats.Applicative
import simulacrum.typeclass

@typeclass
trait ApplicativeLogger[F[_]] {
  def applicative : Applicative[F]
  def log(logLevel: LogLevel)(msg : String) : F[Unit]
  def error(e: Throwable)(msg : String) : F[Unit]
}
