name := "Bitcoin"

version := "0.1"

scalaVersion := Dependencies.Versions.scalaVersion

def commonSettings(projectName: String) = Seq(
  name := projectName,
  scalaVersion := Dependencies.Versions.scalaVersion,
  scalafmtOnCompile := true,
  scalafmtSbtCheck := true,
  libraryDependencies ++= Seq(
    Dependencies.Libraries.logback,
    Dependencies.Libraries.scalaLogging
  )
)

lazy val root = project
  .in(file("."))
  .aggregate(
    crypto,
    blockchain
  )

lazy val crypto = project
  .in(file("crypto"))
  .settings(
    commonSettings("crypto"),
    libraryDependencies ++= Seq(
      Dependencies.Libraries.catsEffect,
      Dependencies.Libraries.catsEffectTest,
      Dependencies.Libraries.bouncyCastle,
      Dependencies.Libraries.specs2Core,
      Dependencies.Libraries.specs2ScaleCheck,
      Dependencies.Libraries.commonsCodec
    )
  )

lazy val blockchain = project
  .in(file("blockchain"))
  .settings(
    commonSettings("blockchain"),
    libraryDependencies ++= Seq(
      Dependencies.Libraries.catsEffect,
      Dependencies.Libraries.catsEffectTest,
      Dependencies.Libraries.bouncyCastle,
      Dependencies.Libraries.specs2Core,
      Dependencies.Libraries.specs2ScaleCheck,
      Dependencies.Libraries.commonsCodec,
      Dependencies.Libraries.http4sCore,
      Dependencies.Libraries.http4sClient
    )
  )
  .dependsOn(crypto, concurrentAlgebra)

lazy val concurrentAlgebra = project
  .in(file("concurrent_algebra"))
  .settings(
    commonSettings("concurrent_algebra"),
    libraryDependencies ++= Seq(
      Dependencies.Libraries.catsEffect,
      Dependencies.Libraries.catsEffectTest,
      Dependencies.Libraries.specs2Core,
      Dependencies.Libraries.specs2ScaleCheck
    )
  )
