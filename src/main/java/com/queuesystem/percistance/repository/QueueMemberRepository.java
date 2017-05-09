package com.queuesystem.percistance.repository;

import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.model.QueueMemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueMemberRepository extends JpaRepository<QueueMember, Long> {
	Queue findByName(String name);

	int countQueueMemberByStatusAndQueue(QueueMemberStatus status, Queue queue);

	QueueMember getFirstByStatusAndQueueOrderByIdAsc(QueueMemberStatus status,Queue queue);
}
