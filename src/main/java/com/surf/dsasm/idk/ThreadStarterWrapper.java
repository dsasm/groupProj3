package com.surf.dsasm.idk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
public class ThreadStarterWrapper {
	
	@Autowired ThreadStarter threadStarter;
	
	
	public void start() {
		threadStarter.start();
	}
}
