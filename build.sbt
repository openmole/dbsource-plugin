name := "dbsource-plugin"

version := "0.1"

scalaVersion := "3.2.0"

enablePlugins(SbtOsgi)

OsgiKeys.exportPackage := Seq("org.openmole.plugin.source.dbsource.*")
OsgiKeys.importPackage := Seq("*;resolution:=optional")
OsgiKeys.privatePackage := Seq("!scala.*")
OsgiKeys.requireCapability := """osgi.ee;filter:="(&(osgi.ee=JavaSE)(version=1.8))""""

val openmoleVersion = "15.0-SNAPSHOT"

libraryDependencies += "org.openmole" %% "org-openmole-core-dsl" % openmoleVersion
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.30"
