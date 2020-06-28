---
WARNING: This is not to be used for productiv systems 
---

# MicroProfile generated Application

## Introduction

MicroProfile Starter has generated this MicroProfile application for you containing some endpoints which are called from the main application (see the `service-a` directory)

The generation of the executable jar file can be performed by issuing the following command

    mvn clean package

This will create an executable jar file **ArtemisMQBackend.jar** within the _target_ maven folder. This can be started by executing the following command

    java -jar target/ArtemisMQBackend.jar 


### Liberty's Dev Mode

During development, you can use Liberty's development mode (dev mode) to code while observing and testing your changes on the fly.
With the dev mode, you can code along and watch the change reflected in the running server right away; 
unit and integration tests are run on pressing Enter in the command terminal; you can attach a debugger to the running server at any time to step through your code.

    mvn liberty:dev

## Specific notes

`../apache-artemis-2.13.0/bin/artemis create mybroker`

Creating ActiveMQ Artemis instance at: `/home/<user>/artemismq/lib/mybroker`
```
--user: is a mandatory property!
Please provide the default username:
admin

--password: is mandatory with this configuration:
Please provide the default password:


--allow-anonymous | --require-login: is a mandatory property!
Allow anonymous access?, valid values are Y,N,True,False
N

Auto tuning journal ...
done! Your system can make 83.33 writes per millisecond, your journal-buffer-timeout will be 12000
```
You can now start the broker by executing:  

  `"/home/<user>/artemismq/lib/mybroker/bin/artemis" run`

Or you can run the broker in the background using:

   `"/home/<user>/artemismq/lib/mybroker/bin/artemis-service" start`
`

After starting the artemis service in background you can access the console:

[Artemis Management Console](http://localhost:8161/console)

#### ArtemisMQ RAR
You have to build your own ArtemisMQ RAR for working with OpenLiberty.
The ArtemisMQ distribution you dowloaded to `<your path>`:
`<your path>/apache-artemis-2.13.0/examples/features/sub-modules/artemis-ra-rar`

##### Step 1: Build your own transaction support for OpenLiberty

Using the sub project txm-openliberty you find the TransactionLocator.
This class just gets the TransactionManager from OpenLiberty and provides it to ArtemisMQ.

In `META-UNF/services` you link the implementation class to the
 `org.apache.activemq.artemis.service.extensions.transactions.TransactionManagerLocator`

Some more information you find on [ARTEMIS-1070](https://issues.apache.org/jira/browse/ARTEMIS-1070) and
[ARTEMIS-1487](https://issues.apache.org/jira/browse/ARTEMIS-1487).

I thank to [Bian S Paskin](https://paskino.com/) for the investigation work and the help.

##### Step 2: Build the updated ArtemisMQ RAR

The RAR pom.xml you have to update with the dependency to your transaction support jar:
```
<dependency>
    <groupId>ch.berchtold.microprofile.sample</groupId>
	<artifactId>artemismq-txm-openliberty</artifactId>      
	<version>1.0-SNAPSHOT</version>
</dependency>
``` 
Redefine `artifactId` to `<artifactId>artemis-rar-openliberty</artifactId>` for identifying easier that is a
special updated version for OpenLiberty.
Then you just build your rar JCA adapter with `mvn clean install`

In the target folder you will find your rar file. This you can now copy over to the service backend rar directory.

In the pom.xml from the `service-backend` I used the plugin `maven-resources-plugin` for placing the rar adapter to the 
right place in OpenLiberty run time environment.