#!/bin/sh
# A convenience script to install deps from lib dir, as specified in pom.xml
mvn install:install-file -DgroupId=de.javakaffee -DartifactId=kryo-serializers -Dversion=0.6 -Dpackaging=jar -Dfile=lib/kryo-serializers-0.6.jar
mvn install:install-file -DgroupId=de.javakaffee.web.msm -DartifactId=msm-kryo-serializer -Dversion=1.3.3 -Dpackaging=jar -Dfile=lib/msm-kryo-serializer-1.3.3.jar
mvn install:install-file -DgroupId=com.esotericsoftware -DartifactId=kryo -Dversion=1.02 -Dpackaging=jar -Dfile=lib/kryo-1.02.jar
mvn install:install-file -DgroupId=com.esotericsoftware -DartifactId=reflectasm -Dversion=0.8 -Dpackaging=jar -Dfile=lib/reflectasm-0.8.jar
mvn install:install-file -DgroupId=com.esotericsoftware -DartifactId=minlog -Dversion=1.2 -Dpackaging=jar -Dfile=lib/minlog-1.2.jar
