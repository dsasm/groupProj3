package considerer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.surf.dsasm.Rework.client.RestClientInteractor;

import MovingAverage.MovingAverageDifferenceCalculator;
import buySell.BuyConsiderer;
import model.Metric;

@Component
public class MovingAverageBuyConsiderer implements BuyConsiderer {
	
	private MovingAverageDifferenceCalculator movingAverageDifferenceCalculator;
	private RestClientInteractor clientInteractor;
	private Logger logger = LoggerFactory.getLogger(MovingAverageBuyConsiderer.class);
	
	@Autowired
	public MovingAverageBuyConsiderer(MovingAverageDifferenceCalculator movingAverageDifferenceCalculator
			, RestClientInteractor clientInteractor) {
		this.clientInteractor = clientInteractor;
		this.movingAverageDifferenceCalculator = movingAverageDifferenceCalculator;
	}

	@Override
	public boolean shouldBuyNow(String symbolToConsider, Metric metric) {
//		Float MADiff = movingAverageDifferenceCalculator.calculateLightweight(symbolToConsider, CandlestickInterval.FIFTEEN_MINUTES, CandlestickInterval.FIVE_MINUTES);
//		List<Candlestick> candlesticks2 = clientInteractor.getCandlesticks(symbolToConsider, CandlestickInterval.FIVE_MINUTES);
//		Float latestOpen = Float.valueOf(candlesticks2.get(candlesticks2.size() - 1).getOpen());
//		Float latestClose = Float.valueOf(candlesticks2.get(candlesticks2.size() - 1).getClose());
//		logger.info("SYMBOL: "+symbolToConsider+" should buy? %diff MA = "+MADiff.toString() +" latestOpen  : "+latestOpen+" vs latestClose : "+latestClose);
//		if (MADiff > 1.001 && latestClose > latestOpen) return gainConfidenceInBuy(latestClose, symbolToConsider);
//		
//		return false;
//		
		return metric.shouldBuy();
	}
	
	private boolean gainConfidenceInBuy(Float priceBasedOn, String thisSymbol) {
		//wait a half minute then check if it has increased, then buy
		List<Boolean> confArray =  new ArrayList<Boolean>();
		int counter = 0;
		while(counter < 3 ) {
			logger.info("CoinWatcher - "+thisSymbol+" - Gaining Confidence in Buy - passed "+priceBasedOn);
			try {
				Thread.sleep(1000*5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Float newPrice = new Float(clientInteractor.getLatestPrice(thisSymbol));
			logger.info("New price "+thisSymbol+" : "+newPrice);
			Float percDiff = newPrice / priceBasedOn * 100;
			if (percDiff > 100.05) {
				logger.info("CoinWatcher - "+thisSymbol+" - Confidence Gained with new Price "+newPrice);
				counter ++;
				confArray.add(true);
				priceBasedOn = newPrice;
			}
			else if (percDiff < 99.95) {
				logger.info("CoinWatcher - "+thisSymbol+" - Confidence Lost with new Price "+newPrice);
				counter++;
				confArray.add(false);
				priceBasedOn = newPrice;
			}
		}
		int confCounter = 0;
		logger.info("Confidence OutCome: ");
		for (int i = 0; i < confArray.size(); i ++) {
			logger.info(confArray.get(i)+" - ");
			if (confArray.get(i)) confCounter++;
		}
		logger.info(" returning : "+confCounter);
		return (confCounter >= 2);
	}

	
}
