package model;

public interface Metric {

	public boolean shouldBuy();
	
	public boolean shouldSell(Double currPrice, BoughtInfo boughtInfo);
	
	public void addNewPrice(Object newPrice); 
	
}
