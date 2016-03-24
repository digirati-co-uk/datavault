DataVault - A long term archive for Research Data
=================================================
![Alt text](/logo-dvsmall.jpg?raw=true "Data Vault logo")

Status
---
![Alt text](https://travis-ci.org/DataVault/datavault.svg?branch=master "Travis build status")

What
----
A Jisc-funded project to create an archive service for Research Data.  Funded under the 'Research at Risk' Data Spring programme.

Who
---
Originally developed by:

 * Tom Higgins - University of Manchester
 * Mary McDerby - University of Manchester
 * Robin Taylor - University of Edinburgh
 * Claire Knowles - University of Edinburgh
 * Stuart Lewis - University of Edinburgh

Further Information
-------------------
Project website: http://datavaultplatform.org/

Installation
------------

  * Clone from Github: https://github.com/DataVault/datavault.git
  * Install MySQL: https://www.mysql.com/
  * Setup database and username to match those in build.properties:
  * CREATE USER 'datavault'@'localhost' IDENTIFIED BY 'datavault';
  * CREATE DATABASE datavault;
  * GRANT ALL ON datavault.* TO 'datavault'@'localhost';
  * Install RabbitMQ: https://www.rabbitmq.com
  * Start up RabbitMQ - should get a healthy startup message
  * Follow the RabbitMQ Browser admin tool instructions: https://www.rabbitmq.com/management.html
  * Create a RabbitMQ user in the RabbitMQ admin tool with the username and password as defined in build.properties
  * Grant permissions for the new user to access the '/' virtual host 
  * Go into the data-vault home directory and run 'mvn package'. This will generate a datavault-home directory in dspace-assembly/target.
  * Start the worker by:
  * 'cd datavault-assembly/target/datavault-assembly-1.0-SNAPSHOT-assembly/datavault-home/lib'
  * java -cp datavault-worker-1.0-SNAPSHOT.jar:./* org.datavaultplatform.worker.WorkerManager
  * Deploy the datavault-webapp and default-broker to a servlet container (e.g. Tomcat)
  * Start webserver
