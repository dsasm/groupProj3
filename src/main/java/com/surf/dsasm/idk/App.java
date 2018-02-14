package com.surf.dsasm.idk;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Hello world!
 *
 */
@EnableScheduling
@SpringBootApplication
public class App 
{
	@Value("${READ_ME}")
	private String readMe;
	
	@PostConstruct
	public void init() {
		runInit();
	}
	
	// tick every 3 seconds
	@Scheduled(fixedRate = 3000)
	public void schedule() {
	  checkStillHere();
	}
	
	public static void main( String[] args )
	{
	    SpringApplication.run(App.class);
	}
	
	public void checkStillHere() {
		System.out.println("you there m8?");
	}
	
	public void runInit() {
		System.out.println("HelloWorld");
	}
}
