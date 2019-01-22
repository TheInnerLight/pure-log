package org.novelfs.pure.log

import cats.Applicative
import simulacrum.typeclass

@typeclass
trait ApplicativeLogger[F[_]] {
  def applicative : Applicative[F]
  def log(logLevel: LogLevel)(msg : String) : F[Unit]
  
  @inline def trace(msg : String) : F[Unit] = log(LogLevel.Trace)(msg)
  @inline def debug(msg : String) : F[Unit] = log(LogLevel.Debug)(msg)
  @inline def info(msg : String) : F[Unit] = log(LogLevel.Info)(msg)
  @inline def warn(msg : String) : F[Unit] = log(LogLevel.Warn)(msg)
  @inline def error(msg : String) : F[Unit] = log(LogLevel.Error)(msg)
}
