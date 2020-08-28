package com.data_processing.app.model;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.data_processing.app.execution.DataExecutor;

public class Task implements Callable<Task> {

	private static Logger logger = LoggerFactory.getLogger(DataExecutor.class);
	private int id;
	private String name;
	private String desc;
	private boolean cancelled;

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public Task() {
	}

	@Override
	public Task call() throws Exception {
		logger.info(name + " in progess... at " + LocalDateTime.now().toString());
		Thread.sleep(2000);
		return this;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (this.getClass() != obj.getClass())
			return false;

		Task task = (Task) obj;
		if (this.id == task.id && this.name.equals(task.name))
			return true;

		return false;
	}

}
