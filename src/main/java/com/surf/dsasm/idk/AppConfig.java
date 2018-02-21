package com.surf.dsasm.idk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import com.surf.dsasm.Rework.client.RestClientInteractor;

import Holders.ThreadCoinHolders;
import MetricApplier.MetricApplier;
import MetricApplier.MovingAverageApplier;
import MovingAverage.MovingAverageDifferenceCalculator;
import MovingAverage.MovingAverageFlatPercentageDifferenceCalculator;
import buySell.FakeBuyer;
import buySell.FakeSeller;
import buySell.ShouldBuy;
import buySell.ShouldSell;
import considerer.MovingAverageBuyConsiderer;
import considerer.PercentageHighestProfitSellConsiderer;

@Configuration
@ComponentScan(basePackages={"com.surf.dsasm.Rework.client", "buySell","considerer"})
public class AppConfig {

	
	@Bean
	public MetricApplier metricApplier(RestClientInteractor clientInteractor , MovingAverageDifferenceCalculator MADiffCalc) {
		if (App.test) return new MovingAverageApplier(clientInteractor, MADiffCalc);
		else return null;
	}
	
	@Bean
	public MovingAverageDifferenceCalculator movingAverageDifferenceCalculator(RestClientInteractor clientInteractor) {
		if (App.test) return new MovingAverageFlatPercentageDifferenceCalculator(clientInteractor);
		else return null;
	}
	
	@Bean
	public ShouldBuy shouldBuy(MovingAverageBuyConsiderer buyConsiderer) {
		if (App.test) return new ShouldBuy(buyConsiderer);
		else return null;
	}
	
	@Bean
	public ShouldSell shouldSell(FakeBuyer buyer, FakeSeller seller, PercentageHighestProfitSellConsiderer sellConsiderer) {
		if (App.test) return new ShouldSell(sellConsiderer, buyer, seller);
		else return null;
	}
	
	@Bean
	public ThreadCoinHolders threadCoinHolders() {
		if (App.test) return new ThreadCoinHolders();
		else return null;
	}
	
}
