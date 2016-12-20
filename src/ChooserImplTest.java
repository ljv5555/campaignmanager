import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;


public class ChooserImplTest {

	@Test
	public void test() {
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

}
