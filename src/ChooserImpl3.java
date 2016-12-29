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

// import java.util.ArrayList;
// import java.util.List;
//
// import org.apache.commons.math3.distribution.EnumeratedDistribution;
// import org.apache.commons.math3.util.Pair;
//
//
// public class ChooserImpl3<T> implements Chooser<T>
// {
//
// private final List<Pair<T,Double>> itemWeights = new
// ArrayList<Pair<T,Double>>();
//
// @Override
// public T choose()
// {
// return new EnumeratedDistribution<>(itemWeights).sample();
// }
//
// @Override
// public void add(T item, double weight)
// {
// Pair<T,Double> p = new Pair<T,Double>(item,weight);
// itemWeights.add(p);
//
// }
//
// @Override
// public void remove(T item) {
// for (Pair<T, Double> pair : itemWeights) {
// if(item!=null && item.equals(pair.getKey())){itemWeights.remove(pair);}
// }
//
// }
//
// @Override
// public boolean isEmpty()
// {
// return itemWeights!=null && itemWeights.size()==0;
// }
//
// public List<Double> getWeights()
// {
// List<Double> weights = new ArrayList<Double>();
// for(Pair<T,Double> p : itemWeights)
// {
// weights.add(p.getValue());
// }
// return weights;
// }
//
// public List<T> getItems()
// {
// List<T> items = new ArrayList();
// for(Pair<T,Double> p : itemWeights)
// {
// items.add(p.getKey());
// }
// return items;
// }
//
//
// // private static long lcm(long a, long b)
// // {
// // return a * (b / gcd(a, b));
// // }
// // private static long gcd(long a, long b)
// // {
// // while (b > 0)
// // {
// // long temp = b;
// // b = a % b; // % is remainder
// // a = temp;
// // }
// // return a;
// // }
//
//
// }
