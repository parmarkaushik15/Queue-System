package com.queuesystem.service;

import com.queuesystem.percistance.model.ActionType;
import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.QueueActionLog;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.repository.ActionLogRepository;
import com.queuesystem.percistance.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogManager {

	@Autowired
	private ActionLogRepository logRepository;

	@Autowired
	private QueueRepository queueRepository;

	public void logAction(ActionType actionType, Queue queue, QueueMember queueMember) {
		QueueActionLog actionLog = new QueueActionLog();
		actionLog.setQueueMember(queueMember);
		actionLog.setQueue(queue);
		logRepository.save(actionLog);
	}

	public void logNewQueue(Queue info) {
		queueRepository.save(info);
	}
}
