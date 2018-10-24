# pure-log

## Simple logging in IO

Works on any monad with a `LiftIO` instance.

```
import org.novelfs.pure.log.Logger
import org.novelfs.pure.log.simple._

Logger.log[IO](LogLevel.Info)("Hello World!").unsafeRunSync()
```

## MDC Logging

Works on any monad with a `LiftIO` instance and an `ApplicativeLocal` instance where the environment that you intend to read from has a `ToMdc` instance.

A `ToMdc` instance is provided for `Map[String, String]` meaning that this works out of the box:

```
import org.novelfs.pure.log.Logger
import org.novelfs.pure.log.mdc._

Logger.log[ReaderT[IO, Map[String, String], ?]](LogLevel.Info)("Hello World!")
    .run( Map("firstName" -> "James", "lastName" -> "Kirk") )
    .unsafeRunSync()

```
