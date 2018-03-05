package model;

public enum State {
	
	LOOKING_AT,
	SHOULD_BUY,
	BOUGHT,
	SHOULD_SELL,
	SELL;
	
	private static State[] vals = values();
	
	public State next()
	{
		return vals[(this.ordinal()+1) % vals.length];
	}
	
}
