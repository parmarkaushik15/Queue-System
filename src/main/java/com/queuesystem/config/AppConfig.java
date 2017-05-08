package com.queuesystem.config;

import com.queuesystem.processor.QueueProcessor;
import java.util.HashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	HashMap<Long, QueueProcessor> queueMap = new HashMap<>();

	@Bean
	public HashMap<Long, QueueProcessor> queueMap() {
		return queueMap;
	}
}
