# Hello MongoDB!

This project shows how to create a [Spring Boot](https://spring.io/projects/spring-boot)
app connected to a [MongoDB](https://www.mongodb.com) database.

This app is made of a single REST controller, which will simply stores input requests to
a MongoDB database.

## How to use it?

Compile this app using a JDK 8+:
```bash
$ ./mvnw clean package
```

Start a MongoDB instance on your workstation:
```bash
$ docker run -p 27017:27017/tcp mongo:4
```

Start this app locally:
```bash
$ java -jar target/hello-mongodb.jar
```

The app is available at http://localhost:8080:
```bash
$ curl localhost:8080
Hello world: current time is 2019-07-05T09:20:18.605Z%
```

## Deploying to Cloud Foundry

This project includes a manifest to deploy this app to Cloud Foundry.

Using Cloud Foundry CLI, create a MongoDB instance
(depending on your installation, this command may differ):
```bash
$ cf create-service mongodb-odb replica_set_small my-mongodb
```

Push this app to Cloud Foundry:
```bash
$ cf push
```

The app will fail to start, because you need to bind it to your MongoDB instance:
```bash
$ cf bind-service hello-mongodb my-mongodb
$ cf restage hello-mongodb
```

At this point, you should be able to hit the REST controller: entities will be inserted
to the MongoDB instance.

## Contribute

Contributions are always welcome!

Feel free to open issues & send PR.

## License

Copyright &copy; 2019 [Pivotal Software, Inc](https://pivotal.io).

This project is licensed under the [Apache Software License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
