import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.Properties
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util
import com.google.protobuf.Message

import scala.collection.JavaConversions._

object Main extends App {

  println("Hello, I'm the consumer ! :)")

  val props = new Properties()
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer")
  props.put("schema.registry.url", "http://localhost:8081")
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val topic = "animal-topic"
  val consumer = new KafkaConsumer[String, Message](props)
  consumer.subscribe(util.Arrays.asList(topic))

  // Yeah, it’s ugly, but it’s a PoC, and PoCs never go into production anyway
  try while ( {
    true
  }) {
    val records = consumer.poll(Duration.of(5, ChronoUnit.SECONDS))
    for (record <- records) {
      val animal = AnimalOuterClass.Animal.parseFrom(record.value().toByteArray)
      if (animal.hasBird) {
        println(s"Oh, it's bird with ${animal.getBird.getWings} wings !")
      } else if (animal.hasDog) {
        println(s"Oh, it's a dog with ${animal.getDog.getPaws} paws !")
      } else {
        println("Oh, it's a ... what ? ")
      }
    }
  }
  finally consumer.close()

}
