name := "calgeneratorchoco"
 
version := "1.0" 
      
lazy val `calgeneratorchoco` = (project in file(".")).enablePlugins(PlayJava)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  javaJdbc ,
  cache ,
  javaWs,
  "org.choco-solver" % "choco-solver" % "4.0.6" withSources() withJavadoc())

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/ChocoTest" )

      