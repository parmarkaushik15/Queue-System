package com.queuesystem.controller;

import com.queuesystem.exception.AppException;
import com.queuesystem.service.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

	@RequestMapping(value = "/getAllQueue", method = RequestMethod.GET, produces = { "application/json" })
	public Response getAllQueue() {
		try {
			return Response.getSuccessResponse(queueManager.getAllQueue());
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}

	@RequestMapping(value = "/addqueue", method = RequestMethod.POST, produces = { "application/json" })
	public Response addQueue(@RequestBody String name) {

		System.out.println(name);
		try {
			return Response.getSuccessResponse(queueManager.addQueueAndStart(name));
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json" })
	public Response deleteQueue(@RequestBody String name, @RequestBody String newName) {
		try {
			return Response.getSuccessResponse(queueManager.updateQueue(name, newName));
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}

	@RequestMapping(value = "/deletequeue", method = RequestMethod.POST, produces = { "application/json" })
	public Response deleteQueue(@RequestBody String name) {
		try {
			return Response.getSuccessResponse(queueManager.deleteQueue(name));
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}

	@RequestMapping(value = "/stop", method = RequestMethod.POST, produces = { "application/json" })
	public Response stopQueue(@RequestBody String name) {
		try {
			return Response.getSuccessResponse(queueManager.stopQueue(name));
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}

	@RequestMapping(value = "/pause", method = RequestMethod.POST, produces = { "application/json" })
	public Response pauseQueue(@RequestBody String name) {
		try {
			return Response.getSuccessResponse(queueManager.pauseQueue(name));
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}

	@RequestMapping(value = "/resume", method = RequestMethod.POST, produces = { "application/json" })
	public Response resumeQueue(@RequestBody String name) {
		try {
			return Response.getSuccessResponse(queueManager.resumeQueue(name));
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}

	@RequestMapping(value = "/addmember", method = RequestMethod.GET, produces = { "application/json" })
	public Response addMemberInSpecificQueue(@RequestParam(value = "name") String name, @RequestParam(value = "queuename") String queueName) {
		try {
			return Response.getSuccessResponse(queueManager.addMember(name, queueName));
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}

	@RequestMapping(value = "/addMemberAnywhere", method = RequestMethod.GET, produces = { "application/json" })
	public Response addMember(@RequestParam(value = "name") String name) {
		try {
			return Response.getSuccessResponse(queueManager.addMember(name));
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}

	@RequestMapping(value = "/removemember", method = RequestMethod.GET, produces = { "application/json" })
	public Response removeMember(@RequestParam(value = "queuenumber") long queueNumber, @RequestParam(value = "queuename") String queueName) {
		try {
			return Response.getSuccessResponse(queueManager.removeMember(queueNumber, queueName));
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}

	@RequestMapping(value = "/me", method = RequestMethod.GET, produces = { "application/json" })
	public Response peopleBeforeMe(@RequestParam(value = "queuenumber") long queueNumber, @RequestParam(value = "queuename") String queueName) {
		try {
			return Response.getSuccessResponse(queueManager.getMyInfo(queueNumber, queueName));
		} catch (AppException e) {
			return Response.getFailResponse(e.getErrorText());
		}
	}
}

