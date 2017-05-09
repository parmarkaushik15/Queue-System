package com.queuesystem.percistance.repository;

import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.QueueMember;
import com.queuesystem.percistance.model.QueueMemberStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueMemberRepository extends JpaRepository<QueueMember, Long> {

	int countQueueMemberByStatusAndQueue(QueueMemberStatus status, Queue queue);

	QueueMember getFirstByStatusAndQueueOrderByIdAsc(QueueMemberStatus status, Queue queue);

	QueueMember findByQueueNumberAndQueue(long queueNumber, Queue queue);

	int countByQueueAndStatusAndQueueNumberLessThan(Queue queue, QueueMemberStatus status, long queueNumber);

//	@Query(value = "SELECT new QueueMember(t.queue) from QueueMember t where t.status= :status" +
//			" group by t.queue order by count(t.id) asc")
//	QueueMember getQueueIdWithLowestActiveMembers(@Param(value = "status") QueueMemberStatus status, Pageable pageable);
}
