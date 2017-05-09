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
	private QueueManager queueManager;

	@Autowired
	public QueueController(QueueManager queueManager) {
		this.queueManager = queueManager;
	}

	@RequestMapping(value = "/addqueue", method = RequestMethod.GET)
	public String addQueue(@RequestParam(value = "name") String name) {
		try {
			queueManager.addQueue(name);
			queueManager.startQueue(name);
			return "Queue Added and started Successfully";
		} catch (AppException e) {
			return e.getErrorText();
		}
	}

	@RequestMapping(value = "/stopqueue", method = RequestMethod.GET)
	public String stopQueue(@RequestParam(value = "name") String name) {
		try {
			queueManager.stopQueue(name);
			return "Queue Stopped Sucessfully";
		} catch (AppException e) {
			return e.getErrorText();
		}
	}

	@RequestMapping(value = "/cur", method = RequestMethod.GET)
	public void threads() {
		System.out.println(Thread.activeCount());
	}

	@RequestMapping(value = "/addmember", method = RequestMethod.GET)
	public String addMember(@RequestParam(value = "name") String name, @RequestParam(value = "queuename") String queueName) {
		try {
			queueManager.addMember(name, queueName);
			return name + " added in Queue: " + queueName + " successfully";
		} catch (AppException e) {
			return e.getErrorText();
		}
	}

	@RequestMapping(value = "/getqueuemember", method = RequestMethod.GET)
	public void getQueueNumber(@RequestParam(value = "name") String name, @RequestParam(value = "queuename") String queueName) throws AppException {
		queueManager.getNumber(name, queueName);
	}
}
