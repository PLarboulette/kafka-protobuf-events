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
  .enablePlugins(ProtobufPlugin)
  .settings(
    name := "consumer",
    libraryDependencies ++= Seq(
      "org.apache.kafka" % "kafka-clients" % "2.6.0",
      "io.confluent" % "kafka-protobuf-serializer" % "5.5.1"
    ),
    resolvers ++= Seq(
      Resolver.jcenterRepo,
      "confluent" at "http://packages.confluent.io/maven/"
    )
  )

lazy val producer = project
  .in(file("producer"))
  .enablePlugins(ProtobufPlugin)
  .settings(
    name := "producer",
    libraryDependencies ++= Seq(
      "org.apache.kafka" % "kafka-clients" % "2.6.0",
      "io.confluent" % "kafka-protobuf-serializer" % "5.5.1"
    ),
    resolvers ++= Seq(
      Resolver.jcenterRepo,
      "confluent" at "http://packages.confluent.io/maven/"
    )
  )