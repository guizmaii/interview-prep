ThisBuild / scalaVersion     := "2.13.13"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.guizmaii.interview.prep"
ThisBuild / organizationName := "guizmaii"

lazy val root = (project in file("."))
  .settings(
    name := "interview-prep",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.21",
      "dev.zio" %% "zio-test" % "2.0.21" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
