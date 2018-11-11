# pure-log


[![Build Status](https://travis-ci.org/TheInnerLight/streaming-kafka.svg?branch=master)](https://travis-ci.org/TheInnerLight/pure-log)
[![Latest version](https://index.scala-lang.org/theinnerlight/pure-log/pure-log/latest.svg?color=orange)](https://index.scala-lang.org/theinnerlight/pure-log/pure-log)
## Simple logging in IO

```
libraryDependencies += "org.novelfs" %% "pure-log" % "[Latest version]"
```

Works in any `Applicative` with a `LiftIO` instance.

```
import org.novelfs.pure.log.Logger
import org.novelfs.pure.log.simple._
import cats.effect.IO

Logger.log[IO](LogLevel.Info)("Hello World!").unsafeRunSync()
```

## MDC Logging

Works in any `Monad` with a `LiftIO` instance and an `ApplicativeLocal` instance where the environment that you intend to read from has a `ToMdc` instance.

```
import org.novelfs.pure.log.Logger
import org.novelfs.pure.log.mdc._
import cats.data.ReaderT
import cats.mtl.implicits._

case class Captain(firstName : String, lastName : String)

implicit val captainToMdc = new ToMdc[Captain] {
  override def toMdc(captain: Captain): Map[String, String] = Map("firstName" -> captain.firstName, "lastName" -> captain.lastName)
}

Logger.log[ReaderT[IO, Captain, ?]](LogLevel.Info)("Hello World!")
  .run(Captain("James", "Kirk"))
  .unsafeRunSync()
```
