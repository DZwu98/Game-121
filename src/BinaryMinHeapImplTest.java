import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class BinaryMinHeapImplTest {
	
	private BinaryMinHeapImpl<Integer, Double> heap1 = new BinaryMinHeapImpl();
	
	@Before
	public void initialize(){
		
	}
	
	
	//first part we test helper methods

	@Test
	public void testParent() {
		assertEquals(3, heap1.parent(7));
	}
	
	@Test
	public void testLeft(){
		assertEquals(5, heap1.left(2));
	}
	
	@Test
	public void testRight(){
		assertEquals(12, heap1.right(5));
	}
	
	@Test
	public void testempty(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		assertTrue(heap.isEmpty());
	}
	
	@Test
	public void testPeekEmpty(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		try{
			heap.peek();
			fail("no exceptions");
		}catch(NoSuchElementException e){
			
		}
	}
		
	@Test
	public void testAddNullKey(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		try{
			heap.add(1, null);
			fail("no exceptions");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void testAddExistingValue(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 2.0);
		try{
			heap.add(1, 1.0);
			fail("no exceptions");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void testNotempty(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 2.0);
		assertTrue(!heap.isEmpty());
	}
	
	@Test
	public void testSize(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 2.0);
		assertEquals(2, heap.size());
	}
	
	@Test
	public void testContainValue(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 2.0);
		assertTrue(heap.containsValue(1));
		assertFalse(heap.containsValue(6));
	}
	
	@Test
	public void testDecreaseNoneExistent(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 2.0);
		try{
			heap.decreaseKey(4, 1.0);
			fail("no exceptions");
		}catch(NoSuchElementException e){
			
		}
	}
	
	@Test
	public void testDecreaseKeyLargerValue(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 2.0);
		try{
			heap.decreaseKey(1,20.0);
			fail("no exceptions");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void testPeek1(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 2.0);
		assertTrue(1 == heap.peek());
	}
	
	@Test
	public void testPeek2(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 1.4);
		heap.add(4, 0.3);
		heap.add(7, 0.02);
		heap.add(9, 100.0);
		heap.add(6, -1.2);
		assertTrue(6 == heap.peek());
	}
	
	@Test
	public void DecreaseKeyNoChange(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 1.4);
		heap.decreaseKey(1, 0.6);
		assertTrue(1 == heap.peek());
	}
	
	@Test
	public void DecreaseKeyChange(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 1.4);
		heap.add(4, 0.3);
		heap.add(7, 0.02);
		heap.add(9, 100.0);
		heap.decreaseKey(4, -1.7);

		assertTrue(4 == heap.peek());
	}
	
	@Test
	public void ExtractMinEmpty(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		try{
			heap.extractMin();
			fail("no exceptions");
		}catch(NoSuchElementException e){
			
		}
	}
	
	@Test
	public void ExtractMinSuccss(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 1.5);
		heap.add(3, 0.2);
		heap.add(4, 0.6);
		heap.add(5, 1.7);
		assertTrue(3 == heap.extractMin());
		assertTrue(4 == heap.extractMin());
		assertTrue(1 == heap.extractMin());
		assertTrue(2 == heap.extractMin());
		assertTrue(5 == heap.extractMin()); 
	}
	
	@Test
	public void testValues(){
		BinaryMinHeapImpl<Integer, Double> heap = new BinaryMinHeapImpl<Integer, Double>();
		heap.add(1, 1.0);
		heap.add(2, 1.5);
		heap.add(3, 0.2);
		heap.add(4, 0.6);
		heap.add(5, 1.7);
		Set<Integer> s = new HashSet<Integer>();
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(4);
		s.add(5);
		assertTrue(s.equals(heap.values()));
	}

	

}
