package com.queuesystem.processor;

import com.queuesystem.percistance.model.ActionType;
import com.queuesystem.percistance.model.QueueInfo;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.service.LogManager;
import com.queuesystem.utils.Utils;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

	@Autowired
	private LogManager logManager;

	BlockingQueue<QueueMember> queue;

	private QueueInfo queueInfo;

	public QueueProducer(BlockingQueue queue, QueueInfo queueInfo) {
		this.queue = queue;
		this.queueInfo = queueInfo;
	}

	@Override
	public void run() {
		try {
			while (true) {
				QueueMember member = queue.remove();
				log.info("Consumed: [" + member.getName() + "],  Queue Number: [" + member.getQueueNumber() + "]");
				logManager.logAction(ActionType.QUEUE_LEAVING, queueInfo, member);
				Thread.sleep(Utils.QUEUE_INTERVAL);
				queue.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
