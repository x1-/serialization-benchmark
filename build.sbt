name := "com.inkenkun.x1.serialization.benchmark"

version := "1.0"

scalaVersion := "2.12.2"

scalacOptions ++= Seq( "-deprecation", "-encoding", "UTF-8", "-target:jvm-1.8" )

libraryDependencies ++= Seq(
  "org.apache.thrift"  % "libthrift"      % "0.10.0",
  "org.apache.avro"    % "avro"           % "1.8.2",
  "org.apache.avro"    % "avro-ipc"       % "1.8.2",
  "org.scalatest"     %% "scalatest"      % "3.0.0"  % "test"
)

enablePlugins(JmhPlugin)
