package org.novelfs.pure.log

sealed trait LogLevel

object LogLevel {
  final case object Trace extends LogLevel
  final case object Debug extends LogLevel
  final case object Info extends LogLevel
  final case object Warn extends LogLevel
  final case object Error extends LogLevel
}
