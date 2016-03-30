import sbt._
import Keys._

object ScalaNN extends Build {

  lazy val scalaNN: Project = Project("scala-nn", file("."),
    settings = Config.buildSettings ++ Seq(libraryDependencies ++=
      Seq(
        "org.scalanlp" %% "breeze" % "0.8.1",
        "org.scalanlp" %% "breeze-natives" % "0.10",
        "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
      )
    ))
}

object Config {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    scalaVersion := "2.11.4"
  )
}