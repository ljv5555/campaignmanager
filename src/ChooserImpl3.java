import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChooserImpl3<T> implements Chooser<T> {
	private List<T> weightedItems = new ArrayList<>();
	private Map<T, Double> weights = new LinkedHashMap<>();
	private long globalWeightMultiplier = 1;

	@Override
	public void add(T item, double weight) {
		weights.put(item, weight);
		while (((globalWeightMultiplier * weight) - Math
				.round(globalWeightMultiplier * weight)) != 0) {
			List<T> pwi = new ArrayList<T>(weightedItems.size());
			pwi.addAll(weightedItems);
			globalWeightMultiplier *= 2.0;
			weightedItems.addAll(pwi);
		}
		long totalWeight = (long) (globalWeightMultiplier * weight);
		// TODO: clean-up loop with collections copy method
		for (int i = 0; i < totalWeight; i++) {
			weightedItems.add(item);
		}
	}

	@Override
	public void remove(T item) {
		while (weightedItems.contains(item)) {
			weightedItems.remove(item);
		}
		if (weights.containsKey(item)) {
			weights.remove(item);
		}
	}

	@Override
	public boolean isEmpty() {
		return weights != null && weightedItems != null && weights.size() != 0
				&& weightedItems.size() != 0;
	}

	@Override
	public T choose() {
		int index = (int) Math
				.round((Math.random() * (weightedItems.size() - 1)));
		if (index < 0)
			index = 0;
		else
			while (index > (weightedItems.size() - 1))
				index--;
		// System.out.println("weightedItems.size()="+weightedItems.size()+", index="+index);
		return weightedItems.get(index);
	}
}
