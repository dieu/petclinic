resolvers += "scala-tools" at "http://scala-tools.org/repo-releases/"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.1.0-TYPESAFE")

addSbtPlugin("play" % "sbt-plugin" % "2.1-0627-sbt12")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.0")

dependencyOverrides += "commons-codec" % "commons-codec" % "1.6"

libraryDependencies += "commons-codec" % "commons-codec" % "1.6"

libraryDependencies ++= Seq(
  "org.hibernate" % "hibernate-entitymanager" % "3.5.4-Final" exclude("commons-logging", "commons-logging") exclude("org.slf4j","slf4j-api") exclude("commons-collections","commons-collections") exclude("cglib","cglib") exclude("xml-apis","xml-apis"),
  "net.thucydides" % "thucydides-core" % "0.9.98")