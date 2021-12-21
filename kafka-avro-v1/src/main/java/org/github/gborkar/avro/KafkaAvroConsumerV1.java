package org.github.gborkar.avro;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import com.example.Customer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;

public class KafkaAvroConsumerV1 {

    public static void main(String[] args) {
        final String topic = "customer-avro";
        
        Properties consumerProperties = new Properties();

        // Consumer properties
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-config-group");
        consumerProperties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Avro properties
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        consumerProperties.setProperty("schema.registry.url", "http://127.0.0.1:8081");
        consumerProperties.setProperty("specific.avro.reader", "true");

        KafkaConsumer<String, Customer> consumer = new KafkaConsumer<String, Customer>(consumerProperties);
        consumer.subscribe(Collections.singleton(topic));
        
        while (true){
            System.out.println("Polling");
            ConsumerRecords<String, Customer> consumerRecords = consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord <String, Customer> record : consumerRecords) {
                Customer customer = record.value();
                System.out.println(customer);
            }

            consumer.commitSync();
        }
    }
}
