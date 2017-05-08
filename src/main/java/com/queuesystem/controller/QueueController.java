package com.queuesystem.controller;

import com.queuesystem.exception.AppException;
import com.queuesystem.service.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queue")
public class QueueController {
	private final QueueManager queueManager;

	@Autowired
	public QueueController(QueueManager queueManager) {
		this.queueManager = queueManager;
	}

	@RequestMapping(value = "/addqueue", method = RequestMethod.GET)
	public void addQueue(@RequestParam(value = "name") String name) {
		queueManager.addQueue(name);
		queueManager.startQueue(name);
	}

	@RequestMapping(value = "/cur", method = RequestMethod.GET)
	public void threads() {
		System.out.println(Thread.activeCount());
	}

	@RequestMapping(value = "/addmember", method = RequestMethod.GET)
	public void addMember(@RequestParam(value = "name") String name, @RequestParam(value = "queuename") String queueName) throws AppException {
		queueManager.addMember(name, queueName);
	}

	@RequestMapping(value = "/addmember", method = RequestMethod.GET)
	public void getQueueNumber(@RequestParam(value = "name") String name, @RequestParam(value = "queuename") String queueName) throws AppException {
		queueManager.getNumber(name, queueName);
	}
}
