import java.util.{Properties, UUID}

import AnimalOuterClass.{Animal, Bird, Dog}
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.KafkaProducer

object Main extends App {

  println("Hello, I'm the producer ! :) ")

  val props = new Properties();
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer")
  props.put("schema.registry.url", "http://127.0.0.1:8081")


   val producer = new KafkaProducer[String, AnimalOuterClass.Animal](props)

  import org.apache.kafka.clients.producer.ProducerRecord

  val topic = "animal-topic"
  val key = UUID.randomUUID().toString

  val dog: Animal = AnimalOuterClass.Animal.newBuilder()
    .setName("Beethoven")
    .setAge(12)
    .setDog(Dog.newBuilder().setPaws(4))
    .build()

  val bird: Animal = AnimalOuterClass.Animal.newBuilder()
    .setName("Titi")
    .setAge(56) // Yes, why not ?
    .setBird(Bird.newBuilder().setWings(2))
    .build()

  val dogRecord = new ProducerRecord[String, AnimalOuterClass.Animal](topic, key, dog)
  val birdRecord = new ProducerRecord[String, AnimalOuterClass.Animal](topic, key, bird)


  // Because we love Java Futures
 val eventFoeDog = producer.send(dogRecord).get()
  println("eventFoeDog pushed ! ")
  val eventForBird = producer.send(birdRecord).get()
  println("eventForBird pushed ! ")

}
