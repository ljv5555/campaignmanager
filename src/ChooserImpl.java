import java.util.ArrayList;
import java.util.List;


public class ChooserImpl<T> implements Chooser<T>{

	private List<T> items = new ArrayList<T>();
	private List<Double> weights = new ArrayList<Double>();
	
	public synchronized List<Double> getWeights() {
		return weights;
	}

	public synchronized void setWeights(List<Double> weights) {
		this.weights = weights;
	}

	public synchronized List<T> getItems() {
		return items;
	}

	public synchronized void setItems(List<T> items) {
		this.items = items;
	}

	@Override
	public void add(T item, double weight) {
		List<T> i = getItems();
		i.add(item);
		setItems(i);
		List<Double> d = getWeights();
		d.add(weight);
		setWeights(d);
	}

	@Override
	public void remove(T item) {
		List<T> i = getItems();
		int index = getItems().indexOf(item);
		List<Double> d = getWeights();
		i.remove(index);
		d.remove(index);
		setItems(i);
		setWeights(d);
	}

	@Override
	public boolean isEmpty() {
		return getItems().size()==0;
	}

	private Double getProportion(T item)
	{
		Double rtn = 1.0;
		Double sumWeights = 0.0;
		for(Double d : getWeights()){sumWeights+=d;}
		rtn =  sumWeights;
		Double iweight = getWeights().get(getItems().indexOf(item));
		rtn = iweight / sumWeights;
		return rtn;
	}
	private List<Double> getProportions()
	{
		List<T> items = getItems();
				
		ArrayList<Double> rtn = new ArrayList<>();
		for(int i = 0;i<items.size();i++)
		{
			T item = items.get(i);
			if(i==0)
			{
				rtn.add(getProportion(item));
			}
			else
			{
				Double n = rtn.get(rtn.size()-1);
				n = n+getProportion(item);
				rtn.add(n);
			}
			

		}
		return rtn;
	}
	
	@Override
	public T choose() {
		List<T> items = getItems();
		List<Double> proportions = getProportions();
		T rtn = items.get(0);
		Double rand = Math.random();
		for(int i=1;i<items.size();i++)
		{
			if(rand>=proportions.get(i-1))
			{
				rtn = items.get(i);
			}
		}
		return rtn;
	}

}
