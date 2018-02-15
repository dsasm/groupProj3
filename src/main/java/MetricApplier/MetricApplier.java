package MetricApplier;

import java.util.HashMap;
import java.util.Map;

public interface MetricApplier {
	
	public static Map<String, Float> symbolsVsMetric = new HashMap<String, Float>();
	
	public void execute();
}
