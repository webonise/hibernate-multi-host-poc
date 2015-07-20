Proof of Concept: Hibernate with MySQL Multi-Host
===================================================

The MySQL driver has [built-in support for master-slave replication](http://dev.mysql.com/doc/connector-j/en/connector-j-master-slave-replication-connection.html).
However, how does this work with Hibernate? The PoC needs to demonstrate the following things, preferably through automated tests.

1. There are two MySQL servers, one of which is routed to master/read-write by the driver, and one of which is routed to as slave/read-only by the driver.
1. There can be a single Hibernate session which uses a single DB connection, but which can use the master/read-write or the slave/read-only when configured by some kind of `setReadOnly(true)` call.
1. We can persist entities through that Hibernate session, and we can retrieve entities through that Hibernate session.
1. Caching of entities works within that single Hibernate session.
