package model;

public class SymbolMetric implements Comparable{
	
	private String symbol;
	private Float metric;
	private State state;
	
	public SymbolMetric() {}
	public SymbolMetric(String symbol, Float metric,  State state) {
		this.symbol = symbol;
		this.metric = metric;
		this.state = state;
	}
	public SymbolMetric(String symbol, Float metric) {
		this.symbol = symbol;
		this.metric = metric;
		this.state = State.LOOKING_AT;
	}
	public SymbolMetric(SymbolMetric copyMe) {
		this.symbol = copyMe.getSymbol();
		this.metric = copyMe.getMetric();
		this.state = copyMe.getState();
	}
	
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
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
	public void nextState() {
		this.state = this.state.next();
	}
	@Override
	public int compareTo(Object o) {
		return this.metric.compareTo(((SymbolMetric) o).getMetric());
	}
	
	
	
}
