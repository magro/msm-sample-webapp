A simple (wicket) webapp for testing and benchmarking [memcached-session-manager](http://code.google.com/p/memcached-session-manager/) (msm).
It comes with two tomcats (in `runtime/`) that are configured with msm+kryo storing sessions in memcached.

# Prerequisites
1. [Buildr](http://buildr.apache.org)/Maven: you should have installed on of both so that you're able to build the webapp. For buildr see [Installing & Running](http://buildr.apache.org/installing.html).
2. memcached: you should have installed memcached so that you can run the webapp with sessions replicated to memcached
3. I don't mention java here :-)

# Building the webapp / war file
For building you have two options: buildr or maven2.

1. With buildr: After [installing buildr](http://buildr.apache.org/installing.html) just run
    $ buildr package test=no
2. With maven (you probably have it already installed) you can run
    $ mvn -Dmaven.test.skip=true package
   As you will probably get some error that you need to install some dependencies,
   you have to follow mvn's instructions. All required msm and kryo libs are located in
   `lib/`. You should also be able to run `./install-mvn-deps.sh` which saves you some typing.

# Running the webapp
You can run the webapp using the preconfigured tomcats in `runtime/`. Before you start tomcat, make sure that you have started two memcached nodes:
    $ memcached -p 11211 -u memcached -m 64 -M -vv &
    $ memcached -p 11212 -u memcached -m 64 -M -vv &
This is the cmd line that I'm using on my system with memcached installed using the memcached user. -vv tells memcached to write lots of stuff to stdout, so you'll see when a session is requested or stored in the output of memcached.

To start both tomcats just run
    $ ./runtime/tomcat1/bin/catalina.sh run &
    $ ./runtime/tomcat2/bin/catalina.sh run &

Now you can access both tomcats with your browser on `http://localhost:8081/` and `http://localhost:8082/`.
