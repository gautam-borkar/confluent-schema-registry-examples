package org.github.gborkar.avro;

import java.util.Properties;

import com.example.Customer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

/**
 * Hello world!
 *
 */
public class KafkaAvroProducerV2 
{
    public static void main( String[] args )
    {
        final String topic = "customer-avro";
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        producerProperties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        producerProperties.setProperty(ProducerConfig.RETRIES_CONFIG, "10");
        
        // Avro
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        producerProperties.setProperty("schema.registry.url", "http://127.0.0.1:8081");

        // // Create a producer
        Producer<String, Customer> producer = new KafkaProducer<String, Customer>(producerProperties);
        
        // Customer object 
        Customer customer = Customer.newBuilder()
            .setFirstName("John")
            .setLastName("Doe")
            .setAge(25)
            .setHeight(175f)
            .setWeight(50)
            .setPhoneNumber("888-888-8888")
            .setEmail("test@test.com")
            .build();

        ProducerRecord<String, Customer> producerRecord = new ProducerRecord<String, Customer>(topic, customer);
        System.out.println(customer);

        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception == null) {
                    System.out.println(metadata);
                } else {
                    exception.printStackTrace();
                }
            }
        });

        producer.flush();
        producer.close();
    }
}
