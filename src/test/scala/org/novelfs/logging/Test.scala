package org.novelfs.logging

import cats.data.ReaderT
import cats.mtl.implicits._
import cats.effect.{IO, LiftIO}
import org.scalatest.{FlatSpec, Matchers}

class Test extends FlatSpec with Matchers {

  "simple logging" should "successfully compile for IO" in {
    import simple._

    Logger.log[IO](LogLevel.Info)("Hello World!").unsafeRunSync()
  }

  "mdc logging" should "successfully compile when mdc._ is imported and a simple Map[String, String] context is used" in {

    import mdc._

    Logger.log[ReaderT[IO, Map[String, String], ?]](LogLevel.Info)("Hello World!")
      .run( Map("firstName" -> "James", "lastName" -> "Kirk") )
      .unsafeRunSync()

  }

}
