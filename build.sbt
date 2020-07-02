name := """play-java-hello-world-tutorial"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

libraryDependencies += guice

libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.2.0"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.5"

libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.12"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "test"