package org.novelfs.pure.log

import cats.Applicative
import simulacrum.typeclass

@typeclass
trait ApplicativeLogger[F[_]] {
  def applicative : Applicative[F]
  def log(logLevel: LogLevel)(msg : String) : F[Unit]
  def logThrowable(logLevel: LogLevel)(e: Throwable)(msg : String) : F[Unit]
}
