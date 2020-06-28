# ActiveMQ Sample

There are two microservices, one activemq-service-backend and one activemq-service-client.

There are 2 projects generated so that the examples for the Rest Client and/or JWT Auth specification are more realistic in the sense that they actually call an endpoint within another service.

. In the `service-client` directory, you find a 'service-client' which does provide an OpenAPI Swagger UI for testing and calls the 'service-backend'.
. In the `service-backend` directory, you can find some endpoints which will be called by code within the client application. This can be seen as the 'backend'.

For being able to run the sample just use ActiveMQ local installation as described here: [ActiveMQ Start Guide](https://activemq.apache.org/version-5-getting-started.html)

**Important** 
ActiveMQ should after installation be available on Port 61616 with the default `user:password admin:admin`

Have a look in the `readme.md` file in each directory which describes how each project can be built and run.