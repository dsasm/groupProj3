package com.surf.dsasm.idk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.BurstSearcher;
import MetricApplier.MetricApplier;
import MovingAverage.MovingAverageDifferenceCalculator;
import MovingAverage.MovingAverageFlatPercentageDifferenceCalculator;
import ThreadCoinHolders.ThreadCoinHolders;
import buySell.BurstBuyConsiderer;
import buySell.BurstSellConsiderer;
import buySell.BuyConsiderer;
import buySell.FakeBuyer;
import buySell.FakeSeller;
import buySell.SellConsiderer;
import buySell.ShouldBuy;
import buySell.ShouldSell;
import considerer.PercentageHighestProfitSellConsiderer;

@Configuration
@ComponentScan(basePackages={"com.surf.dsasm.Rework.client", "buySell","considerer"})
public class AppConfig {
	
	
	
	@Bean
	public MetricApplier metricApplier(RestClientInteractor clientInteractor ) {
		if (App.test) return new BurstSearcher(clientInteractor);
		else return null;
	}
	
	@Bean
	public MovingAverageDifferenceCalculator movingAverageDifferenceCalculator(RestClientInteractor clientInteractor) {
		if (App.test) return new MovingAverageFlatPercentageDifferenceCalculator(clientInteractor);
		else return null;
	}
	
	@Bean
	public ShouldBuy shouldBuy(BurstBuyConsiderer buyConsiderer) {
		if (App.test) return new ShouldBuy(buyConsiderer);
		else return null;
	}
	
	@Bean
	public BuyConsiderer buyConsiderer() {
		if (App.test) return new BurstBuyConsiderer();
		else return null;
	}
	
	@Bean
	public SellConsiderer sellConsiderer(RestClientInteractor clientInteractor) {
		if (App.test) return new BurstSellConsiderer(clientInteractor);
		else return null;
	}
	
	@Bean
	public ShouldSell shouldSell(FakeBuyer buyer, FakeSeller seller, BurstSellConsiderer sellConsiderer) {
		if (App.test) return new ShouldSell(sellConsiderer, buyer, seller);
		else return null;
	}
	
	@Bean
	public ThreadCoinHolders threadCoinHolders() {
		if (App.test) return new ThreadCoinHolders();
		else return null;
	}
	
}
