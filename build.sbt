Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion               := "2.13.13"
ThisBuild / version                    := "0.1.0-SNAPSHOT"
ThisBuild / organization               := "com.guizmaii.interview.prep"
ThisBuild / organizationName           := "guizmaii"
ThisBuild / scalafmtCheck              := true
ThisBuild / scalafmtSbtCheck           := true
ThisBuild / scalafmtOnCompile          := (if (insideCI.value) false else true)
ThisBuild / semanticdbEnabled          := true
ThisBuild / semanticdbOptions += "-P:semanticdb:synthetics:on"
ThisBuild / semanticdbVersion          := scalafixSemanticdb.revision // use Scalafix compatible version
ThisBuild / scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value)
ThisBuild / scalafixDependencies ++= List(
  "com.github.vovapolu"                      %% "scaluzzi" % "0.1.23",
  "io.github.ghostbuster91.scalafix-unified" %% "unified"  % "0.0.9",
)

addCommandAlias("tc", "Test/compile")
addCommandAlias("ctc", "clean; Test/compile")
addCommandAlias("rctc", "reload; clean; Test/compile")

lazy val root =
  (project in file("."))
    .settings(
      name := "interview-prep"
    )
