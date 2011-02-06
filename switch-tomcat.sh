#!/bin/sh

if [ $# -ne 1 ]; then
        echo 1>&2 "Usage: $0 <tomcat-version>"
        echo "   e.g.: $0 7.0.6"
        exit 127
fi

VERSION=$1

cd runtime

if [ -e "tomcat" ]
then
    rm tomcat
fi

ln -s apache-tomcat-$VERSION/ tomcat
cd -
