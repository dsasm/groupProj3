package com.surf.dsasm.idk;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class App 
{
	public static boolean allMode = true;
	public static Mode mode = Mode.BURST;
	public static boolean finishedRun = true;
	public static int speed = 5;
	
	Logger LOGGER = LoggerFactory.getLogger(App.class);
	
	@Autowired
	private ThreadStarterWrapper threadStarterWrapper ;
	
	@Autowired private DataGatherer dataGatherer;
	
	@PostConstruct
	public void init() throws InterruptedException {
		if (allMode) {
			for (Mode currMode : EnumUtils.getEnumList(Mode.class)) {
				mode = currMode;
				finishedRun = false;
				mode = currMode;
				LOGGER.info("Starting with Mode "+currMode);
				threadStarterWrapper.start();
				while(!finishedRun) {
					
				}
				LOGGER.info("Moving on to new Mode");
				LOGGER.info("Moving on to new Mode");
				LOGGER.info("Moving on to new Mode");
				LOGGER.info("Moving on to new Mode");
			}
		}
		else threadStarterWrapper.start();
	}
	public static void main( String[] args )
	{
	    SpringApplication.run(App.class);
	    
	}
	
}
