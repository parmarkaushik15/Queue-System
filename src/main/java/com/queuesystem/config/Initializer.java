package com.queuesystem.config;

import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.Status;
import com.queuesystem.percistance.repository.QueueMemberRepository;
import com.queuesystem.percistance.repository.QueueRepository;
import com.queuesystem.processor.QueueProcessor;
import com.queuesystem.service.LogManager;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("singleton")
@Component
public class Initializer {

	private static final Logger log = LoggerFactory.getLogger(Initializer.class);

	private LogManager logManager;

	private QueueRepository queueRepository;

	private QueueMemberRepository queueMemberRepository;

	private final HashMap<Long, QueueProcessor> queueMap;

	@Autowired
	public Initializer(LogManager logManager, QueueRepository queueRepository, QueueMemberRepository queueMemberRepository, HashMap<Long, QueueProcessor> queueMap) {
		this.logManager = logManager;
		this.queueRepository = queueRepository;
		this.queueMemberRepository = queueMemberRepository;
		this.queueMap = queueMap;
	}

	@PostConstruct
	public void init() {
		log.info("-------------- Start of Initializing -----------------------");
		System.out.println(queueRepository.findAllByStatusOrStatus(Status.ACTIVE, Status.INACTIVE));
		List<Queue> queueList = queueRepository.findAllByStatusOrStatus(Status.ACTIVE, Status.INACTIVE);

		queueList.forEach(queue -> {
			QueueProcessor queueProcessor = new QueueProcessor(queue, logManager, queueMemberRepository);
			queueMap.put(queue.getId(), queueProcessor);
			if (queue.getStatus() == Status.ACTIVE) {
				queueProcessor.start();
			}
			log.info(queue.getName() + "  was initalized  [" + queue + " ]");
		});

	}
}
