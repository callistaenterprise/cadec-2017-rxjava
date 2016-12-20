name := "book-store"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
	javaJdbc,
	cache,
	javaWs,
	"io.reactivex.rxjava2" % "rxjava" % "2.0.2",
	"com.adrianhurt" %% "play-bootstrap" % "1.1-P25-B4",
	"com.chuusai" %% "shapeless" % "2.3.2"
)