ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "authPrac"
  )

val AkkaVersion = "2.8.2"
libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % "0.21.0",
  "org.http4s" %% "http4s-circe" % "0.21.0",
  "org.http4s" %% "http4s-dsl" % "0.21.0",
  "org.http4s" %% "http4s-ember-server" % "0.21.0",
  "io.circe" %% "circe-generic" % "0.13.0",
  "org.typelevel" %% "cats-core" % "2.1.1",
  "com.typesafe.akka" %% "akka-http" % "10.5.0",
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.0",
  "com.auth0" % "java-jwt" % "3.18.1",
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "javax.mail" % "mail" % "1.4",
"com.auth0" % "java-jwt" % "3.18.1",
  "com.sun.mail" % "javax.mail" % "1.6.2"
)
