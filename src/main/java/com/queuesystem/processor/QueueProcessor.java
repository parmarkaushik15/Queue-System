package com.queuesystem.processor;

import com.queuesystem.percistance.model.ActionType;
import com.queuesystem.percistance.model.QueueInfo;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.service.LogManager;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueProcessor {

	@Autowired
	private LogManager logManager;

	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

	private LinkedBlockingQueue<QueueMember> blockingQueue = new LinkedBlockingQueue<>();

	private QueueInfo queueInfo;

	private int maxNumber = 0;

	public QueueProcessor(QueueInfo queueInfo) {
		this.queueInfo = queueInfo;
	}

	public void start() {
		QueueProducer producer = new QueueProducer(blockingQueue, queueInfo);
		Thread thread = new Thread(producer);
		thread.start();
		queueInfo.setStatus (Status.ACTIVE);

	}

	public void addMember(String queueMemberName) {
		QueueMember queueMember = createQueueMember(queueMemberName);
		if (blockingQueue.isEmpty()) {
			blockingQueue.add(queueMember);
			blockingQueue.notify();
		} else {
			blockingQueue.add(queueMember);
		}
		logManager.logAction(ActionType.QUEUE_UP, queueInfo, queueMember);
		log.info("Added new Queue Member -  name: [ " + queueMember.getName() + " ];" + "  Queue Number " + queueMember.getQueueNumber());
	}


	private QueueMember createQueueMember(String queueMemberName) {
		QueueMember queueMember = new QueueMember();
		queueMember.setName(queueMemberName);
		queueMember.setQueueNumber(maxNumber++);
		return queueMember;
	}
}
