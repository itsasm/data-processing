package com.data_processing.app.execution;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.data_processing.app.dao.DataDAO;
import com.data_processing.app.model.Task;

@Component
public class DataExecutor {

	@Autowired
	private DataDAO dao;

	private static Logger logger = LoggerFactory.getLogger(DataExecutor.class);

	static ExecutorService executor;

	public void invalidateRequest() {
		if (dao.getTask() != null && !dao.getTask().isEmpty()) {
			dao.getTask().clear();

			if (!executor.isShutdown())
				executor.shutdownNow();
		}
	}

	public void startDataProcessing() throws InterruptedException, ExecutionException {

		executor = Executors.newCachedThreadPool();

		for (Task task : dao.getTask()) {

			Future<Task> future = null;
			if (!task.isCancelled()) {
				future = executor.submit(task);
			} else {
				dao.getCancelledTaskList().add(task);
				logger.info(task.getName() + " has been cancelled");
				continue;
			}

			Task cancelled = null;
			while (!future.isDone()) {
				if (task.isCancelled()) {
					cancelled = future.get();
					future.cancel(true);
				}
			}

			if (future.isCancelled()) {
				dao.getCancelledTaskList().add(cancelled);
				logger.info("Task ID...... : " + future.get().getId() + ", isCancelled : " + future.get().isCancelled()
						+ ", f: " + future.isCancelled());
			}

			if (future.isDone()) {
				logger.info("Task " + future.get().getName() + " {" + future.get().getDesc() + "} finished......");
				dao.getSubmittedTaskList().add(future.get());
			}
		}

		executor.shutdown();
	}

	public List<String> stopDataProcessing(List<Task> cancelList) {
		List<String> reply = new ArrayList<>();
		if (dao.getTask().isEmpty()) {
			reply.add("No Running Task Found...");
			return reply;
		}

		if (executor.isShutdown()) {
			reply.add("Task already been completed.");
			return reply;
		}

		for (Task task : cancelList) {
			try {
				int index = dao.getTask().indexOf(task);
				if (index >= 0) {
					dao.getTask().get(index).setCancelled(true);
					logger.info(task.getName()+" set to cancel.");
					Thread.sleep(10);
				}
				reply.add("Task Set To Cancel : " + task.getName());
			} catch (ConcurrentModificationException | InterruptedException e) {
				logger.error("Error Occurred : " + e.getMessage());
				reply.add("Task Could Not Be Cancelled : " + task.getName());
			}
		}

		return reply;
	}

}
