package com.queuesystem.percistance.repository;

import com.queuesystem.percistance.model.Queue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends JpaRepository<Queue, Long> {
	Queue findByName(String name);
}
