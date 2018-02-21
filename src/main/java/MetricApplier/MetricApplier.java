package MetricApplier;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface MetricApplier {
	
	public static Map<String, Float> symbolsVsMetric = new HashMap<String, Float>();
	
	public void execute();
}
