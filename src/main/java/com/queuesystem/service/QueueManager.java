package com.queuesystem.service;

import com.queuesystem.percistance.model.QueueInfo;
import com.queuesystem.processor.QueueProcessor;
import org.springframework.stereotype.Service;

@Service
public class QueueManager {

	public void addQueue(String name) {
		QueueInfo queueInfo = new QueueInfo();
		queueInfo.setName(name);
		QueueProcessor queueProcessor = new QueueProcessor(queueInfo);
	}
}
