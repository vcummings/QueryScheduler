package com.budco.appReports.job.scheduler;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.budco.appReports.job.runner.MainRunner;
import com.budco.appReports.util.EmailUtil;

public class MainJob implements Job {
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		//		SchedulerContext schedulerContext = null;
//		try {
//			schedulerContext = context.getScheduler().getContext();
//		} catch (SchedulerException e1) {
//			e1.printStackTrace();
//		}
//		Connection sourceConn = (Connection) schedulerContext.get("connection");
		try {
			MainRunner.runReports();
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			EmailUtil.sendError( sw.toString());
			e.printStackTrace();
			System.exit(0);
		}
	}
}
