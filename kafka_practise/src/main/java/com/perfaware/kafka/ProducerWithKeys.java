package com.perfaware.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerWithKeys {

	public static void main(String[] args) 
	{
		final Logger logger=LoggerFactory.getLogger(ProducerWithKeys.class);
		String topic="first_topic";
		String value="Assign and Seek ";		
		
		//creating producer properties
		Properties properties=new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName() );
		
		//create producer
		KafkaProducer<String, String> producer=new KafkaProducer<String, String>(properties);
		
		for(int i=0;i<10;i++)
		{
			final String key="id_"+Integer.toString(i);
			
			//create a producerRecord
			ProducerRecord<String, String> producerrecord=
					new ProducerRecord<String, String>(topic, key, value+Integer.toString(i));
			
			//send data asynchronously
			producer.send(producerrecord, new Callback() {
				
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if(exception==null)
					{
						logger.info("Key: "+key);
						logger.info("Received messages. \n"+"Topic: "+metadata.topic()+"\n"+
									"Partition: "+metadata.partition()+"\n"+"Offsets: "+metadata.offset()+
									"\n"+"Timestamp: "+metadata.timestamp()+"\n");
					}
					else
					{
						exception.printStackTrace();
					}
				}
			});
		}
		
		//flushing the data
		producer.close();
	
	}

}
