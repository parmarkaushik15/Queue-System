package com.queuesystem.service;

import com.queuesystem.exception.AppException;
import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.percistance.repository.QueueMemberRepository;
import com.queuesystem.percistance.repository.QueueRepository;
import com.queuesystem.processor.QueueProcessor;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class QueueManager {

	private LogManager logManager;

	private QueueRepository queueRepository;

	private QueueMemberRepository queueMemberRepository;

	private Map<Long, QueueProcessor> queueMap;

	@Bean
	public Map<Long, QueueProcessor> myBean() {
		return queueMap;
	}

	@Autowired
	public QueueManager(QueueRepository queueRepository, LogManager logManager, QueueMemberRepository queueMemberRepository, Map<Long, QueueProcessor> queueMap) {
		this.queueRepository = queueRepository;
		this.logManager = logManager;
		this.queueMemberRepository = queueMemberRepository;
		this.queueMap = queueMap;
	}

	public void addQueue(String name) {
		Queue queue = new Queue();
		queue.setStatus(Status.INACTIVE);
		queue.setName(name);
		QueueProcessor queueProcessor = new QueueProcessor(queue, logManager, queueMemberRepository);
		queue = queueRepository.save(queue);
		queueMap.put(queue.getId(), queueProcessor);
	}

	public void startQueue(String queueName) {
		Queue queue = queueRepository.findByName(queueName);
		queueMap.get(queue.getId()).start();
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
		queueMap.forEach((e1,e2) -> {
			System.out.println(e1);
			System.out.println(e2);
		});
		queueMap.get(queue.getId()).addMember(memberName);
	}

	public String getNumber(String name, String queueName) {
		Queue queue = queueRepository.findByName(queueName);
		return "";
	}
}