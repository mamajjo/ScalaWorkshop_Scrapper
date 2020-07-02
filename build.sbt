lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """play-scraper""",
    organization := "com.MaciejMajchrowski",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
      "net.ruippeixotog" %% "scala-scraper" % "2.2.0",
      "io.spray" %%  "spray-json" % "1.3.5",
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.12",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
