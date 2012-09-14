A sample (wicket) webapp for playing with [memcached-session-manager](http://code.google.com/p/memcached-session-manager/) (msm).
It comes with two tomcats (in `runtime/`) that are configured with msm+kryo (msm kryo-serializer) storing sessions in memcached.

This sample comes with two tomcat instances (in `runtime/`, tomcat1 and tomcat2) that are configured with msm+kryo (msm kryo-serializer) for non-sticky sessions by default.

To change the stickyness you can switch via `./switch-stickyness.sh sticky|nonsticky`.
Btw, there are 2 different tomcat versions available in `runtime/` (6.0.32 and 7.0.8), you can switch them via `./switch-tomcat.sh 7.0.8` or `./switch-tomcat.sh 6.0.32`.

# Prerequisites
1. [Maven](http://maven.apache.org): you should have installed maven to be able to build the webapp.
2. [memcached](http://memcached.org): you should have installed memcached so that you can run the webapp with sessions replicated to memcached
3. I don't mention java here :-)

# Building the webapp / war file
1. Build the web application:
    `$ mvn package`

# Running the webapp
You can run the webapp using the preconfigured tomcats in `runtime/`. Before you start tomcat, make sure that you have started two memcached nodes:
    $ memcached -p 11211 -u memcached -m 64 -M -vv &
    $ memcached -p 11212 -u memcached -m 64 -M -vv &
This is the cmd line that I'm using on my system with memcached installed using the memcached user. -vv tells memcached to write lots of stuff to stdout, so you'll see when a session is requested or stored in the output of memcached.

To start both tomcats just run
    $ ./runtime/tomcat1/bin/catalina.sh run &
    $ ./runtime/tomcat2/bin/catalina.sh run &

Now you can access both tomcats with your browser on [http://localhost:8081/](http://localhost:8081/) and [http://localhost:8082/](http://localhost:8082/). To simulate a loadbalancer in front of your tomcats and a session failover just request the same url on the other tomcat (just change to port in the url).
