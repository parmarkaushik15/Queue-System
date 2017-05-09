package com.queuesystem.processor;

import com.queuesystem.exception.AppException;
import com.queuesystem.exception.ErrorCode;
import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.model.QueueMemberStatus;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.percistance.repository.QueueMemberRepository;
import com.queuesystem.percistance.repository.QueueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class QueueProcessor {

	private QueueMemberRepository queueMemberRepository;

	private QueueRepository queueRepository;

	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

	private final Queue queue;

	public QueueProcessor(Queue queue, QueueMemberRepository queueMemberRepository, QueueRepository queueRepository) {
		this.queue = queue;
		this.queueMemberRepository = queueMemberRepository;
		this.queueRepository = queueRepository;
	}

	public void start() {
		QueueConsumer consumer = new QueueConsumer(queue, queueMemberRepository,queueRepository);
		Thread thread = new Thread(consumer);
		thread.start();
	}

	public void addMember(String queueMemberName) throws AppException {
		if (queue.getStatus() != Status.ACTIVE) {
			throw new AppException(ErrorCode.QUEUE_IS_NOT_ACTIVE, queue.getName());
		}
		QueueMember queueMember = createQueueMember(queueMemberName);

		if (queueMemberRepository.countQueueMemberByStatusAndQueue(QueueMemberStatus.IN_QUEUE, queue) == 0) {
			queueMemberRepository.save(queueMember);
			log.info("Awaking queue " + queue.getName());
			queue.awake();
		} else {
			queueMemberRepository.save(queueMember);
		}
		log.info("Added new Queue Member -  name: [ " + queueMember.getName() + " ];" + "  Queue Number " + queueMember.getQueueNumber() + " INTO QUEUE:  [ " + queue.getName() + " ]");
	}

	private QueueMember createQueueMember(String queueMemberName) {
		QueueMember queueMember = new QueueMember();
		queueMember.setName(queueMemberName);
		queueMember.setQueue(queue);
		queueMember.setStatus(QueueMemberStatus.IN_QUEUE);
		queueMember.setQueueUpTime(new Date());
		queueMember.setQueueNumber(queue.getNextIndex());
		queueRepository.save(queue);
		return queueMember;
	}

	public void pause() {
		queue.setStatus(Status.PAUSED);
	}

	public void stop() {
		queue.setStatus(Status.CANCELLED);
	}
}
