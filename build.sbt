name := """stock-quoter-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.yahoofinance-api" % "YahooFinanceAPI" % "2.0.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
