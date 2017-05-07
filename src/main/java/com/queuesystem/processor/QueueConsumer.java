package com.queuesystem.processor;

import com.queuesystem.percistance.model.ActionType;
import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.service.LogManager;
import com.queuesystem.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;

public class QueueConsumer implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

	private LogManager logManager;

	private final LinkedBlockingQueue blockingQueue;
	private Queue queue;

	QueueConsumer(LinkedBlockingQueue blockingQueue, Queue queue, LogManager logManager) {
		this.blockingQueue = blockingQueue;
		this.queue = queue;
		this.logManager = logManager;
	}

	@Override
	public void run() {
		synchronized (blockingQueue) {
			try {
				while (true) {
					if (!blockingQueue.isEmpty() && queue.getStatus() == Status.ACTIVE) {
						Object memberWrapper = blockingQueue.poll();
						QueueMember member = (QueueMember) memberWrapper;
						logManager.logAction(ActionType.QUEUE_DELETE, queue, member);
						log.info("Consumed: [" + member.getName() + "],  Queue Number: [" + member.getQueueNumber() + "]");
						Thread.sleep(Utils.QUEUE_INTERVAL);
					} else if (queue.getStatus() == Status.CANCELLED) {
						break;
					} else {
						blockingQueue.wait();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}