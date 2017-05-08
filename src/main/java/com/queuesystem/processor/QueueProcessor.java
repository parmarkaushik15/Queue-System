package com.queuesystem.processor;

import com.queuesystem.exception.AppException;
import com.queuesystem.exception.ErrorCode;
import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.model.QueueMemberStatus;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.percistance.repository.QueueMemberRepository;
import com.queuesystem.service.LogManager;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueProcessor {

	private LogManager logManager;

	private QueueMemberRepository queueMemberRepository;

	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

	private final Queue queue;

	private int maxNumber = 0;

	public QueueProcessor(Queue queue, LogManager logManager, QueueMemberRepository queueMemberRepository) {
		this.queue = queue;
		this.logManager = logManager;
		this.queueMemberRepository = queueMemberRepository;
	}

	public void start() {
		QueueConsumer consumer = new QueueConsumer(queue, logManager, queueMemberRepository);
		Thread thread = new Thread(consumer);
		thread.start();
	}

	public void addMember(String queueMemberName) throws AppException {
		if (queue.getStatus() != Status.ACTIVE) {
			throw new AppException(ErrorCode.QUEUE_IS_NOT_ACTIVE);
		}
		synchronized (queue) {
			QueueMember queueMember = createQueueMember(queueMemberName);

			if (queueMemberRepository.countQueueMemberByStatus(QueueMemberStatus.IN_QUEUE) == 0) {
				queueMemberRepository.save(queueMember);
				queue.notify();
			} else {
				queueMemberRepository.save(queueMember);
			}
			log.info("Added new Queue Member -  name: [ " + queueMember.getName() + " ];" + "  Queue Number " + queueMember.getQueueNumber());
		}
	}

	private QueueMember createQueueMember(String queueMemberName) {
		QueueMember queueMember = new QueueMember();
		queueMember.setName(queueMemberName);
		queueMember.setQueue(queue);
		queueMember.setStatus(QueueMemberStatus.IN_QUEUE);
		queueMember.setQueueUpTime(new Date());
		queueMember.setQueueNumber(maxNumber++);
		return queueMember;
	}

	public void pause() {
		queue.setStatus(Status.INACTIVE);
	}

	public void stop() {
		queue.setStatus(Status.CANCELLED);
	}
}
