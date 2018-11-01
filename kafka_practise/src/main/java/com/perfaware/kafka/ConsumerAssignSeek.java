package com.perfaware.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerAssignSeek {

	public static void main(String[] args) 
	{
		Logger logger=LoggerFactory.getLogger(ConsumerAssignSeek.class);
		String topic_id="first_topic";
		long offsetToReadFrom = 7L;	
		boolean keepReading = true;
		
		//setting up the consumer config
		Properties properties=new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		//create consumer
		KafkaConsumer<String, String> consumer=new KafkaConsumer<String, String>(properties);
		
		//Assign and Seek is used to replay data or fetch a specific message
		
		//Assign
		TopicPartition PartitionToReadFrom = new TopicPartition(topic_id, 0);
		consumer.assign(Arrays.asList(PartitionToReadFrom));
		
		//Seek
		consumer.seek(PartitionToReadFrom, offsetToReadFrom);
		
		//poll foe new data
		while(keepReading)
		{
			ConsumerRecords<String, String> consumer_records=consumer.poll(Duration.ofMillis(100));
			
			for(ConsumerRecord<String, String> record:consumer_records)
			{
				logger.info("Key: "+record.key()+"\n"+"Value: "+record.value()+"\n"+
							"Partition: "+record.partition()+"\n"+"Offset: "+record.offset());
			}		
		}
		consumer.close();
	}
}
