import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class SimpleKafkaAvroCustomerGenericRecordProducer {

    private static final String BROKERHOST = "127.0.0.1";
    private static final String BROKERPORT = "9092";
    private static final String TOPIC = "AvroTest";

    public static void main(String[] args) {
        Properties p = new Properties();

        // Properties are created similarly , note the KafkaAvroSerializer used here instead of StringSerializer
        p.put("bootstrap.servers",BROKERHOST+":"+BROKERPORT);
        //p.put("key.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        p.put("key.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        p.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        p.put("schema.registry.url", "http://127.0.0.1:8081");


        String schemaString = "{\"namespace\": \"customerManagement.avro\", \"type\": \"record\", " +
                "\"name\": \"Customer\"," +
                "\"fields\": [" +
                "{\"name\": \"id\", \"type\": \"int\"}," +
                "{\"name\": \"name\", \"type\": \"string\"}" +
                "]}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(schemaString);
        Producer<Integer, GenericRecord> pd = new KafkaProducer<>(p);

        CustomerGenerator myGenerator = new CustomerGenerator();
        Customer u = myGenerator.getNext();
        String currCustomerName = u.getName();
        Integer currCustomerId = u.getID();
        GenericRecord customer = new GenericData.Record(schema);
        customer.put("id",currCustomerId);
        customer.put("name",currCustomerName);
        ProducerRecord<Integer, GenericRecord> rec = new ProducerRecord<Integer, GenericRecord>(TOPIC,
                1, customer);
        try {
            pd.send(rec);
            // Capture the Future information and see which all things are reorted by Kafka
            Future<RecordMetadata> resultFuture = pd.send(rec);
            System.out.println("Avro Message sent to partition " + resultFuture.get().partition());
            System.out.println("Offset of message is " + resultFuture.get().offset());
            System.out.println("Topic of the message is " + resultFuture.get().topic());
        } catch (Exception e) {
            System.out.println("Failed to send Avro message");
            e.printStackTrace();
        }
    }
    }

