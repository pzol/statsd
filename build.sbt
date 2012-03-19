name := "statsd"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.1" % "test"

libraryDependencies += "com.mongodb.casbah" % "casbah_2.9.0-1" % "2.1.5.0"

resolvers += "Novus Release Repository" at "http://repo.novus.com/releases/"

resolvers += "Novus Snapshots Repository" at "http://repo.novus.com/snapshots/"

libraryDependencies += "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT"
