package com.queuesystem.processor;

import com.queuesystem.exception.AppException;
import com.queuesystem.exception.ErrorCode;
import com.queuesystem.percistance.model.ActionType;
import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.service.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

public class QueueProcessor {

	private LogManager logManager;

	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

	private LinkedBlockingQueue<QueueMember> blockingQueue = new LinkedBlockingQueue<>();

	private Queue queue;

	private int maxNumber = 0;

	public QueueProcessor(Queue queue, LogManager logManager) {
		this.queue = queue;
		this.logManager = logManager;
	}

	public void start() {
		QueueConsumer consumer = new QueueConsumer(blockingQueue, queue,logManager);
		Thread thread = new Thread(consumer);
		thread.start();
	}

	public void addMember(String queueMemberName) throws AppException {
		if (queue.getStatus() != Status.ACTIVE) {
			throw new AppException(ErrorCode.QUEUE_IS_NOT_ACTIVE);
		}
		QueueMember queueMember = createQueueMember(queueMemberName);
		if (blockingQueue.isEmpty()) {
			blockingQueue.add(queueMember);
			blockingQueue.notify();
		} else {
			blockingQueue.add(queueMember);
		}
		logManager.logAction(ActionType.QUEUE_UP, queue, queueMember);
		log.info("Added new Queue Member -  name: [ " + queueMember.getName() + " ];" + "  Queue Number " + queueMember.getQueueNumber());
	}


	private QueueMember createQueueMember(String queueMemberName) {
		QueueMember queueMember = new QueueMember();
		queueMember.setName(queueMemberName);
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
