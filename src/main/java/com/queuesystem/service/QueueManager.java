package com.queuesystem.service;

import com.queuesystem.exception.AppException;
import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.percistance.repository.QueueMemberRepository;
import com.queuesystem.percistance.repository.QueueRepository;
import com.queuesystem.processor.QueueProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QueueManager {

	private static final Logger log = LoggerFactory.getLogger(QueueManager.class);

	private QueueRepository queueRepository;

	private QueueMemberRepository queueMemberRepository;

	private Map<Long, QueueProcessor> queueMap;

	@Autowired
	public QueueManager(QueueRepository queueRepository, QueueMemberRepository queueMemberRepository, Map<Long, QueueProcessor> queueMap) {
		this.queueRepository = queueRepository;
		this.queueMemberRepository = queueMemberRepository;
		this.queueMap = queueMap;
	}

	public void addQueue(String name) {
		Queue queue = new Queue();
		queue.setStatus(Status.INACTIVE);
		queue.setName(name);
		QueueProcessor queueProcessor = new QueueProcessor(queue, queueMemberRepository, queueRepository);
		queue = queueRepository.save(queue);
		queueMap.put(queue.getId(), queueProcessor);
		log.info("Added new Queue: [ " + queue.getName() + " ]");
	}

	public void startQueue(String queueName) {
		Queue queue = queueRepository.findByName(queueName);
		queueMap.get(queue.getId()).start();
		queue.setStatus(Status.ACTIVE);
		queueRepository.save(queue);
		log.info("Queue: [ " + queue.getName() + " ] Started");

	}

	public void stopQueue(String queueName) {
		Queue queue = queueRepository.findByName(queueName);
		queueMap.get(queue.getId()).pause();
		queue.setStatus(Status.INACTIVE);
		queueRepository.save(queue);
	}

	public void addMember(String memberName, String queueName) throws AppException {
		Queue queue = queueRepository.findByName(queueName);
		queueMap.get(queue.getId()).addMember(memberName);
	}

	public String getNumber(String name, String queueName) {
		Queue queue = queueRepository.findByName(queueName);
		return "";
	}
}