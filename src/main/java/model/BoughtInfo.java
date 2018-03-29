package model;

public class BoughtInfo {
	
	private String symbol;
	private Double boughtAt;
	private Double highestProfit;
	private boolean passedThreshhold;
	private boolean shouldSell;
	private Integer quantity;
	private long timeBoughtAt;
	
	public BoughtInfo(String symbol, Double boughtAt, Double highestProfit,  boolean passedThreshhold, Integer quantity, boolean shouldSell, long timeBoughtAt) {
		this.symbol = symbol;
		this.boughtAt = boughtAt;
		this.highestProfit = highestProfit;
		this.passedThreshhold = passedThreshhold;
		this.quantity = quantity;
		this.shouldSell = shouldSell;
		this.timeBoughtAt = timeBoughtAt;
	}

	public long getTimeBoughtAt() {
		return timeBoughtAt;
	}

	public void setTimeBoughtAt(long timeBoughtAt) {
		this.timeBoughtAt = timeBoughtAt;
	}

	public boolean isShouldSell() {
		return shouldSell;
	}

	public void setShouldSell(boolean shouldSell) {
		this.shouldSell = shouldSell;
	}

	public BoughtInfo() {
		// TODO Auto-generated constructor stub
	}

	public boolean isPassedThreshhold() {
		return passedThreshhold;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public void setPassedThreshhold(boolean passedThreshhold) {
		this.passedThreshhold = passedThreshhold;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Double getBoughtAt() {
		return boughtAt;
	}

	public void setBoughtAt(Double boughtAt) {
		this.boughtAt = boughtAt;
	}

	public Double getHighestProfit() {
		return highestProfit;
	}

	public void setHighestProfit(Double highestProfit) {
		this.highestProfit = highestProfit;
	}
	
	public Double getHighestProfitDiff() {
		return highestProfit - boughtAt;
	}
	
	
	
}
