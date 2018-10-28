package org.novelfs.pure.log

import cats.data.ReaderT
import cats.mtl.implicits._
import cats.effect.{IO, LiftIO}
import org.scalatest.{FlatSpec, Matchers}

class Test extends FlatSpec with Matchers {

  case class Captain(firstName : String, lastName : String)

  "simple logging" should "successfully compile for IO" in {
    import simple._

    Logger.log[IO](LogLevel.Info)("Hello World!").unsafeRunSync()

  }

  "mdc logging" should "successfully compile when mdc._ is imported and a simple Map[String, String] context is used" in {
    import mdc._

    implicit val stringMapToMdc = new ToMdc[Map[String, String]] {
      override def toMdc(item: Map[String, String]): Map[String, String] = item
    }

    Logger.log[ReaderT[IO, Map[String, String], ?]](LogLevel.Info)("Hello World!")
      .run( Map("firstName" -> "James", "lastName" -> "Kirk") )
      .unsafeRunSync()
  }
}
