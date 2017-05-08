package com.queuesystem.percistance.repository;

import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {

	Queue findByName(String name);

	List<Queue> findAllByStatusOrStatus(Status status1,Status status2);
}
