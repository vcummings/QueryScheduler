package com.budco.appReports.job.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzScheduler {

//	final static String SOURCE_DATABASE_PROPERTIES = "C:\\QueryScheduler\\jobs\\cfg\\database.src.properties";

	public static void main(String[] args) throws Exception {
//		Connection sourceConn = DatabaseUtil.openConnection(SOURCE_DATABASE_PROPERTIES);

		try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//			scheduler.getContext().put("connection", sourceConn);

			// and start it off
			 scheduler.start();

			// define the job and tie it to our HelloJob class
			JobDetail job = newJob(MainJob.class)
					.withIdentity("job1", "group1")
					.build();

			// Trigger the job to run now, and then repeat every 60 seconds
			 Trigger trigger = newTrigger()
			 .withIdentity("trigger1", "group1")
			 .startNow()
			 .withSchedule(simpleSchedule()
			 .withIntervalInSeconds(60)
			 .repeatForever())
			 .build();

			// Trigger the job to run now, and then repeat every 40 seconds
//			Trigger trigger = newTrigger().withIdentity("trigger1", "group1")
//					.startNow().build();

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);

//			scheduler.start();

			// 60 second pause
//			while (true) {
//				try {
//					Thread.sleep(20L * 1000L);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//					DatabaseUtil.closeConnection(sourceConn);
//				}
//			}

		} catch (SchedulerException se) {
			se.printStackTrace();
//			DatabaseUtil.closeConnection(sourceConn);
		}
	}
}