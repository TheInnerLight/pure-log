package org.novelfs.logging.mdc

trait ToMdc[T] {
  def toMdc(item :T) : Map[String, String]
}
