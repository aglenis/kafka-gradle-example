/**
 * Created by aglenis on 1/13/17.
 */

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import java.io.Closeable;
import java.util.Map;

/**
 * (De)serializes SensorReadings using Kryo.
 */

public class CustomerKryoSerializer implements Closeable, AutoCloseable, Serializer<Customer>, Deserializer<Customer> {


        private ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>() {
            protected Kryo initialValue() {
                Kryo kryo = new Kryo();
                kryo.addDefaultSerializer(Customer.class, new KryoInternalSerializer());
                return kryo;
            };
        };

        @Override
        public void configure(Map<String, ?> map, boolean b) {
        }

        @Override
        public byte[] serialize(String s, Customer customer) {
            ByteBufferOutput output = new ByteBufferOutput(100);
            kryos.get().writeObject(output, customer);
            return output.toBytes();
        }

        @Override
        public Customer deserialize(String s, byte[] bytes) {
            try {
                return kryos.get().readObject(new ByteBufferInput(bytes), Customer.class);
            }
            catch(Exception e) {
                throw new IllegalArgumentException("Error reading bytes",e);
            }
        }

        @Override
        public void close() {

        }

        private static class KryoInternalSerializer extends com.esotericsoftware.kryo.Serializer<Customer> {
            @Override
            public void write(Kryo kryo, Output output, Customer customer) {
                output.writeInt(customer.getID());
                output.writeString(customer.getName());
            }

            @Override
            public Customer read(Kryo kryo, Input input, Class<Customer> aClass) {
                int id = input.readInt();
                String name = input.readString();

                return new Customer(id, name);
            }
        }
    }


