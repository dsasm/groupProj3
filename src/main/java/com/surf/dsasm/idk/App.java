package com.surf.dsasm.idk;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Hello world!
 *
 */
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class App 
{
	public static boolean test = true;
	@Autowired private ThreadStarter threadStarter;
	
	@PostConstruct
	public void init() {
		threadStarter.start();
	}
	public static void main( String[] args )
	{
	    SpringApplication.run(App.class);
	    
	}
	
}
