package model;

public class SymbolMetric implements Comparable{
	
	private String symbol;
	private Float metric;
	
	public SymbolMetric() {}
	public SymbolMetric(String symbol, Float metric) {
		this.symbol = symbol;
		this.metric = metric;
	}
	public SymbolMetric(SymbolMetric copyMe) {
		this.symbol = copyMe.getSymbol();
		this.metric = copyMe.getMetric();
	}
	
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Float getMetric() {
		return metric;
	}
	public void setMetric(Float metric) {
		this.metric = metric;
	}
	
	@Override
	public int compareTo(Object o) {
		return this.metric.compareTo(((SymbolMetric) o).getMetric());
	}
	
	
	
}
