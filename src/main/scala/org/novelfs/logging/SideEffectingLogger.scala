package org.novelfs.logging

import org.log4s.{Logger => Log4sLogger}

private [this] object SideEffectingLogger {
  def logWithLogger(logger : Log4sLogger)(logLevel: LogLevel)(msg : String): Unit = {
    logLevel match {
      case LogLevel.Trace => logger.trace(msg)
      case LogLevel.Debug => logger.debug(msg)
      case LogLevel.Info  => logger.info(msg)
      case LogLevel.Warn  => logger.warn(msg)
      case LogLevel.Error => logger.error(msg)
    }
  }
}
