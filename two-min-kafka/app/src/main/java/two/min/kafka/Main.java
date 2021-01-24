/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package two.min.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class Main {
    private static final String TOPIC = "my-first-topic";

    public static void main(String[] args) {
        System.out.println(new Main().getGreeting());
        sendMsg();
        consumeMsg();
    }

    private static void sendMsg() {
        try (Producer<String, String> producer = ProducerCreater.createProducer()) {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(TOPIC, "hello, 1st java-msg");
            // send msg
            RecordMetadata metadata = producer.send(record).get();
            System.out.println("Record sent to partition: " + metadata.partition()
                    + " with offset: " + metadata.offset());
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Error in producer sending msg");
            e.printStackTrace();
        }

    }

    private static void consumeMsg() {
        try (Consumer<String, String> consumer = ConsumerCreator.createConsumer()) {
           while (true) {
               // subscribe topic and consume msg
               consumer.subscribe(Collections.singleton(TOPIC));

               ConsumerRecords<String, String> consumerRecords =
                       consumer.poll(Duration.ofMillis(1000));
               for (ConsumerRecord<String, String> record : consumerRecords) {
                   System.out.println("Consumer consumed msg: " + record.value());
               }
           }
        }
    }
//    1
//    3
//    72
//    a

    public String getGreeting() {
        return "Hello World, 5min quick-start Kafka-java";
    }


}