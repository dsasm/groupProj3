package model;

public class SymbolMetric<T> implements Comparable{
	
	private String symbol;
	private T metric;
	private State state;
	
	public SymbolMetric() {}
	public SymbolMetric(String symbol, T metric,  State state) {
		this.symbol = symbol;
		this.metric = metric;
		this.state = state;
	}
	public SymbolMetric(String symbol, T metric) {
		this.symbol = symbol;
		this.metric = metric;
		this.state = State.LOOKING_AT;
	}
	public SymbolMetric(SymbolMetric copyMe) {
		this.symbol = copyMe.getSymbol();
		this.metric = (T) copyMe.getMetric();
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
	public T getMetric() {
		return metric;
	}
	public void setMetric(T metric) {
		this.metric = metric;
	}
	public void nextState() {
		this.state = this.state.next();
	}
	@Override
	public int compareTo(Object o) {
		return ((Comparable) this.metric).compareTo(((SymbolMetric<T>) o).getMetric());
	}
	
	
	
}
