#!/bin/sh
# A convenience script to install deps from lib dir, as specified in pom.xml
mvn install:install-file -DgroupId=de.javakaffee.web.msm -DartifactId=msm-kryo-serializer -Dversion=1.4.2-SNAPSHOT -Dpackaging=jar -Dfile=lib/msm-kryo-serializer-1.4.2-SNAPSHOT.jar
