name := "calgeneratorchoco"
 
version := "1.0" 
      
lazy val `calgeneratorchoco` = (project in file(".")).enablePlugins(PlayJava)


resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  javaJdbc ,
  cache ,
  javaWs,
  "org.choco-solver" % "choco-solver" % "4.0.8" withSources() withJavadoc())

libraryDependencies += guice

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/ChocoTest" )

