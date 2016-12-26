import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;


public class ChooserImpl2<T>  implements Chooser<T>
{
	
	private final List<Pair<T,Double>> itemWeights = new ArrayList<Pair<T,Double>>();
	
	@Override
	public T choose() 
	{
		return new EnumeratedDistribution<>(itemWeights).sample();
	}

	@Override
	public void add(T item, double weight) 
	{
		Pair<T,Double> p = new Pair<T,Double>(item,weight);
		itemWeights.add(p);
	}

	@Override
	public void remove(T item) {
		for (Pair<T, Double> pair : itemWeights) {
			if(item!=null && item.equals(pair.getKey())){itemWeights.remove(pair);}
		}
		
	}

	@Override
	public boolean isEmpty() 
	{
		return itemWeights!=null && itemWeights.size()==0;
	}

	public List<Double> getWeights() 
	{
		List<Double> weights = new ArrayList<Double>();
		for(Pair<T,Double> p : itemWeights)
		{
			weights.add(p.getValue());
		}
		return weights;
	}

	public List<T> getItems() 
	{
		List<T> items = new ArrayList();
		for(Pair<T,Double> p : itemWeights)
		{
			items.add(p.getKey());
		}
		return items;			
	}
		
	
	

}
