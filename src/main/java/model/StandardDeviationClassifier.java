package model;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StandardDeviationClassifier implements Comparable{
	
	Logger logger = LoggerFactory.getLogger(StandardDeviationClassifier.class);
	
	private String symbol;
	private List<Double> lastPrices;
	private int size;
	private Double lastPrice;
	
	private Double currentStandardDev;
	private Double highestProfit;
	private Double boughtAt;
	
	private Double sumValues;
	private Double sumSquares;
	
	public StandardDeviationClassifier() {
		this.lastPrices = new LinkedList<Double>();
		this.lastPrice = 0d;
	}
	
	public StandardDeviationClassifier(int size) {
		this.lastPrices = new LinkedList<Double>();
		this.size = size;
		this.lastPrice = 0d;
	}
	
	public StandardDeviationClassifier(String symbol, int size) {
		this.symbol = symbol;
		this.lastPrices = new LinkedList<Double>();
		this.size = size;
		this.lastPrice = 0d;

		this.sumValues = 0d;
		this.sumSquares = 0d;
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

	public Double getCurrentStandardDev() {
		return currentStandardDev;
	}

	public void setCurrentStandardDev(Double currentStandardDev) {
		this.currentStandardDev = currentStandardDev;
	}

	public Double getHighestProfit() {
		return highestProfit;
	}

	public void setHighestProfit(Double highestProfit) {
		this.highestProfit = highestProfit;
	}

	public Double getBoughtAt() {
		return boughtAt;
	}

	public void setBoughtAt(Double boughtAt) {
		this.boughtAt = boughtAt;
	}

	public void setLastPrices(List<Double> lastPrices) {
		this.lastPrices = lastPrices;
	}
	
	public boolean shouldBuy(Double priceToCompare) {
		logger.info("price "+(priceToCompare /  lastPrices.get(lastPrices.size()-1))+" would need "+(currentStandardDev + lastPrices.get(lastPrices.size()-1)));
		return priceToCompare > currentStandardDev + lastPrices.get(lastPrices.size()-1);
	}
	
	public void addNewPrice(Double newPrice) {
		if (lastPrice == 0d) lastPrice = newPrice;
		if (lastPrices.size() >= size) {
			currentStandardDev = newStandardDeviation(newPrice);
			lastPrices.remove(0);
			lastPrices.add(newPrice);
		}
		else {
			
			lastPrices.add(newPrice/lastPrice);
			sumValues += newPrice/lastPrice;
			sumSquares += (newPrice)*(newPrice);
		}
		lastPrice = newPrice;
	}
	
	public double newStandardDeviation(Double newPrice) {

		sumValues -= lastPrices.get(0);
		sumSquares -= lastPrices.get(0)*lastPrices.get(0);
		
		sumValues += newPrice;
		sumSquares += newPrice*newPrice;
		
		return (sumSquares / lastPrices.size()) - Math.pow((sumValues / lastPrices.size()), 2);
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Double thisDiff =this.getLastPrices().get(this.getSize()-1) - (currentStandardDev + lastPrices.get(lastPrices.size()-2));
		Double thatDiff = ((StandardDeviationClassifier) o).getLastPrices().get(((StandardDeviationClassifier) o).getSize()-1) - (((StandardDeviationClassifier) o).getCurrentStandardDev() +((StandardDeviationClassifier) o).getLastPrices().get(((StandardDeviationClassifier) o).getSize()-2)) ; 
		return thisDiff.compareTo(thatDiff);
	}
}
