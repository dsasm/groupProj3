package com.surf.dsasm.idk;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import MetricApplier.MetricApplier;
import ThreadCoinHolders.ThreadCoinHolders;
import buySell.BuyConsiderer;
import buySell.Buyer;
import buySell.SellConsiderer;
import buySell.Seller;
import buySell.ShouldBuy;
import buySell.ShouldSell;

@Component
public class ThreadStarter {
	
	//Autowires aren't constructed ON PURPOSE
	@Autowired private MetricApplier metricApplier;
	private ShouldBuy[] shouldBuy = new ShouldBuy[2];
	private ShouldSell[] shouldSell = new ShouldSell[2];
	@Autowired private ThreadCoinHolders threadCoinHolder;
	@Autowired SellConsiderer sellConsiderer;
	@Autowired BuyConsiderer buyConsiderer;
	@Autowired Buyer buyer;
	@Autowired Seller seller;
	Logger logger = LoggerFactory.getLogger(ThreadStarter.class);
	
	
	public void start() {
		List<Runnable> tasks = new LinkedList<Runnable>();
		ExecutorService es = Executors.newFixedThreadPool(6);
		
		logger.info("Starting up the threads");
		es.execute(metricApplier);
		logger.info("Executed Metric Applier");
		for (int i = 0 ; i < 2; i++) {
			shouldSell[i] = new ShouldSell(sellConsiderer, buyer, seller);
			shouldSell[i].setThreadIndex(i);
			Thread sellThread = new Thread(shouldSell[i]);
			tasks.add(sellThread);
			es.execute(sellThread);
			logger.info("Set up new Seller");
		}
		for (int i = 0 ; i < 2; i++) {
			shouldBuy[i] = new ShouldBuy(buyConsiderer);
			shouldBuy[i].setThreadIndex(i);
			Thread buyThread = new Thread(shouldBuy[i]);
			tasks.add(buyThread);
			es.execute(buyThread);
			logger.info("Set up new Buyer");
		}
		
		
		Thread coinHolderThread = new Thread(threadCoinHolder);
		tasks.add(coinHolderThread);
		es.execute(coinHolderThread);
		logger.info("Executed ThreadCoinHolder");
		
	}
	
}
