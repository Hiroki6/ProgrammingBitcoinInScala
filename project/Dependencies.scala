import sbt._
import Dependencies.Versions._

object Dependencies {
  object Versions {
    val scalaVersion = "2.13.5"
    val catsEffectVersion = "3.1.0"
    val catsEffectTestVersion = "1.0.0"
    val pureConfigVersion = "0.12.3"
    val bouncyCastleV = "1.68"
    val specs2Version = "4.10.6"
    val logbackVersion = "1.2.3"
    val scalaLoggingVersion = "3.9.3"
    val commonsCodecVersion = "1.15"
    val http4sVersion = "1.0.0-M21"
  }

  object Libraries {
    val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectVersion
    val catsEffectTest = "org.typelevel" %% "cats-effect-testing-specs2" % catsEffectTestVersion % Test
    val pureConfig = "com.github.pureconfig" %% "pureconfig" % pureConfigVersion
    val bouncyCastle = "org.bouncycastle" % "bcprov-jdk15on" % bouncyCastleV
    val specs2Core = "org.specs2" %% "specs2-core" % specs2Version % "test"
    val specs2ScaleCheck = "org.specs2" %% "specs2-scalacheck" % specs2Version % "test"
    val logback = "ch.qos.logback" % "logback-classic" % logbackVersion
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
    val commonsCodec = "commons-codec" % "commons-codec" % commonsCodecVersion
    val http4sCore = "org.http4s" %% "http4s-core" % http4sVersion
    val http4sClient = "org.http4s" %% "http4s-blaze-client" % http4sVersion
  }
}