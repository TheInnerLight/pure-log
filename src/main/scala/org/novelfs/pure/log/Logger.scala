package org.novelfs.pure.log

object Logger {
  def log[F[_]](logLevel: LogLevel)(msg : String)(implicit applicativeLogger : ApplicativeLogger[F]) : F[Unit] =
    applicativeLogger.log(logLevel)(msg)
}
