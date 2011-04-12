This is the slightly modified guessNumber sample from the [http://javaserverfaces.java.net/download.html](mojarra 2.0.4 binary release), to show how the JSF2 reference implementation plays with [http://code.google.com/p/memcached-session-manager](memcachd-session-manager).

The text from the original `README.txt`:
    This demo shows how to use resources to create a client side validator tag, and use it within a page.

The following changes were made to the sources of the mojarra 2.0.4 guessNumber sample:
1. The `pom.xml` is patched to prevent conflicts with tomcats own el-api.jar (so el-api is excluded from artifacts in the pom.xml).
2. The dependencies in `pom.xml` are extended by kryo libs, to see how kryo serialization works with mojarra (the required repository is added for this to work).
3. In `src/main/webapp/WEB-INF/web.xml` the value of `javax.faces.STATE_SAVING_METHOD` is changed from `client` to `server`.

The rest of the sample (runtime) works as in all other branches. It comes with two tomcat instances (in `runtime/`, tomcat1 and tomcat2) that are configured with plain java serialization for sticky sessions by default.
To change the stickyness you can switch via `./switch-stickyness.sh sticky|nonsticky`.
There are 2 different tomcat versions available in `runtime/` (6.0.32 and 7.0.8), you can switch them via `./switch-tomcat.sh 7.0.8` or `./switch-tomcat.sh 6.0.32` (though, it does not seem to work with tomcat7).

# Prerequisites
1. [Maven](http://maven.apache.org): you should have installed maven to be able to build the webapp.
2. [memcached](http://memcached.org): you should have installed memcached so that you can run the webapp with sessions replicated to memcached
3. I don't mention java here :-)

# Building the webapp / war file
1. Build the web application via maven:
    `$ mvn clean package`

# Running the webapp
You can run the webapp using the preconfigured tomcats in `runtime/`. Before you start tomcat, make sure that you have started two memcached nodes:
    $ memcached -p 11211 -u memcached -m 64 -M -vv &
    $ memcached -p 11212 -u memcached -m 64 -M -vv &
This is the cmd line that I'm using on my system with memcached installed using the memcached user. -vv tells memcached to write lots of stuff to stdout, so you'll see when a session is requested or stored in the output of memcached.

To start both tomcats just run
    $ ./runtime/tomcat1/bin/catalina.sh run &
    $ ./runtime/tomcat2/bin/catalina.sh run &

Now you can access both tomcats with your browser on [http://localhost:8081/](http://localhost:8081/) and [http://localhost:8082/](http://localhost:8082/), which should show you the number guess greeting page (s.o. named "Duke" will say hello). Then you can guess numbers...

To see that the session can be taken over by the second tomcat just request [http://localhost:8082/](http://localhost:8082/), you have simulated a tomcat failover / session failover.
With non-sticky sessions activated each request will test the session replication, as the session is loaded from memcached for each request. So if you can guess numbers with non-sticky sessions then session replication is fine.
