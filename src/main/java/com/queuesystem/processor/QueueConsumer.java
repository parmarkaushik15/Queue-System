package com.queuesystem.processor;

import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.utils.Utils;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueConsumer implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

	BlockingQueue<QueueMember> queue;

	public QueueConsumer(BlockingQueue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				if (!queue.isEmpty()) {
					QueueMember member = queue.remove();
					log.info("Consumed: [" + member.getName() + "],  Queue Number: [" + member.getQueueNumber() + "]");
					Thread.sleep(Utils.QUEUE_INTERVAL);
				} else {
					queue.wait();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
