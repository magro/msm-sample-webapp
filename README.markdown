A sample (wicket) webapp for playing with [memcached-session-manager](http://code.google.com/p/memcached-session-manager/) (msm).
It comes with two tomcats (in `runtime/`) that are configured with msm+kryo (msm kryo-serializer) storing sessions in memcached.

# Prerequisites
1. [Buildr](http://buildr.apache.org)/[Maven](http://maven.apache.org): you should have installed one of both so that you're able to build the webapp. For buildr see [Installing & Running](http://buildr.apache.org/installing.html). Maven - ok, probably you have [installed](http://maven.apache.org/download.html) it already...
2. [memcached](http://memcached.org): you should have installed memcached so that you can run the webapp with sessions replicated to memcached
3. I don't mention java here :-)

# Building the webapp / war file
For building you have two options: buildr or maven2.

1. With buildr: After [installing buildr](http://buildr.apache.org/installing.html) just run
    `$ buildr package test=no`
2. With maven (you probably have it already installed) you first should install dependencies via `$ ./install-mvn-deps.sh` (this installs jars from `lib/`), then you can run
    `$ mvn -Dmaven.test.skip=true package`
   to build the web application.

# Running the webapp
You can run the webapp using the preconfigured tomcats in `runtime/`. Before you start tomcat, make sure that you have started two memcached nodes:
    $ memcached -p 11211 -u memcached -m 64 -M -vv &
    $ memcached -p 11212 -u memcached -m 64 -M -vv &
This is the cmd line that I'm using on my system with memcached installed using the memcached user. -vv tells memcached to write lots of stuff to stdout, so you'll see when a session is requested or stored in the output of memcached.

To start both tomcats just run
    $ ./runtime/tomcat1/bin/catalina.sh run &
    $ ./runtime/tomcat2/bin/catalina.sh run &

Now you can access both tomcats with your browser on [http://localhost:8081/](http://localhost:8081/) and [http://localhost:8082/](http://localhost:8082/). To simulate a loadbalancer in front of your tomcats and a session failover just request the same url on the other tomcat (just change to port in the url).
