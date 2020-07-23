lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-bastian",
    organization := "io.github.todokr",
    version := "2020.7.0-SNAPSHOT",
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-unchecked",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused"
    ),
    libraryDependencies ++= Seq(
      "net.sourceforge.plantuml" % "plantuml" % "8059",
      "com.github.bigwheel" %% "util-backports" % "2.1",
      "org.wvlet.airframe" %% "airframe-log" % "20.6.2",
      "org.scalatest" %% "scalatest" % "3.2.0" % Test,
      "org.scalacheck" %% "scalacheck" % "1.14.1" % Test
    )
  )

description := "A tool that generates a single usecase diagram from Product Backlog Items written in markdown format"
licenses := List("EPL 2.0" -> new URL("https://www.eclipse.org/legal/epl-2.0/"))
homepage := Some(url("https://github.com/todokr/bastian"))
developers := List(
  Developer(
    id = "todokr",
    name = "Shunsuke Tadokoro",
    email = "s.tadokoro0317@gmail.com",
    url = url("https://github.com/todokr")
  )
)
scmInfo := Some(
  ScmInfo(
    url("https://github.com/todokr/bastian"),
    "scm:git@github.com:todokr/bastian.git"
  )
)
pomIncludeRepository := { _ => false }
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
publishMavenStyle := true
