
import org.junit.Test;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaSimpleProducer {

    //private static final String ZKHOST = "127.0.0.1";
   private static final String BROKERHOST = "127.0.0.1";
   private static final String BROKERPORT = "9092";
   private static final String TOPIC = "test";

    @Test
    public static void main(String[] args) throws InterruptedException, IOException {

        Properties p = new Properties();

        // Declare the propeties of cluster and informationa about data key and value
        p.put("bootstrap.servers",BROKERHOST+":"+BROKERPORT);
        p.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        p.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        // Create producer and send data in format : (topic name , key , value)
        Producer<String,String> pd = new KafkaProducer<>(p);
        ProducerRecord<String,String> rec = new ProducerRecord<>(TOPIC ,"key","value");

        // 1) Fire and forget
        pd.send(rec);
        pd.close();


    }
}
