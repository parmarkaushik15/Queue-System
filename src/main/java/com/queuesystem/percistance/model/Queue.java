package com.queuesystem.percistance.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Queue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private String name;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	@PrePersist
	private void postPersist() {
		startTime = new Date();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Queue queue = (Queue) o;

		return id == queue.id && (name != null ? name.equals(queue.name) : queue.name == null);
	}

	@Override
	public int hashCode() {

		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + name.hashCode();
		return result;
	}
}
