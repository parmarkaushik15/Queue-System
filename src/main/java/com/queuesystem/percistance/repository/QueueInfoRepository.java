package com.queuesystem.percistance.repository;

import com.queuesystem.percistance.model.QueueInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueInfoRepository extends JpaRepository<QueueInfo, Long> {

}
