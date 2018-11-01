package com.perfaware.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerWithThreads {

	public static void main(String[] args) 
	{
		new ConsumerWithThreads().run();
	}
	
	private ConsumerWithThreads()
	{
	}
	
	public void run()
	{
		final Logger logger=LoggerFactory.getLogger(ConsumerWithThreads.class);
		String group_id="grp3";
		String topic_id="first_topic";
		
		//latch to deal  with multiple threads
		CountDownLatch latch=new CountDownLatch(1);
		
		//Creating the consumer runnable
		logger.info("Creating consumer thread");
		Runnable myConsumerRunnable=new ConsumerRunnable(latch,topic_id,group_id);
		Thread myThread=new Thread(myConsumerRunnable);
		
		
		myThread.start();
		
		//add a shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread( () -> 
			{	
				logger.info("Caught shutdown hook");
				((ConsumerRunnable)myConsumerRunnable).shutdown();
				try {
					latch.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Application has exited");
			}
		));
		
		
		try {
			latch.await();
		} 
		catch (InterruptedException e) 
		{
			logger.info("Application got interruped",e);
			e.printStackTrace();
		}
		finally
		{
			logger.info("Applicationn is closing");
		}
		
	}
	
	class ConsumerRunnable implements Runnable
	{
		private CountDownLatch latch;
		private Logger logger=LoggerFactory.getLogger(ConsumerRunnable.class);
		KafkaConsumer<String, String> consumer;
		
		
		public ConsumerRunnable(CountDownLatch latch, String topic_id,String group_id)
		{
			this.latch=latch;
			
			//setting up the consumer config
			Properties properties=new Properties();
			properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
			properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, group_id);
			
			//create consumer
			consumer=new KafkaConsumer<String, String>(properties);
			//subscribe consumer
			consumer.subscribe(Arrays.asList(topic_id));
		}

		public void run() 
		{
			try
			{
				//poll foe new data
				while(true)
				{
					ConsumerRecords<String, String> consumer_records=consumer.poll(Duration.ofMillis(100));
					
					for(ConsumerRecord<String, String> record:consumer_records)
					{
						logger.info("Key: "+record.key()+"\n"+"Value: "+record.value()+"\n"+
									"Partition: "+record.partition()+"\n"+"Offset: "+record.offset());
					}
				}
			}
			catch(WakeupException e)
			{
				logger.info("Received shutdown signal");
			}
			finally
			{
				consumer.close();
				//inform main code that consuming is done
				latch.countDown(); 
			}
		}
		
		public void shutdown()
		{
			//it is a method used to interrupt consumer.poll by raising the exception
			consumer.wakeup();
		}
	}
}
