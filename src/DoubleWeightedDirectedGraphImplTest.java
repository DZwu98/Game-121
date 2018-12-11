import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;


public class DoubleWeightedDirectedGraphImplTest {
	HashSet<Integer> set1 = new HashSet<Integer>();
	HashSet<Integer> set2 = new HashSet<Integer>();
	DoubleWeightedDirectedGraphImpl<Integer> g;
	
	@Before
	public void initialize(){
		for(int i = 0; i< 10; i++){
			set1.add(i);
		}
		g = new DoubleWeightedDirectedGraphImpl<Integer>(set1);
		for(int i = 0; i< 9; i++){
			g.addEdge(i, i+1, i*0.1);
		}
		g.addEdge(1, 3, 0.1);
		g.addEdge(1, 4, 0.1);
		
	}

	@Test
	public void GetWeightArg0NotExist() {
		try{
			g.getWeight(100, 1);
			fail("no exceptions thrown");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void GetWeightArg1NotExist() {
		try{
			g.getWeight(1, 100);
			fail("no exceptions thrown");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void GetWeightNoEdge() {
		try{
			g.getWeight(2, 1);
			fail("no exceptions thrown");
		}catch(NoSuchElementException e){
			
		}
	}
	
	@Test
	public void GetWeightExist(){
		assertTrue(g.getWeight(1, 2).get().equals(0.1));
	}
	
	@Test
	public void NeighboursExceptions(){
		try{
			g.neighbors(22);
			fail("no exceptions thrown");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void NeighboursSucces(){
		HashSet<Integer> s = new HashSet<Integer>();
		s.add(5);
		s.add(7);
		assertEquals(s, g.neighbors(6));
	}
	
	@Test
	public void vetexSet(){
		HashSet<Integer> s = new HashSet<Integer>();
		for(int i = 0; i< 10; i++){
			s.add(i);
		}
		assertEquals(s, g.vertexSet());
	}
	
	@Test
	public void inNeighboursExceptions(){
		try{
			g.inNeighbors(11);
			fail("no exceptions thrown");
		}catch(NoSuchElementException e){
			
		}
	}
	
	@Test
	public void inNeighboursSuccess(){
		HashSet<Integer> s = new HashSet<Integer>();
		s.add(1);
		s.add(2);
		assertEquals(s, g.inNeighbors(3));
	}
	
	@Test
	public void outNeighboursExceptions(){
		try{
			g.outNeighbors(11);
			fail("no exceptions thrown");
		}catch(NoSuchElementException e){
			
		}
	}
	
	@Test
	public void outNeighboursSuccess(){
		HashSet<Integer> s = new HashSet<Integer>();
		s.add(2);
		s.add(3);
		s.add(4);
		assertEquals(s, g.outNeighbors(1));
	}
	
	@Test
	public void addEdge(){
		g.addEdge(2, 7, 4.5);
		assertTrue(g.getWeight(2, 7).get().equals(4.5));
	}
	
	

}
