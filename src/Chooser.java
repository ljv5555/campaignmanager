public interface Chooser<T> {
public void add(T item, double weight);
public void remove(T item);
public boolean isEmpty();
// Choose an item at random, based on the configured weights.  
// A higher weight implies that the corresponding item 
// has a higher probability of being selected.
// Items are not removed after they have been selected.
public T choose();
}
 