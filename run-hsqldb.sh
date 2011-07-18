#!/bin/sh
java -classpath lib/hsqldb-2.2.5.jar org.hsqldb.server.Server --database.0 file:runtime/hsqldb/hsql-userdb --dbname.0 userdb
