import java.util.Properties;

/**
 * Created by aglenis on 12/21/16.
 */

import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerRecord;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class SimpleKafkaAvroProducer {

    private static final String BROKERHOST = "127.0.0.1";
    private static final String BROKERPORT = "9092";
    private static final String TOPIC = "AvroTest";

    public static void main(String[] args) {
        Properties p = new Properties();

        // Properties are created similarly , note the KafkaAvroSerializer used here instead of StringSerializer
        p.put("bootstrap.servers",BROKERHOST+":"+BROKERPORT);
        //p.put("key.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        p.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        p.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        p.put("schema.registry.url", "http://127.0.0.1:8081");

        Producer<String, String> pd = new KafkaProducer<>(p);

        //CustomerGenerator myGenerator = new CustomerGenerator();
        //Customer u = myGenerator.getNext();
        ProducerRecord<String, String> rec = new ProducerRecord<String, String>(TOPIC,
                "lala", "value");
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

