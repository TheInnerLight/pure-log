package org.novelfs.logging

object Logger {
  def log[F[_]](logLevel: LogLevel)(msg : String)(implicit monadLogger : MonadLogger[F]) : F[Unit] =
    monadLogger.log(logLevel)(msg)
}
