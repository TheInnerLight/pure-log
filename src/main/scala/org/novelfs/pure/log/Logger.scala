package org.novelfs.pure.log

object Logger {
  def log[F[_]](logLevel: LogLevel)(msg : String)(implicit applicativeLogger : ApplicativeLogger[F]) : F[Unit] =
    applicativeLogger.log(logLevel)(msg)

  def logThrowable[F[_]](logLevel: LogLevel)(msg : String)(e: Throwable)(implicit applicativeLogger : ApplicativeLogger[F]): F[Unit] =
    applicativeLogger.logThrowable(logLevel)(e)(msg)
}
