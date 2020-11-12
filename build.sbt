name := "kafka-protobuf-events"

version := "0.1"

scalaVersion := "2.13.3"

lazy val root = project
  .in(file("."))
  .settings(
    crossScalaVersions := Seq.empty,
    name := "kafka-protobuf-events-root"
  )
  .aggregate(
    `consumer`,
    `producer`
  )

lazy val consumer = project
  .in(file("consumer"))
  .settings(
    name := "consumer",
    libraryDependencies ++= Seq.empty
  )

lazy val producer = project
  .in(file("producer"))
  .settings(
    name := "producer",
    libraryDependencies ++= Seq.empty
  )