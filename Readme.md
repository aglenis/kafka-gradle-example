# Kafka Gradle Project
The purpose of this project is to integrate Kafka with various serialization formats and use Gradle as a build System.

So far the supported formats are:
 * Primitive Types (in the example Strings), 
 * Avro Generic Records 
 * Kryo.
 
 Soon there will be Parquet support


## Preparation bits

* Kafka and Confluent

Download Confluent and extract it to some place.
Call it CONFLUENT_HOME


`cd $CONFLUENT_HOME`

Start confluent in separate terminals and leave them running

`./bin/zookeeper-server-start ./etc/kafka/zookeeper.properties`

`./bin/kafka-server-start ./etc/kafka/server.properties`

`./bin/schema-registry-start ./etc/schema-registry/schema-registry.properties`



# Creating the required topics

##  Step 1 : Create topic

Create first topic with single partition and replication

`cd $CONFLUENT_HOME`

`./bin/kafka-topics --zookeeper localhost:2181 --create --topic test  --partitions 1 --replication-factor 1`

The code above creates the topic for the primitive types.

The naming convention for test topics is as follows:

* Primitive Types: test
* Avro : avroTest
* Kryo : kryoTest

## Step 2 : Start the Consumers

To start the Consumers change directory to the root of the project and type in the terminal

`gradle -PmainClass=KafkaSimpleConsumer execute`

The Producer main Classes are as follows:

* Primitive Types: KafkaSimpleConsumer
* Avro : SimpleKafkaAvroGenericRecordConsumer
* Kryo : SimpleKafkaKryoCustomerConsumer

## Step 3 : Start the Producers

To start the Producers change directory to the root of the project and type in the terminal

`gradle -PmainClass=KafkaSimpleProducer execute`

The Producer main Classes are as follows:

* Primitive Types: KafkaSimpleProducer
* Avro : SimpleKafkaAvroGenericRecordProducer
* Kryo : SimpleKafkaKryoCustomerProducer
