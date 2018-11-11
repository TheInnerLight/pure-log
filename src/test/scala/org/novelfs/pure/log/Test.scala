package org.novelfs.pure.log

import cats.data.ReaderT
import cats.mtl.implicits._
import cats.effect.{IO, LiftIO}
import org.scalatest.{FlatSpec, Matchers}

class Test extends FlatSpec with Matchers {

  // Sanity check simple use cases resolve implicits correctly

  case class Captain(firstName : String, lastName : String)

  "simple logging" should "successfully compile for IO" in {
    import simple._

    Logger.log[IO](LogLevel.Info)("Hello World!").unsafeRunSync()

  }

  "mdc logging" should "successfully compile when mdc._ is imported and a simple Map[String, String] context is used" in {
    import mdc._

    implicit val stringMapToMdc = new ToMdc[Captain] {
      override def toMdc(captain: Captain): Map[String, String] = Map("firstName" -> captain.firstName, "lastName" -> captain.lastName)
    }

    Logger.log[ReaderT[IO, Captain, ?]](LogLevel.Info)("Hello World!")
      .run(Captain("James", "Kirk"))
      .unsafeRunSync()
  }
}
