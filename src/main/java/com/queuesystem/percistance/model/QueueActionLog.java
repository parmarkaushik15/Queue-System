package com.queuesystem.percistance.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class QueueActionLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(cascade = CascadeType.ALL)
	QueueMember queueMember;

	@Enumerated(EnumType.STRING)
	ActionType actionType;

	@ManyToOne
	QueueInfo queueInfo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	@PrePersist
	private void postPersist() {
		time = new Date();
	}
}
