
name := "MetricsCollector"

version := "0.1"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "All Spark Repository -> bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven/"
)

libraryDependencies ++= Seq(

  "io.prometheus" % "simpleclient" % "0.1.0",
  "io.prometheus" % "simpleclient_common" % "0.1.0",
  "io.prometheus" % "simpleclient_hotspot" % "0.1.0",
  "io.prometheus" % "simpleclient_pushgateway" % "0.1.0",
  "com.typesafe" % "config" % "1.3.2",
  "org.apache.spark" % "spark-core_2.11" % "2.2.0",
  "org.apache.spark" % "spark-sql_2.11" % "2.2.0",
)

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.2" % "test"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

test in assembly := {}
        