package com.queuesystem.percistance.repository;

import com.queuesystem.percistance.model.Queue;
import com.queuesystem.percistance.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {

	Queue findByName(String name);

	List<Queue> findAllByStatusOrStatus(Status status1, Status status2);

	int countByName(String name);
}
