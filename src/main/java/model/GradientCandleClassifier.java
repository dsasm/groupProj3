package model;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binance.api.client.domain.market.Candlestick;

public class GradientCandleClassifier implements Metric, Comparable{
			
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
		
		/**
		 * 	A Method to work out whether or not based on the Held information, the Symbol should be bought
		 * @return		- Boolean Whether or not the Symbol Should be Bought
		 */
		public boolean shouldBuy() {
			
			//Work out the Number of Candlesticks that are increasing compared to their Predecessor
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

			//Work out whether or not it is recently increasing
			boolean [] increasesRecently = {Double.valueOf(lastPrices.get(size-1).getClose())> Double.valueOf(lastPrices.get(size-1).getOpen()),
					Double.valueOf(lastPrices.get(size-2).getClose()) > Double.valueOf(lastPrices.get(size-2).getOpen()) ,
					Double.valueOf(lastPrices.get(size-3).getClose()) > Double.valueOf(lastPrices.get(size-3).getOpen())};
			for (boolean recentIncr : increasesRecently) {
				if (recentIncr) recIncr++;
			}
			
			
			//Formula used to work out whether or not to Buy based on general trend and recent trend
			return (sumIncr >= size*0.7) && (recIncr >= 2);
			
		}
		
		/**
		 * Get the number of Candlesticks increasing but returns 0 no matter what if it isnt RECENTLY trending up
		 * @return		- The number of Candlesticks increasing
		 */
		public int getSumIncr() {
			
			//Work out the Number of Candlesticks that are increasing compared to their Predecessor
			int sumIncr =0;
			Candlestick prevPrice = null;
			for (Candlestick price: lastPrices) {
				if (prevPrice == null) prevPrice = price;
				else {
					if (Double.valueOf(price.getClose()) > Double.valueOf(prevPrice.getClose())) sumIncr++; 
					prevPrice = price;
				}
			}
			
			//Work out whether or not it is recently increasing
			boolean [] increasesRecently = {Double.valueOf(lastPrices.get(size-1).getClose())> Double.valueOf(lastPrices.get(size-1).getOpen()),
					Double.valueOf(lastPrices.get(size-2).getClose()) > Double.valueOf(lastPrices.get(size-2).getOpen()) ,
					Double.valueOf(lastPrices.get(size-3).getClose()) > Double.valueOf(lastPrices.get(size-3).getOpen())};
			
			//Count the number of increases over the last 3 Prices
			int recIncr = 0;
			for (boolean recentIncr : increasesRecently) {
				if (recentIncr) recIncr++;
			}
			
			//If it isn't recently increasing, return 0 
			if (recIncr <2) sumIncr = 0;
			return sumIncr;
		}
		
		/**
		 * A Method to return the Difference in Price over the Last 10 Minutes but ONLY if it should be Bought
		 * @return 		- Double Difference in price over the Last 10 Minutges
		 */
		public Double getDiff() {
			
			//If we should Buy, return the Difference in price over the Last 10 minutes
			if (shouldBuy()) return (Double.valueOf(lastPrices.get(lastPrices.size() -1).getClose()) - Double.valueOf(lastPrices.get(0).getOpen()));
			//Else return null
			else return null;
		}
		
		/**
		 * A Method to update the internal list of Prices
		 * @param newPrice			- The latest Candlestick to consider
		 */
		public void addNewPrice(Object newObject) {
			
			Candlestick newCandlestick = (Candlestick) newObject;
			
			//If we aren't booting up, remove the First value and add the new Price to the End
			if (lastPrices.size() >= size) {
				lastPrices.remove(0);
				lastPrices.add(newCandlestick);
			}
			//Else if we are still booting up, just add the new Price to the List of Prices Generated
			else {
				
				lastPrices.add(newCandlestick);
			}
		}
		
		/**
		 * A method used to compare objects 
			@exception TypeException 			- Object type is not ensured, it is up to the user to ensure this is called with the Correct implementation of Object
		 */
		@Override
		public int compareTo(Object o) {
			//When comparing, we want to sort by not just whether to buy, but that which should buy and is promising
			
			//Extract the Differences between the prices 10 mins to now
			Double thisDiff = this.getDiff();
			Double thatDiff = ((GradientCandleClassifier) o).getDiff();
			
			//Would return null from getDiff if shouldnt Buy so sort those to the bottom
			if (thisDiff == null && thatDiff == null) return 0;
			if (thisDiff== null) return -1;
			else if (thatDiff == null) return 1;
			
			//If both are should buys compare their differences
			else return Double.compare(thisDiff ,thatDiff);
		}

		@Override
		public boolean shouldSell(Double currPrice, BoughtInfo boughtInfo) {
			
			return this.getSumIncr() <= 5 || currPrice < 0.995*boughtInfo.getBoughtAt() || currPrice < 0.998*boughtInfo.getHighestProfit();
		}
}
