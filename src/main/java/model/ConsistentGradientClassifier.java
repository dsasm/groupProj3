package model;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsistentGradientClassifier implements Comparable{
		
		Logger logger = LoggerFactory.getLogger(ConsistentGradientClassifier.class);
		
		private String symbol;
		private List<Double> lastPrices;
		private int size;
		private Double lastPrice;
		
		private long timeBoughtAt;
		
		public ConsistentGradientClassifier() {
			this.lastPrices = new LinkedList<Double>();
			this.lastPrice = 0d;
		}
		
		public ConsistentGradientClassifier(int size) {
			this.lastPrices = new LinkedList<Double>();
			this.size = size;
			this.lastPrice = 0d;
		}
		
		public ConsistentGradientClassifier(String symbol, int size) {
			this.symbol = symbol;
			this.lastPrices = new LinkedList<Double>();
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

		public List<Double> getLastPrices() {
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

		public void setLastPrices(List<Double> lastPrices) {
			this.lastPrices = lastPrices;
		}
		
		public boolean shouldBuy() {
			int sumIncr = 0;
			int recIncr = 0;
			
			Double prevPrice = null;
			for (Double price: lastPrices) {
				if (prevPrice == null) prevPrice = price;
				else {
					if (price > prevPrice) sumIncr++; 
					prevPrice = price;
				}
			}
			
			boolean [] increasesRecently = {lastPrices.get(size-1)> lastPrices.get(size-2),
					lastPrices.get(size-2) > lastPrices.get(size-3) ,
					lastPrices.get(size-3) > lastPrices.get(size-4)};
			for (boolean recentIncr : increasesRecently) {
				if (recentIncr) recIncr++;
			}
			
			return (sumIncr >= size*0.7) && (recIncr >= 2);
			
		}
		
		public int getSumIncr() {
			int sumIncr =0;
			Double prevPrice = null;
			for (Double price: lastPrices) {
				if (prevPrice == null) prevPrice = price;
				else {
					if (price > prevPrice) sumIncr++; 
					prevPrice = price;
				}
			}
			return sumIncr;
		}
		
		public void addNewPrice(Double newPrice) {
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
			return Integer.compare(this.getSumIncr(), ((ConsistentGradientClassifier) o).getSumIncr());
		}
}
