package com.data_processing.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.data_processing.app.model.Task;
import com.data_processing.app.services.DataService;

@RestController
@RequestMapping("/data")
public class DataController {

	@Autowired
	private DataService service;

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public String startProcessing(@RequestBody List<Task> taskList) {
		return service.startProcessing(taskList);
	}

	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	public List<String> stopProcessing(@RequestBody List<Task> cancelList) {
		return service.stopProcessing(cancelList);
	}

	@RequestMapping(value = "/get/submitted", method = RequestMethod.GET)
	public List<Task> getSubmiitedTask() {
		return service.getSubmiitedTask();
	}

	@RequestMapping(value = "/get/cancelled", method = RequestMethod.GET)
	public List<Task> getCancelledTask() {
		return service.getCancelledTask();
	}

}
