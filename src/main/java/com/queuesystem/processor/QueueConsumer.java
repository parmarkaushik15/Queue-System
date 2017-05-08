package com.queuesystem.processor;

import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.model.QueueMemberStatus;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.percistance.repository.QueueMemberRepository;
import com.queuesystem.service.LogManager;
import com.queuesystem.utils.Utils;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueConsumer implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

	private LogManager logManager;

	private QueueMemberRepository queueMemberRepository;

	private final Queue queue;

	QueueConsumer(Queue queue, LogManager logManager, QueueMemberRepository queueMemberRepository) {
		this.queue = queue;
		this.logManager = logManager;
		this.queueMemberRepository = queueMemberRepository;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(Utils.QUEUE_INTERVAL);
				synchronized (queue) {
					QueueMember member = queueMemberRepository.getFirstByStatusOrderByIdAsc(QueueMemberStatus.IN_QUEUE);
					if (queue.getStatus() == Status.ACTIVE && member != null) {

						member.setStatus(QueueMemberStatus.OUT_OF_QUEUE);
						member.setQueueLeaveTime(new Date());
						queueMemberRepository.save(member);
						log.info("Consumed: [" + member.getName() + "],  Queue Number: [" + member.getQueueNumber() + "]  BY QUEUE: [" + queue.getName() + "]");
					} else if (queue.getStatus() == Status.CANCELLED) {
						break;
					} else {
						queue.wait();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}