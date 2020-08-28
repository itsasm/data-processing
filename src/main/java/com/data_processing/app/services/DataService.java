package com.data_processing.app.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data_processing.app.dao.DataDAO;
import com.data_processing.app.execution.DataExecutor;
import com.data_processing.app.model.Task;

@Service
public class DataService {

	@Autowired
	private DataDAO dao;

	@Autowired
	private DataExecutor executor;

	public String startProcessing(List<Task> taskList) {
		executor.invalidateRequest();
		dao.addTask(taskList);
		try {
			executor.startDataProcessing();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return "Data Processed Successfully.";
	}

	public List<String> stopProcessing(List<Task> cancelList) {
		return executor.stopDataProcessing(cancelList);
	}

	public List<Task> getSubmiitedTask() {
		return dao.getSubmittedTaskList();
	}

	public List<Task> getCancelledTask() {
		return dao.getCancelledTaskList();
	}
}
