package com.queuesystem.service;

import com.queuesystem.exception.AppException;
import com.queuesystem.exception.ErrorCode;
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

	public void addQueue(String name) throws AppException {
		checkIfExists(name);
		Queue queue = new Queue();
		queue.setStatus(Status.PAUSED);
		queue.setName(name);
		QueueProcessor queueProcessor = new QueueProcessor(queue, queueMemberRepository, queueRepository);
		queue = queueRepository.save(queue);
		queueMap.put(queue.getId(), queueProcessor);
		log.info("Added new Queue: [ " + queue.getName() + " ]");
	}

	public void startQueue(String queueName) throws AppException {
		Queue queue = queueRepository.findByName(queueName);
		if (queue.getStatus() == Status.ACTIVE) {
			throw new AppException(ErrorCode.QUEUE_IS_ALREADY_IN_STATUS, queueName, Status.ACTIVE.toString());
		}
		queueMap.get(queue.getId()).start();
		queue.setStatus(Status.ACTIVE);
		queueRepository.save(queue);
		log.info("Queue: [ " + queue.getName() + " ] Started");

	}

	public void pauseQueue(String queueName) throws AppException {
		changeStatus(queueName, Status.PAUSED);
	}

	public void stopQueue(String queueName) throws AppException {
		changeStatus(queueName, Status.CANCELLED);
	}

	public void addMember(String memberName, String queueName) throws AppException {
		Queue queue = queueRepository.findByName(queueName);
		queueMap.get(queue.getId()).addMember(memberName);
	}

	public String getNumber(String name, String queueName) {
		Queue queue = queueRepository.findByName(queueName);
		return "";
	}

	private void checkIfExists(String name) throws AppException {
		if (queueRepository.countByName(name) > 0) {
			throw new AppException(ErrorCode.QUEUE_NAME_IS_USED, name);
		}
	}

	private void changeStatus(String queueName, Status status) throws AppException {
		Queue queue = queueRepository.findByName(queueName);
		if (queue.getStatus() != status) {
			throw new AppException(ErrorCode.QUEUE_IS_NOT_ACTIVE, queueName, status.toString());
		}
		queueMap.get(queue.getId()).pause();
		queue.setStatus(Status.PAUSED);
		queueRepository.save(queue);
	}
}
