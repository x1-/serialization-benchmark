logLevel := Level.Warn

resolvers += "Twitter Repository" at "http://maven.twttr.com"

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.5")

addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.2.5")

//addSbtPlugin("com.twitter" % "scrooge-sbt-plugin" % "4.8.0")
