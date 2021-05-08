import sbt._
import Dependencies.Versions._

object Dependencies {
  object Versions {
    val scalaVersion = "2.13.5"
    val catsEffectVersion = "3.1.0"
    val catsEffectTestVersion = "1.0.0"
    val pureConfigVersion = "0.12.3"
    val specs2Version = "4.10.6"
    val logbackVersion = "1.2.3"
    val scalaLoggingVersion = "3.9.3"
  }

  object Libraries {
    val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectVersion
    val catsEffectTest = "org.typelevel" %% "cats-effect-testing-specs2" % catsEffectTestVersion % Test
    val pureConfig = "com.github.pureconfig" %% "pureconfig" % pureConfigVersion
    val specs2Core = "org.specs2" %% "specs2-core" % specs2Version % "test"
    val specs2ScaleCheck = "org.specs2" %% "specs2-scalacheck" % specs2Version % "test"
    val logback = "ch.qos.logback" % "logback-classic" % logbackVersion
    val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
  }
}