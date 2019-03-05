package org.novelfs.pure.log

import org.log4s.{Logger => Log4sLogger}

private [log] object SideEffectingLogger {
  def logWithLogger(logger : Log4sLogger)(logLevel: LogLevel)(msg : String): Unit = {
    logLevel match {
      case LogLevel.Trace => logger.trace(msg)
      case LogLevel.Debug => logger.debug(msg)
      case LogLevel.Info  => logger.info(msg)
      case LogLevel.Warn  => logger.warn(msg)
      case LogLevel.Error => logger.error(msg)
    }
  }

  def logThrowableWithLogger(logger : Log4sLogger)(logLevel: LogLevel)(e: Throwable)(msg : String): Unit = {
    logLevel match {
      case LogLevel.Trace => logger.trace(e)(msg)
      case LogLevel.Debug => logger.debug(e)(msg)
      case LogLevel.Info  => logger.info(e)(msg)
      case LogLevel.Warn  => logger.warn(e)(msg)
      case LogLevel.Error => logger.error(e)(msg)
    }
  }
}
