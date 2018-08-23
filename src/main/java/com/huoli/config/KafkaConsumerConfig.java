package com.huoli.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

/**
 * 
 * Kafka消费者配置<br>
 * 版权: Copyright (c) 2011-2018<br>
 * 
 * @author: 孙常军<br>
 * @date: 2018年8月23日<br>
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value("${tripevent.kafka.group.id}")
	public String tripEventGroupId;

	@Value("${tripevent.kafka.bootstrap.servers}")
	public String tripEventBootstrapServers;

	@Value("${flightcdm.kafka.group.id}")
	public String flightCdmGroupId;

	@Value("${flightcdm.kafka.bootstrap.servers}")
	public String flightCdmBootstrapServers;

	public Map<String, Object> tripEventCconsumerConfigs() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("bootstrap.servers", tripEventBootstrapServers);
		props.put("group.id", tripEventGroupId);
		props.put("enable.auto.commit", Boolean.valueOf(true));
		props.put("auto.commit.interval.ms", "100");
		props.put("session.timeout.ms", "15000");
		props.put("key.deserializer", StringDeserializer.class);
		props.put("value.deserializer", StringDeserializer.class);
		return props;
	}

	public Map<String, Object> flightCdmCconsumerConfigs() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("bootstrap.servers", flightCdmBootstrapServers);
		props.put("group.id", flightCdmGroupId);
		props.put("enable.auto.commit", Boolean.valueOf(true));
		props.put("auto.commit.interval.ms", "100");
		props.put("session.timeout.ms", "15000");
		props.put("key.deserializer", StringDeserializer.class);
		props.put("value.deserializer", StringDeserializer.class);
		return props;
	}

	public ConsumerFactory<String, String> tripEventConsumerFactory() {
		return new DefaultKafkaConsumerFactory<String, String>(tripEventCconsumerConfigs());
	}

	public ConsumerFactory<String, String> flightCdmConsumerFactory() {
		return new DefaultKafkaConsumerFactory<String, String>(flightCdmCconsumerConfigs());
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> tripEventKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(tripEventConsumerFactory());
		factory.setConcurrency(1);// 启动一个监听,为了顺序消费,如果不考虑顺序和负载，可以考虑多个
		return factory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> flightCdmKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(flightCdmConsumerFactory());
		factory.setConcurrency(1);
		return factory;
	}

}
