package com.surf.dsasm.idk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Holders.ThreadCoinHolders;
import MetricApplier.MetricApplier;
import buySell.ShouldBuy;
import buySell.ShouldSell;

@Component
public class ThreadStarter {
	
	//Autowires aren't constructed ON PURPOSE
	@Autowired private MetricApplier metricApplier;
	@Autowired private ShouldBuy [] shouldBuy = new ShouldBuy[2];
	@Autowired private ShouldSell [] shouldSell = new ShouldSell[2];
	@Autowired private ThreadCoinHolders threadCoinHolder;
	
	Logger logger = LoggerFactory.getLogger(ThreadStarter.class);
	
	
	public void start() {
		logger.info("Starting up the threads");
		metricApplier.execute();
		logger.info("Executed Metric Applier");
		for (int i = 0 ; i < shouldBuy.length; i++) {
			shouldBuy[i].setThreadIndex(i);
			logger.info("Set up new Buyer");
			try {
				shouldBuy[i].execute();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("Failed to set up Seller");
			}
		}
		
		for (int i = 0 ; i < shouldSell.length; i++) {
			
			shouldSell[i].setThreadIndex(i);
			try {
				shouldSell[i].execute();
				logger.info("Set up new Seller");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("Failed to set up Seller");
			}
		}
		
		threadCoinHolder.execute();

		logger.info("Executed ThreadCoinHolder");
	}
	
}
