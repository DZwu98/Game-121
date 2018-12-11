import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class RecordInvertedIndexImplTest {
	private RecordInvertedIndexImpl<Integer> record = new RecordInvertedIndexImpl<Integer>();
	
	@Before
	public void init(){
		record.addRecord("a", 1);
		record.addRecord("s", 2);
		record.addRecord("c", 1);
		record.addRecord("a", 2);
	}

	@Test
	public void testAddNull() {
		try{
			record.addRecord(null, 1);
			fail("no exceptions thrown");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void getNull(){
		assertTrue(record.getRecords(null) == null);
	}
	
	@Test
	public void getNormal(){
		Collection<Integer> c = new HashSet<Integer>();
		c.add(1);
		c.add(2);
		assertEquals(record.getRecords("a"), c);
	}
	
	@Test
	public void getNotInthere(){
		assertTrue(record.getRecords("snake") == null);
	}
	
	@Test
	public void ContainsNull(){
		assertFalse(record.containsKey(null));
	}
	
	@Test
	public void Contains(){
		assertTrue(record.containsKey("s"));
	}
	
	@Test
	public void NotContains(){
		assertFalse(record.containsKey("haha"));
	}
	
	
	@Test
	public void deleteNull(){
		assertTrue(record.deleteRecords(null) == null);
	}
	
	@Test
	public void deleteNormal(){
		Collection<Integer> c = new HashSet<Integer>();
		c.add(2);
		assertEquals(record.deleteRecords("s"), c);
		assertTrue(!record.containsKey("s"));
	}
	
	@Test
	public void keySet(){
		Set<String> s = new HashSet<String>();
		s.add("a");
		s.add("s");
		s.add("c");
		assertEquals(record.keySet(), s);
	}
	
	@Test
	public void size(){
		assertTrue(record.size() == 3);
	}
	
	@Test
	public void count(){
		assertTrue(record.count("a") == 2);
	}
	
	@Test
	public void countNull(){
		assertTrue(record.count(null) == 0);
	}

}
