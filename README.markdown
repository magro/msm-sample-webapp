A sample webapp for testing [memcached-session-manager](http://code.google.com/p/memcached-session-manager/) (msm) with [OpenWebBeans](http://openwebbeans.apache.org), specifically the combination of `@SessionScoped` with session failover. This does not work as expected, see the last paragraph below for details.
This sample comes with two tomcat instances (in `runtime/`, tomcat1 and tomcat2) that are configured with plain java serialization for non-sticky sessions by default.
To change the stickyness you can switch via `./switch-stickyness.sh sticky|nonsticky`.
Btw, there are 2 different tomcat versions available in `runtime/` (6.0.32 and 7.0.8), you can switch them via `./switch-tomcat.sh 7.0.8` or `./switch-tomcat.sh 6.0.32`.

# Prerequisites
1. [Maven](http://maven.apache.org): you should have installed maven to be able to build the webapp.
2. [memcached](http://memcached.org): you should have installed memcached so that you can run the webapp with sessions replicated to memcached
3. I don't mention java here :-)

# Building the webapp / war file
1. Build the web application:
    `$ mvn -Dmaven.test.skip=true package`

# Running the webapp
You can run the webapp using the preconfigured tomcats in `runtime/`. Before you start tomcat, make sure that you have started two memcached nodes:
    $ memcached -p 11211 -u memcached -m 64 -M -vv &
    $ memcached -p 11212 -u memcached -m 64 -M -vv &
This is the cmd line that I'm using on my system with memcached installed using the memcached user. -vv tells memcached to write lots of stuff to stdout, so you'll see when a session is requested or stored in the output of memcached.

To start both tomcats just run
    $ ./runtime/tomcat1/bin/catalina.sh run &
    $ ./runtime/tomcat2/bin/catalina.sh run &

Now you can access both tomcats with your browser on [http://localhost:8081/test.xhtml](http://localhost:8081/test.xhtml) and [http://localhost:8082/test.xhtml](http://localhost:8082/msm-sample-openwebbeans/test.xhtml).

# Reproducing the issue with @SessionScoped beans
The `@SessionScoped` bean (represented by the first input field) seems to be not loaded correctly from the session.

When playing with a single tomcat and non-sticky sessions (non-sticky sessions here means: the session is removed from tomcats session map at the end of the request, stored in memcached, and loaded from memcached into tomcats session map with the next request) the value stored in the session bean is kept as expected. When tomcat is restarted and `/test.xhtml` is requested again, the value is (unexpectadly) lost.

The issue can be seen in a different scenario: when playing with 2 tomcats, and one enters "foo" for the session scoped value in tomcat1 ([http://localhost:8081/test.xhtml](http://localhost:8081/test.xhtml)), this value is (as already said above) correctly displayed when requesting this url again. When the page is requested from tomcat2 ([http://localhost:8082/test.xhtml](http://localhost:8082/test.xhtml)) the value is not shown / empty. For tomcat1 the old value is still displayed. When setting the value to "bar" for tomcat2, this is shown for tomcat2 when requesting the page from tomcat2 again. For tomcat1 the value is still "foo". This is not expected, instead, both tomcat1 and tomcat2 should show the same value.
