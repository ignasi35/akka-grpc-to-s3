name := "akka-grpc-quickstart-java"
version := "1.0"
scalaVersion := "2.13.4"

val akkaVersion = "2.6.13"
val akkaHttpVersion = "10.2.4"
lazy val akkaGrpcVersion = "1.1.1"

enablePlugins(AkkaGrpcPlugin)

akkaGrpcGeneratedLanguages := Seq(AkkaGrpc.Java)
akkaGrpcCodeGeneratorSettings += "server_power_apis"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http2-support" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-discovery" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-pki" % akkaVersion,

  "ch.qos.logback" % "logback-classic" % "1.2.3",

  "com.lightbend.akka" %% "akka-stream-alpakka-s3" % "2.0.2",
  "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion,

  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "junit" % "junit" % "4.13" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

