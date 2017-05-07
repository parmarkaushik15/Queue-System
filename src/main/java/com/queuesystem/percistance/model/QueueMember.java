package com.queuesystem.percistance.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class QueueMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private int queueNumber;

	@ManyToOne
	private Queue queue;
}
