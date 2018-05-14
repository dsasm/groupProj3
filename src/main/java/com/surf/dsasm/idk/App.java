package com.surf.dsasm.idk;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.EnumUtils;
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
	public static Mode mode = Mode.ALL;
	public static boolean finishedRun = true;
	@Autowired private ThreadStarter threadStarter;
	
	@Autowired private DataGatherer dataGatherer;
	
	@PostConstruct
	public void init() throws InterruptedException {
		if (App.mode.equals(Mode.ALL)) {
			for (Mode currMode : EnumUtils.getEnumList(Mode.class)) {
				finishedRun = false;
				mode = currMode;
				threadStarter.start();
				while(!finishedRun) {
					
				}
			}
		}
		else threadStarter.start();
	}
	public static void main( String[] args )
	{
	    SpringApplication.run(App.class);
	    
	}
	
}
