package com.huoli.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 
 * flightcdm kafka监听器<br>
 * 版权: Copyright (c) 2011-2018<br>
 * 
 * @author: 孙常军<br>
 * @date: 2018年8月23日<br>
 */
@Component
public class KafkaFlightCdmListener {
	private static final Logger logger = LoggerFactory.getLogger(KafkaFlightCdmListener.class);

	@KafkaListener(topics = "flightcdm", containerFactory = "flightCdmKafkaListenerContainerFactory")
	public void listen(ConsumerRecord<?, ?> record) {
		logger.info("flightcdm::offset:{}, message:{}", record.offset(), record.value());
	}

}
