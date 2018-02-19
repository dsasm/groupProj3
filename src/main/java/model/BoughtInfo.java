package model;

public class BoughtInfo {
	
	private String symbol;
	private Float boughtAt;
	private Float highestProfit;
	private boolean passedThreshhold;
	private boolean shouldSell;
	private Integer quantity;
	
	public BoughtInfo(String symbol, Float boughtAt, Float highestProfit,  boolean passedThreshhold, Integer quantity, boolean shouldSell) {
		this.symbol = symbol;
		this.boughtAt = boughtAt;
		this.highestProfit = highestProfit;
		this.passedThreshhold = passedThreshhold;
		this.quantity = quantity;
		this.shouldSell = shouldSell;
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

	public Float getBoughtAt() {
		return boughtAt;
	}

	public void setBoughtAt(Float boughtAt) {
		this.boughtAt = boughtAt;
	}

	public Float getHighestProfit() {
		return highestProfit;
	}

	public void setHighestProfit(Float highestProfit) {
		this.highestProfit = highestProfit;
	}
	
	public Float getHighestProfitDiff() {
		return highestProfit - boughtAt;
	}
	
	
	
}
