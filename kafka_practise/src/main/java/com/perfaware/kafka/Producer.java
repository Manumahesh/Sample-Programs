package com.perfaware.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer {

	public static void main(String[] args) 
	{
		//creating producer properties
		Properties properties=new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName() );
		
		//create producer
		KafkaProducer<Integer, String> producer=new KafkaProducer<Integer, String>(properties);
		
		//create a producerRecord
		ProducerRecord<Integer, String> producerrecord=new ProducerRecord<Integer, String>("fifth_topic","sample run");
		
		//send data
		producer.send(producerrecord);
		
		//flushing the data
		producer.close();
	
	}

}
