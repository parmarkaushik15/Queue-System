package com.queuesystem.service;

import com.queuesystem.percistance.model.ActionType;
import com.queuesystem.percistance.model.QueueActionLog;
import com.queuesystem.percistance.model.QueueInfo;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.repository.ActionLogRepository;
import com.queuesystem.percistance.repository.QueueInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogManager {

	@Autowired
	private ActionLogRepository logRepository;

	@Autowired
	private QueueInfoRepository queueInfoRepository;

	public void logAction(ActionType actionType, QueueInfo queueInfo, QueueMember queueMember) {
		QueueActionLog actionLog = new QueueActionLog();
		actionLog.setQueueMember(queueMember);
		actionLog.setQueueInfo(queueInfo);
		logRepository.save(actionLog);
	}

	public void logNewQueue(QueueInfo info) {
		queueInfoRepository.save(info);
	}
}
