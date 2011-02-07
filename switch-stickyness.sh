#!/bin/sh

if [ $# -ne 1 ]; then
        echo 1>&2 "Usage: $0 sticky|nonsticky"
        echo "   e.g.: $0 nonsticky"
        exit 127
fi

STICKYNESS=$1

cd runtime

if [ "$STICKYNESS" == "sticky" ]
then
    sed -i "s|sticky=\"false\"|sticky=\"true\"|g" tomcat1/conf/context.xml 
    sed -i "s|failoverNodes=\"\"|failoverNodes=\"n1\"|g" tomcat1/conf/context.xml 

    sed -i "s|sticky=\"false\"|sticky=\"true\"|g" tomcat2/conf/context.xml 
    sed -i "s|failoverNodes=\"\"|failoverNodes=\"n2\"|g" tomcat2/conf/context.xml 
else
    sed -i "s|sticky=\"true\"|sticky=\"false\"|g" tomcat1/conf/context.xml 
    sed -i "s|failoverNodes=\"n1\"|failoverNodes=\"\"|g" tomcat1/conf/context.xml 

    sed -i "s|sticky=\"true\"|sticky=\"false\"|g" tomcat2/conf/context.xml 
    sed -i "s|failoverNodes=\"n2\"|failoverNodes=\"\"|g" tomcat2/conf/context.xml 
fi

cd ..
