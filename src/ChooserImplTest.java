import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;


public class ChooserImplTest {

	
	@Test
	public void testIterationTimes()
	{
		for(int i=10;i<=40;i+=10)
		{
			System.out.println("v1-collection size: "+i+", average choice time (ms): "+testChoices(i, 1000000));
		}
	}
	
	@Test
	public void testIterationTimes2()
	{
		for(int i=10;i<=40;i+=10)
		{
			System.out.println("v2-collection size: "+i+", average choice time (ms): "+testChoices2(i, 500000));
		}
	}
	
	@Test
	public void testIterationTimes3()
	{
		for(int i=10;i<=40;i+=10)
		{
			System.out.println("v3-collection size: "+i+", average choice time (ms): "+testChoices3(i, 500000));
		}
	}
	
	
	/**
	 * 
	 * @param totalChoices  total possible choices in rotation
	 * @param iterations    the number of times to call the choose function
	 * @param variance      .10 asserts the expected result is within 10% of the actual result
	 * @return average time per choice in ms
	 */
	public double testChoices(int totalChoices, int iterations) {
		List<Integer> choiceTimesNs = new ArrayList<Integer>();
		double variance = 0.10;
		ChooserImpl<String> c = new ChooserImpl<String>();
		for(int i=0;i<totalChoices;i++)
		{
			c.add("choice_"+i,1.0*i);
		}
		
		
		List<Double> weights =  c.getWeights();
		List<String> items = c.getItems();
		double totalWeight = 0;
		
		List<Double> proportions = new ArrayList<Double>();
		
		for(Double w : weights){totalWeight+=w;proportions.add(w);}
		for(int i=0;i<proportions.size();i++){double d = proportions.get(i)/totalWeight;proportions.set(i, new Double(d));}
		//System.out.println("1.");
		List<String> choices = new ArrayList<String>();
		List<Double> itemChosenCount = new ArrayList<Double>();
		for(String item : items){itemChosenCount.add(0.0);}
		for(int i=0;i<iterations;i++)
		{
			long prevtime = System.nanoTime();
			String choice = c.choose();
			choiceTimesNs.add(new Integer((int)(System.nanoTime()-prevtime)));
			choices.add(choice);
			Double prevItemChosenCount=itemChosenCount.get(items.indexOf(choice));
			itemChosenCount.set(items.indexOf(choice), prevItemChosenCount+1.0);
		}
	
		for(int i=0;i<items.size();i++)
		{
			double expectedItemCount = iterations * proportions.get(i);
			double expectedItemCountLow = expectedItemCount - (expectedItemCount * variance);
			double expectedItemCountHigh = expectedItemCount + (expectedItemCount * variance);
			int count = Collections.frequency(choices, items.get(i));
			//System.out.println("i="+i+",   "+expectedItemCountLow + " <= "+count + " <= "+expectedItemCountHigh );
			//assertTrue(count>=expectedItemCountLow);
			//assertTrue(count<=expectedItemCountHigh);
		}
		double totalTime = 0;
		for(Integer i : choiceTimesNs)
		{
			totalTime+=i;
		}
		double averageTime = totalTime / choiceTimesNs.size();
		return averageTime/1000.0;
		
	}
	/**
	 * 
	 * @param totalChoices  total possible choices in rotation
	 * @param iterations    the number of times to call the choose function
	 * @param variance      .10 asserts the expected result is within 10% of the actual result
	 * @return average time per choice in ms
	 */
	public double testChoices2(int totalChoices, int iterations) {
		List<Integer> choiceTimesNs = new ArrayList<Integer>();
		double variance = 0.10;
		ChooserImpl2<String> c = new ChooserImpl2<String>();
		for(int i=0;i<totalChoices;i++)
		{
			c.add("choice_"+i,1.0*i);
		}
		
		
		List<Double> weights =  c.getWeights();
		List<String> items = c.getItems();
		double totalWeight = 0;
		
		List<Double> proportions = new ArrayList<Double>();
		
		for(Double w : weights){totalWeight+=w;proportions.add(w);}
		for(int i=0;i<proportions.size();i++){double d = proportions.get(i)/totalWeight;proportions.set(i, new Double(d));}
		//System.out.println("1.");
		List<String> choices = new ArrayList<String>();
		List<Double> itemChosenCount = new ArrayList<Double>();
		for(String item : items){itemChosenCount.add(0.0);}
		for(int i=0;i<iterations;i++)
		{
			long prevtime = System.nanoTime();
			String choice = c.choose();
			choiceTimesNs.add(new Integer((int)(System.nanoTime()-prevtime)));
			choices.add(choice);
			Double prevItemChosenCount=itemChosenCount.get(items.indexOf(choice));
			itemChosenCount.set(items.indexOf(choice), prevItemChosenCount+1.0);
		}
	
		for(int i=0;i<items.size();i++)
		{
			double expectedItemCount = iterations * proportions.get(i);
			double expectedItemCountLow = expectedItemCount - (expectedItemCount * variance);
			double expectedItemCountHigh = expectedItemCount + (expectedItemCount * variance);
			int count = Collections.frequency(choices, items.get(i));
			//System.out.println("i="+i+",   "+expectedItemCountLow + " <= "+count + " <= "+expectedItemCountHigh );
			//assertTrue(count>=expectedItemCountLow);
			//assertTrue(count<=expectedItemCountHigh);
		}
		double totalTime = 0;
		for(Integer i : choiceTimesNs)
		{
			totalTime+=i;
		}
		double averageTime = totalTime / choiceTimesNs.size();
		return averageTime/1000.0;
		
	}
	
	public double testChoices3(int totalChoices, int iterations) {
		List<Integer> choiceTimesNs = new ArrayList<Integer>();
		double variance = 0.10;
		Chooser<String> c = new ChooserImpl3<String>();
		List<Double> weights =  new ArrayList<>();
		List<String> items = new ArrayList<>();
		for(int i=0;i<totalChoices;i++)
		{
			String item = "choice_"+i;
			Double weight = 1.0*(i+1);
			c.add(item,weight);
			items.add(item);
			weights.add(weight);
		}
		
		
		double totalWeight = 0;
		
		List<Double> proportions = new ArrayList<Double>();
		
		for(Double w : weights){totalWeight+=w;proportions.add(w);}
		for(int i=0;i<proportions.size();i++){double d = proportions.get(i)/totalWeight;proportions.set(i, new Double(d));}
		//System.out.println("1.");
		List<String> choices = new ArrayList<String>();
		List<Double> itemChosenCount = new ArrayList<Double>();
		for(String item : items){itemChosenCount.add(0.0);}
		for(int i=0;i<iterations;i++)
		{
			long prevtime = System.nanoTime();
			String choice = c.choose();
			choiceTimesNs.add(new Integer((int)(System.nanoTime()-prevtime)));
			choices.add(choice);
			Double prevItemChosenCount=itemChosenCount.get(items.indexOf(choice));
			itemChosenCount.set(items.indexOf(choice), prevItemChosenCount+1.0);
		}
	
		for(int i=0;i<items.size();i++)
		{
			double expectedItemCount = iterations * proportions.get(i);
			double expectedItemCountLow = expectedItemCount - (expectedItemCount * variance);
			double expectedItemCountHigh = expectedItemCount + (expectedItemCount * variance);
			int count = Collections.frequency(choices, items.get(i));
			//System.out.println("i="+i+",   "+expectedItemCountLow + " <= "+count + " <= "+expectedItemCountHigh );
			//assertTrue(count>=expectedItemCountLow);
			//assertTrue(count<=expectedItemCountHigh);
		}
		double totalTime = 0;
		for(Integer i : choiceTimesNs)
		{
			totalTime+=i;
		}
		double averageTime = totalTime / choiceTimesNs.size();
		return averageTime/1000.0;
		
	}

	@Test
	public void test1() {
		Chooser<String> c = new ChooserImpl<String>();
		c.add("a", 5);
		c.add("b", 2.5);
		c.add("c", 2.5);
		String choices = "";
		for(int i=0;i<1000;i++){choices=choices+c.choose();}
		int countA = choices.replaceAll("[^a]", "").length();
		int countB = choices.replaceAll("[^b]", "").length();
		int countC = choices.replaceAll("[^c]", "").length();
		assertTrue((countA+countB+countC)==1000);
		System.out.println(choices);
		
		System.out.println("countA:"+countA+", countB="+countB+", countC="+countC);
		assertTrue(countA>(500-(.25*500)));
		assertTrue(countA<(500+(.25*500)));
		assertTrue(countB>(250-(.25*250)));
		assertTrue(countB<(250+(.25*250)));
		assertTrue(countC>(250-(.25*250)));
		assertTrue(countC<(250+(.25*250)));
		
	}
	
	@Test
	public void test2() {
		Chooser<String> c = new ChooserImpl2<String>();
		c.add("a", 5);
		c.add("b", 2.5);
		c.add("c", 2.5);
		String choices = "";
		for(int i=0;i<1000;i++){choices=choices+c.choose();}
		int countA = choices.replaceAll("[^a]", "").length();
		int countB = choices.replaceAll("[^b]", "").length();
		int countC = choices.replaceAll("[^c]", "").length();
		assertTrue((countA+countB+countC)==1000);
		System.out.println(choices);
		
		System.out.println("countA:"+countA+", countB="+countB+", countC="+countC);
		assertTrue(countA>(500-(.25*500)));
		assertTrue(countA<(500+(.25*500)));
		assertTrue(countB>(250-(.25*250)));
		assertTrue(countB<(250+(.25*250)));
		assertTrue(countC>(250-(.25*250)));
		assertTrue(countC<(250+(.25*250)));
		
	}
	@Test
	public void test3() {
		Chooser<String> c = new ChooserImpl3<String>();
		c.add("a", 5);
		c.add("b", 2.5);
		c.add("c", 2.5);
		String choices = "";
		for(int i=0;i<1000;i++){choices=choices+c.choose();}
		int countA = choices.replaceAll("[^a]", "").length();
		int countB = choices.replaceAll("[^b]", "").length();
		int countC = choices.replaceAll("[^c]", "").length();
		assertTrue((countA+countB+countC)==1000);
		System.out.println(choices);
		
		System.out.println("countA:"+countA+", countB="+countB+", countC="+countC);
		assertTrue(countA>(500-(.25*500)));
		assertTrue(countA<(500+(.25*500)));
		assertTrue(countB>(250-(.25*250)));
		assertTrue(countB<(250+(.25*250)));
		assertTrue(countC>(250-(.25*250)));
		assertTrue(countC<(250+(.25*250)));
		
	}


}
