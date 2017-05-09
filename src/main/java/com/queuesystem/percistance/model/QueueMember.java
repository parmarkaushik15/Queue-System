package com.queuesystem.percistance.model;

import java.util.Date;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class QueueMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private long queueNumber;

	@Enumerated(EnumType.STRING)
	private QueueMemberStatus status;

	@ManyToOne
	private Queue queue;

	@Temporal(TemporalType.TIMESTAMP)
	private Date queueUpTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date queueLeaveTime;
}
