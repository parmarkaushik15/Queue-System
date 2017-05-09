package com.queuesystem.processor;

import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.model.QueueMemberStatus;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.percistance.repository.QueueMemberRepository;
import com.queuesystem.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class QueueConsumer implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

	private QueueMemberRepository queueMemberRepository;

	private final Queue queue;

	QueueConsumer(Queue queue, QueueMemberRepository queueMemberRepository) {
		this.queue = queue;
		this.queueMemberRepository = queueMemberRepository;
	}

	@Override
	public void run() {
		if (queue.getStatus() == Status.CANCELLED) {
			log.info("Queue not started " + queue.getName());
			return;
		}

		try {
			while (true) {
				Thread.sleep(Utils.QUEUE_INTERVAL);

				QueueMember member = queueMemberRepository.getFirstByStatusAndQueueOrderByIdAsc(QueueMemberStatus.IN_QUEUE, queue);
				if (queue.getStatus() == Status.ACTIVE && member != null) {
					member.setStatus(QueueMemberStatus.OUT_OF_QUEUE);
					member.setQueueLeaveTime(new Date());
					queueMemberRepository.save(member);
					log.info("Consumed: [" + member.getName() + "],  Queue Number: [" + member.getQueueNumber() + "]  BY QUEUE: [" + queue.getName() + "]");
				} else {
					log.info("Pause queue " + queue.getName());
					queue.pause();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}