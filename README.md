# Test Kafka Protobuf Events 

## Context

- You need a Schema Registry of version 5.5.0+ to have the support of Protobuf. (it's already correctly setup the docker-compose file)
- I use the lib https://github.com/sbt/sbt-protobuf to generate classes from Protobuf schemas.  
- A good doc is available here : https://docs.confluent.io/current/schema-registry/serdes-develop/serdes-protobuf.html

## How to clone (really ?) 

git clone git@github.com:PLarboulette/kafka-protobuf-events.git

## How to use 
- Add protobuf schemas into the protobuf folder of each project (consumer and producer). Your file need an extension .proto and to be located in this folder. I think you can customize this but why ? 
- Use `sbt protobufGenerate`. 
- Your new model(s) will be available into `{{messageName}}OuterClass.{{messageName` : for example, I define a model Animal, so 
the model I use with Scala is `AnimalOuterClass.Animal`. 
- Launch the environment : `docker-compose -f docker-compose.yml up`
- Run the consumer 
- Run the producer. You can change the name of the animal to see the difference between the records in the topic.  

## Some command lines 
- To see the available subjects in the Schema Registry, you can use : `curl http://localhost:8081/subjects`
- To see the details about the schema, you can use `curl http://localhost:8081/subjects/{{yourSchemaReturnedByThePreviousCall}}/versions/1` (change the version if you have made an evolution)