import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by aglenis on 12/21/16.
 */
public class SimpleKafkaAvroCustomerGenericRecordConsumer {
    private static final String BROKERHOST = "127.0.0.1";
    private static final String BROKERPORT = "9092";
    private static final String TOPIC = "AvroTest";

    public static void main(String[] args) {



        Properties p = new Properties();
        p.put("bootstrap.servers",BROKERHOST+":"+BROKERPORT);
        p.put("group.id", "AvroConsumer");
        p.put("key.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        p.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        p.put("schema.registry.url", "http://127.0.0.1:8081");

        KafkaConsumer<Integer, GenericRecord> c = new KafkaConsumer<Integer, GenericRecord>(p);
        c.subscribe(Collections.singletonList(TOPIC));

        try {
            while (true) {
                ConsumerRecords<Integer, GenericRecord> rec = c.poll(100);
                System.out.println("We got record count " + rec.count());
                for (ConsumerRecord<Integer, GenericRecord> r : rec) {
                    System.out.println("Current consumer has id "+r.value().get(0).toString()+"and name "+r.value().get(1).toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }
    }
}

