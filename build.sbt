ThisBuild / scalaVersion := "2.13.18"

lazy val root = (project in file("."))
  .aggregate(exercises, answers)
  .settings(
    name := "fpinscala"
  )

lazy val exercises = (project in file("exercises"))
  .settings(
    name := "exercises",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % Test
  )

lazy val answers = (project in file("answers"))
  .settings(
    name := "answers"
  )
