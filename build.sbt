name := "teleport"

version := "0.1"

scalaVersion := "2.12.6"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  "manyangled" at "https://dl.bintray.com/manyangled/maven/"
)

libraryDependencies ++= Seq(
  "com.manyangled" %% "breakable" % "0.1.1",
  "com.chuusai" %% "shapeless" % "2.3.3",
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)