package com.data_processing.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.data_processing.app.model.Task;

@Repository
public class DataDAO {

	private static List<Task> taskList = new ArrayList<>();
	private static List<Task> submittedTaskList = new ArrayList<>();
	private static List<Task> cancelledTaskList = new ArrayList<>();

	public List<Task> getSubmittedTaskList() {
		return submittedTaskList;
	}

	public List<Task> getCancelledTaskList() {
		return cancelledTaskList;
	}

	public void addTask(List<Task> taskListNew) {
		taskList.addAll(taskListNew);
	}

	public List<Task> getTask() {
		return taskList;
	}

}
