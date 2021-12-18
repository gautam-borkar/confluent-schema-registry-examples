package org.github.gborkar.avro;

import java.util.Properties;

/**
 * Hello world!
 *
 */
public class KafkaAvroProducerV1 
{
    public static void main( String[] args )
    {
        Properties producerProperties = new Properties();
        producerProperties.setProperty("bootstrap.server", "cp-helm-charts-cp-kafka-headless:9092");
        producerProperties.setProperty("acks", "all");
        producerProperties.setProperty("retries", "10");
        
        // Avro
        producerProperties.setProperty("schema.registry", "http://cp-helm-charts-cp-schema-registry:8081");
        producerProperties.setProperty("arg0", "arg1");
        producerProperties.setProperty("arg0", "arg1");
    }
}
