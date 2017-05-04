package com.queuesystem.controller;

import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queue")
public class QueueController {

	@RequestMapping(value = "/add", method = RequestMethod.GET, produces = { "application/json" })
	public void addQueue() {
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

	}
}
