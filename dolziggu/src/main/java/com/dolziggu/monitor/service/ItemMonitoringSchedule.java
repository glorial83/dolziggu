package com.dolziggu.monitor.service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemMonitoringSchedule extends HttpServlet {
	
	private static Logger _log = LoggerFactory.getLogger(ItemMonitoringSchedule.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig cfg) throws ServletException {
		// TODO Auto-generated method stub
		super.init(cfg);
		
		_log.info("quartz init ----");

		try {
			// 실행할 준비가 된 Scheduler 인스턴스를 리턴한다.
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			
			// 쿼츠에서 Scheduler는 시작, 정지, 일시정지할 수 있다.
			// Scheduler가 시작되지 않았거나 일시 정지상태이면 Trigger를 발동시키지 않으므로 start()해준다.
			scheduler.start();
			
			JobDetail job = JobBuilder.newJob(ItemMonitoringAmazon.class).withIdentity("job1", "group1").build();
			
			// 40초마다 생성되는 트리거를 생성한다.
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").withSchedule(CronScheduleBuilder.cronSchedule("0/40 * * * * ?")).build();

			// 실행
			scheduler.scheduleJob(job, trigger);
			
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
}
