import model.SymbolMetric;

public class SymbolMetricBought {
	
	private SymbolMetric symbolMetric;
	private boolean bought;
	
	public SymbolMetricBought(SymbolMetric symbolMetric) {
		this.symbolMetric = symbolMetric;
	}

	public SymbolMetric getSymbolMetric() {
		return symbolMetric;
	}

	public void setSymbolMetric(SymbolMetric symbolMetric) {
		this.symbolMetric = symbolMetric;
	}

	public boolean isBought() {
		return bought;
	}

	public void setBought(boolean bought) {
		this.bought = bought;
	}
	
	
	
}
