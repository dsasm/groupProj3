package model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BurstClassifier implements Comparable{
	
	private Logger logger = LoggerFactory.getLogger(BurstClassifier.class);
	
	private String symbol;
	private List<Boolean> lastPrices;
	private int size;
	private Float lastPrice;
	
	public BurstClassifier() {
		this.lastPrices = new LinkedList<Boolean>();
		this.lastPrice = 0f;
	}
	
	public BurstClassifier(int size) {
		this.lastPrices = new LinkedList<Boolean>();
		this.size = size;
		this.lastPrice = 0f;
	}
	
	public BurstClassifier(String symbol, int size) {
		this.symbol = symbol;
		this.lastPrices = new LinkedList<Boolean>();
		this.size = size;
		this.lastPrice = 0f;
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
	
	public void addNewPrice(Float newPrice) {
		
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
	
	public boolean shouldBuy() {
		
		int lastCount = lastPrices.stream().filter(bool -> bool == true).collect(Collectors.toList()).size();
		int otherLastCount = lastPrices.subList(lastPrices.size()/2, lastPrices.size()).stream().filter(bool -> bool == true).collect(Collectors.toList()).size();
		boolean trendingUpAtEnd = ((double)otherLastCount )/ (((double) lastPrices.size())/2) >= 0.75d;
		logger.info("otherLastCount "+otherLastCount+" divided "+((double)otherLastCount )/ (((double) lastPrices.size())/2)+" trending? "+trendingUpAtEnd);
		logger.info(lastPrices.subList(lastPrices.size()/2, lastPrices.size()).size()+" size");
		return (lastPrices.size() >= size && lastCount >= Math.ceil(size * 0.7)) && trendingUpAtEnd; 
	}
	
	public int numberIncrease() {
		return lastPrices.stream().filter(bool -> bool == true).collect(Collectors.toList()).size();
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return new Integer(this.numberIncrease()).compareTo(((BurstClassifier) o).numberIncrease());
	}
	
}
