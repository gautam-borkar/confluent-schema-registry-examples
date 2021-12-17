package org.github.gborkar.avro.specifics;

import java.io.File;
import java.io.IOException;

import com.example.Customer;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

public class SpecificsRecordExample {
    public static void main(String[] args) {
        // Create specifics record
        Customer.Builder customerBuilder = Customer.newBuilder();
        customerBuilder.setFirstName("John");
        customerBuilder.setLastName("Doe");
        customerBuilder.setAge(25);
        customerBuilder.setHeight(170.0f);
        customerBuilder.setWeight(85f);
        customerBuilder.setAutomatedEmail(false);
        Customer customer = customerBuilder.build();
        System.out.println(customer);

        // Write to a file
        final DatumWriter<Customer> datumWriter = new SpecificDatumWriter<>(Customer.class);

        try (DataFileWriter<Customer> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(customer.getSchema(), new File("customer-specific.avro"));
            dataFileWriter.append(customer);
            System.out.println("successfully wrote customer-specific.avro");
        } catch (IOException e){
            e.printStackTrace();
        }
        
        // Read from a file
        final File file = new File("customer-specific.avro");
        final DatumReader<Customer> datumReader = new SpecificDatumReader<>(Customer.class);
        final DataFileReader<Customer> dataFileReader;
        try {
            System.out.println("Reading our specific record");
            dataFileReader = new DataFileReader<>(file, datumReader);
            while (dataFileReader.hasNext()) {
                Customer readCustomer = dataFileReader.next();
                System.out.println(readCustomer.toString());
                System.out.println("First name: " + readCustomer.getFirstName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
