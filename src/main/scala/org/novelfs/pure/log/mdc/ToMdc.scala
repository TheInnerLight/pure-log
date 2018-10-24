package org.novelfs.pure.log.mdc

trait ToMdc[T] {
  def toMdc(item :T) : Map[String, String]
}
