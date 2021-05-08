name := "Bitcoin"

version := "0.1"

scalaVersion := Dependencies.Versions.scalaVersion

def commonSettings(projectName: String) = Seq(
  name := projectName,
  scalafmtOnCompile := true,
  scalafmtSbtCheck := true,
  scalacOptions ++= Seq("-Ypartial-unification"),
  libraryDependencies ++= Seq(
    Dependencies.Libraries.logback,
    Dependencies.Libraries.scalaLogging
  )
)

lazy val root = project.in(file("."))
  .aggregate(
    crypto
  )

lazy val crypto = project.in(file("crypto"))
  .settings(
    commonSettings("crypto"),
    libraryDependencies ++= Seq(
      Dependencies.Libraries.catsEffect,
      Dependencies.Libraries.catsEffectTest,
      Dependencies.Libraries.pureConfig,
      Dependencies.Libraries.specs2Core,
      Dependencies.Libraries.specs2ScaleCheck
    )
  )