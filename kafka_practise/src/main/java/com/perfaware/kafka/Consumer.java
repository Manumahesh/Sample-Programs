package com.perfaware.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {

	public static void main(String[] args) 
	{
		Logger logger=LoggerFactory.getLogger(Consumer.class);
		String group_id="grp3";
		String topic_id="first_topic";
		
		//setting up the consumer config
		Properties properties=new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group_id);
		
		//create consumer
		KafkaConsumer<String, String> consumer=new KafkaConsumer<String, String>(properties);
		
		//subscribe consumer
		consumer.subscribe(Arrays.asList(topic_id));
		
		//poll foe new data
		while(true)
		{
			ConsumerRecords<String, String> consumer_records=consumer.poll(Duration.ofMillis(100));
			
			for(ConsumerRecord<String, String> record:consumer_records)
			{
				System.out.println(consumer.toString());
			}
		}
	}
}
