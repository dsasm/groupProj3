package model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BurstClassifier implements Metric, Comparable{
	
	private Logger logger = LoggerFactory.getLogger(BurstClassifier.class);
	
	private String symbol;
	private List<Boolean> lastPrices;
	private int size;
	private Double lastPrice;
	
	public BurstClassifier() {
		this.lastPrices = new LinkedList<Boolean>();
		this.lastPrice = 0d;
	}
	
	public BurstClassifier(int size) {
		this.lastPrices = new LinkedList<Boolean>();
		this.size = size;
		this.lastPrice = 0d;
	}
	
	public BurstClassifier(String symbol, int size) {
		this.symbol = symbol;
		this.lastPrices = new LinkedList<Boolean>();
		this.size = size;
		this.lastPrice = 0d;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public List<Boolean> getLastPrices() {
		return lastPrices;
	}

	public void setLastPrices(List<Boolean> lastPrices) {
		this.lastPrices = lastPrices;
	}
	
	public void addNewPrice(Object newObject) {
		
		Double newPrice = (Double) newObject;
		
		if (lastPrices.size() >= size) {
			lastPrices.remove(0);
			lastPrices.add(newPrice > lastPrice);
			
		}
		else {
			lastPrices.add(newPrice > lastPrice);
			logger.info(symbol.toUpperCase()+" - "+lastPrices.get(lastPrices.size()-1));
		}
		lastPrice = newPrice;
	}
	
	/**
	 * Work out whether or not this Symbol looks Promising 
	 * @return			- Whether or not this Symbol should be Bought
	 */
	public boolean shouldBuy() {
		
		//Get the last Count
		int lastCount = lastPrices.stream().filter(bool -> bool == true).collect(Collectors.toList()).size();
		
		//Get a Recent Count
		int otherLastCount = lastPrices.subList(lastPrices.size()/2, lastPrices.size()).stream().filter(bool -> bool == true).collect(Collectors.toList()).size();
		boolean trendingUpAtEnd = ((double)otherLastCount )/ (((double) lastPrices.size())/2) >= 0.75d;
		
		
		logger.info("otherLastCount "+otherLastCount+" divided "+((double)otherLastCount )/ (((double) lastPrices.size())/2)+" trending? "+trendingUpAtEnd);
		logger.info(lastPrices.subList(lastPrices.size()/2, lastPrices.size()).size()+" size");
		
		//Return true if the lastCount is promising and it is recently trending up
		return (lastPrices.size() >= size && lastCount >= Math.ceil(size * 0.7)) && trendingUpAtEnd; 
	}
	
	/**
	 * Work out the number of Increases this Object has Recorded
	 * @return		- Return the Number of Increases this Object has recorded
	 */
	public int numberIncrease() {
		
		//return the Number of Increases this Object has recorded
		return lastPrices.stream().filter(bool -> bool == true).collect(Collectors.toList()).size();
	}

	@Override
	public int compareTo(Object o) {
		
		//Just return a comparison of the number of Increases between the 2 Objects
		return new Integer(this.numberIncrease()).compareTo(((BurstClassifier) o).numberIncrease());
	}

	@Override
	public boolean shouldSell(Double currPrice, BoughtInfo boughtInfo) {
		return this.numberIncrease() <= 5 || currPrice < 0.995*boughtInfo.getBoughtAt() || currPrice < 0.995*boughtInfo.getHighestProfit();
	}
	
}
