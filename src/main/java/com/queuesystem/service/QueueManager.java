package com.queuesystem.service;

import com.queuesystem.exception.AppException;
import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.percistance.repository.QueueRepository;
import com.queuesystem.processor.QueueProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class QueueManager {

	private LogManager logManager;

	private QueueRepository queueRepository;

	private Map<Queue, QueueProcessor> queueMap = new HashMap<>();

	@Autowired
	public QueueManager(QueueRepository queueRepository, LogManager logManager) {
		this.queueRepository = queueRepository;
		this.logManager = logManager;
	}

	public void addQueue(String name) {
		Queue queue = new Queue();
		queue.setStatus(Status.INACTIVE);
		queue.setName(name);
		QueueProcessor queueProcessor = new QueueProcessor(queue, logManager);
		queue = queueRepository.save(queue);
		queueMap.put(queue, queueProcessor);
	}

	public void startQueue(String queueName) {
		Queue queue = queueRepository.findByName(queueName);
		queueMap.get(queue).start();
		queue.setStatus(Status.ACTIVE);
		queueRepository.save(queue);
	}

	public void stopQueue(String queueName) {
		Queue queue = queueRepository.findByName(queueName);
		queueMap.get(queue).pause();
		queue.setStatus(Status.INACTIVE);
		queueRepository.save(queue);
	}

	public void addMember(String memberName, String queueName) throws AppException {
		Queue queue = queueRepository.findByName(queueName);
//		System.out.println(queueMap.get(queue));
//		System.out.println(queue.hashCode());
//		System.out.println(queueMap.isEmpty());
//		queueMap.forEach((e,e2) ->{
//			System.out.println(e.hashCode());
//		});
		System.out.println(queue.hashCode());
		queueMap.forEach((e1,e2) ->{
			System.out.println(e1.hashCode());
		});
		queueMap.get(queue).addMember(memberName);
	}
}
