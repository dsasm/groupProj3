package com.surf.dsasm.idk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.surf.dsasm.Rework.client.RestClientInteractor;

import MetricApplier.BurstSearcher;
import MetricApplier.CandlestickGradientSearcher;
import MetricApplier.ConsistentGradientSearcher;
import MetricApplier.MetricApplier;
import MetricApplier.StandardDeviationSearcher;
import MovingAverage.MovingAverageDifferenceCalculator;
import MovingAverage.MovingAverageFlatPercentageDifferenceCalculator;
import ThreadCoinHolders.ThreadCoinHolders;
import buySell.BurstBuyConsiderer;
import buySell.BurstSellConsiderer;
import buySell.BuyConsiderer;
import buySell.CandlestickGradientBuyConsiderer;
import buySell.CandlestickGradientSellConsiderer;
import buySell.ConsistentGradientBuyConsiderer;
import buySell.ConsistentGradientSellConsiderer;
import buySell.FakeBuyer;
import buySell.FakeSeller;
import buySell.MetricBuyConsiderer;
import buySell.MetricSellConsiderer;
import buySell.SellConsiderer;
import buySell.ShouldBuy;
import buySell.ShouldSell;
import buySell.StandardDeviationBuyConsiderer;
import buySell.StandardDeviationSellConsiderer;
import considerer.PercentageHighestProfitSellConsiderer;

@Configuration
@ComponentScan(basePackages={"com.surf.dsasm.Rework.client", "buySell","considerer"})
public class AppConfig {
	
	
	
	@Bean
	public MetricApplier metricApplier(RestClientInteractor clientInteractor ) {
		if (App.mode.equals(Mode.BURST)) return new BurstSearcher(clientInteractor);
		else if (App.mode.equals(Mode.SD)) return new StandardDeviationSearcher(clientInteractor);
		else if (App.mode.equals(Mode.GRADIENT)) return new ConsistentGradientSearcher(clientInteractor);
		else if (App.mode.equals(Mode.GRADIENT_CANDLES)) return new CandlestickGradientSearcher(clientInteractor);
		else return null;
	}
	
	@Bean
	public MovingAverageDifferenceCalculator movingAverageDifferenceCalculator(RestClientInteractor clientInteractor) {
		if (App.mode.equals(Mode.BURST)) return new MovingAverageFlatPercentageDifferenceCalculator(clientInteractor);
		else return null;
	}
	
	@Bean
	public ShouldBuy shouldBuy(MetricBuyConsiderer metricBuyConsiderer) {
		
		if (!App.mode.equals(Mode.DATA_GATHER)) return new ShouldBuy(metricBuyConsiderer);
		else return null;
	}
	
	@Bean
	public BuyConsiderer buyConsiderer(RestClientInteractor clientInteractor) {
		if (!App.mode.equals(Mode.DATA_GATHER)) return new MetricBuyConsiderer(clientInteractor);
		else return null;
	}
	
	@Bean
	public SellConsiderer sellConsiderer(RestClientInteractor clientInteractor) {
		if (!App.mode.equals(Mode.DATA_GATHER)) return new MetricSellConsiderer(clientInteractor);
		else return null;
	}
	
	@Bean
	public ShouldSell shouldSell(FakeBuyer buyer
			, FakeSeller seller
			, MetricSellConsiderer metricSellConsiderer) {
		
		if (!App.mode.equals(Mode.DATA_GATHER)) return new ShouldSell(metricSellConsiderer, buyer, seller);
		else return null;
	}
	
	@Bean
	public ThreadCoinHolders threadCoinHolders() {
		if (App.mode.equals(Mode.BURST)
				|| App.mode.equals(Mode.SD) 
				|| App.mode.equals(Mode.GRADIENT)
				|| App.mode.equals(Mode.GRADIENT_CANDLES)) return new ThreadCoinHolders();
		else return null;
	}
	
}
