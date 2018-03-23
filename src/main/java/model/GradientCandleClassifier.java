package model;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binance.api.client.domain.market.Candlestick;

public class GradientCandleClassifier implements Comparable{
			
		Logger logger = LoggerFactory.getLogger(GradientCandleClassifier.class);
		
		private String symbol;
		private List<Candlestick> lastPrices;
		private int size;
		private Double lastPrice;
		
		private long timeBoughtAt;
		
		public GradientCandleClassifier() {
			this.lastPrices = new LinkedList<Candlestick>();
			this.lastPrice = 0d;
		}
		
		public GradientCandleClassifier(int size) {
			this.lastPrices = new LinkedList<Candlestick>();
			this.size = size;
			this.lastPrice = 0d;
		}
		
		public GradientCandleClassifier(String symbol, int size) {
			this.symbol = symbol;
			this.lastPrices = new LinkedList<Candlestick>();
			this.size = size;
			this.lastPrice = 0d;

		}
		

		public Logger getLogger() {
			return logger;
		}

		public void setLogger(Logger logger) {
			this.logger = logger;
		}

		public long getTimeBoughtAt() {
			return timeBoughtAt;
		}

		public void setTimeBoughtAt(long timeBoughtAt) {
			this.timeBoughtAt = timeBoughtAt;
		}

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		public List<Candlestick> getLastPrices() {
			return lastPrices;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public Double getLastPrice() {
			return lastPrice;
		}

		public void setLastPrice(Double lastPrice) {
			this.lastPrice = lastPrice;
		}

		public void setLastPrices(List<Candlestick> lastPrices) {
			this.lastPrices = lastPrices;
		}
		
		public boolean shouldBuy() {
			int sumIncr = 0;
			int recIncr = 0;
			
			Candlestick prevPrice = null;
			for (Candlestick price: lastPrices) {
				if (prevPrice == null) prevPrice = price;
				else {
					if (Double.valueOf(price.getClose()) > Double.valueOf(prevPrice.getClose())) sumIncr++; 
					prevPrice = price;
				}
			}
			
			boolean [] increasesRecently = {Double.valueOf(lastPrices.get(size-1).getClose())> Double.valueOf(lastPrices.get(size-2).getClose()),
					Double.valueOf(lastPrices.get(size-2).getClose()) > Double.valueOf(lastPrices.get(size-3).getClose()) ,
					Double.valueOf(lastPrices.get(size-3).getClose()) > Double.valueOf(lastPrices.get(size-4).getClose())};
			for (boolean recentIncr : increasesRecently) {
				if (recentIncr) recIncr++;
			}
			
			return (sumIncr >= size*0.7) && (recIncr >= 2);
			
		}
		
		public int getSumIncr() {
			int sumIncr =0;
			Candlestick prevPrice = null;
			for (Candlestick price: lastPrices) {
				if (prevPrice == null) prevPrice = price;
				else {
					if (Double.valueOf(price.getClose()) > Double.valueOf(prevPrice.getClose())) sumIncr++; 
					prevPrice = price;
				}
			}
			
			return sumIncr;
		}
		
		public void addNewPrice(Candlestick newPrice) {
			if (lastPrices.size() >= size) {
				lastPrices.remove(0);
				lastPrices.add(newPrice);
			}
			else {
				lastPrices.add(newPrice);
			}
		}
		@Override
		public int compareTo(Object o) {
			return Integer.compare(this.getSumIncr(), ((GradientCandleClassifier) o).getSumIncr());
		}
}
