import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.graph.DoubleWeightedDirectedGraph;


public class ShortestPathImplTest {
	
	//We use intege to denote vetex in this test
	
	ShortestPathImpl<Integer> s = new ShortestPathImpl<Integer>();
	DoubleWeightedDirectedGraphImpl<Integer> G1;
	DoubleWeightedDirectedGraphImpl<Integer> G2;
	
	@Before
	public void initialize(){
		HashSet<Integer> s1 = new HashSet<Integer>();
		for(int i = 0; i < 10; i++){
			s1.add(i);
		}
		G1 = new DoubleWeightedDirectedGraphImpl<Integer>(s1);
		G1.addEdge(1, 2, 1.0);
		G1.addEdge(1, 3, 0.1);
		G1.addEdge(3, 4, 0.2);
		G1.addEdge(4, 2, 0.3);
		G2 = new DoubleWeightedDirectedGraphImpl<Integer>(s1);
		G2.addEdge(1, 2, 1.2);
		G2.addEdge(1, 3, 0.7);
		G2.addEdge(2, 4, 1.4);
		G2.addEdge(4, 6, 1.6);
		G2.addEdge(2, 5, 1.0);
		G2.addEdge(5, 6, 20.1);
		G2.addEdge(3, 5, 1.5);
		G2.addEdge(3, 7, 0.2);
	}
	
	@Test
	public void testGisNull() {
		try{
			s.getShortestPath(null, 1, 2);
			fail("no exceptions thrown");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void testsSrcisNull() {
		HashSet<Integer> set = new HashSet<Integer>();
		set.add(2);
		DoubleWeightedDirectedGraphImpl<Integer> G = 
				new DoubleWeightedDirectedGraphImpl<Integer>(set);
		try{
			s.getShortestPath(G, null, 2);
			fail("no exceptions thrown");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void testsTgtisNull() {
		HashSet<Integer> set = new HashSet<Integer>();
		set.add(1);
		DoubleWeightedDirectedGraphImpl<Integer> G = 
				new DoubleWeightedDirectedGraphImpl<Integer>(set);
		try{
			s.getShortestPath(G, 1, null);
			fail("no exceptions thrown");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void testsSrcNoneExist() {
		HashSet<Integer> set = new HashSet<Integer>();
		set.add(2);
		DoubleWeightedDirectedGraphImpl<Integer> G 
		= new DoubleWeightedDirectedGraphImpl<Integer>(set);
		try{
			s.getShortestPath(G, 1, 2);
			fail("no exceptions thrown");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void testsTgtNoneExist() {
		HashSet<Integer> set = new HashSet<Integer>();
		set.add(2);
		DoubleWeightedDirectedGraphImpl<Integer> G 
		= new DoubleWeightedDirectedGraphImpl<Integer>(set);
		try{
			s.getShortestPath(G, 2, 1);
			fail("no exceptions thrown");
		}catch(IllegalArgumentException e){
			
		}
	}
	
	@Test
	public void testSmallGraph(){
		Iterable<Integer> l = s.getShortestPath(G1, 1, 2);
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(3);
		list.add(4);
		list.add(2);
		int index = 0;
		for(Integer c: l){
			assertEquals(c, list.get(index));
			index++;
		}
	}
	
	@Test
	public void testSameVertices(){
		Iterable<Integer> l = s.getShortestPath(G1, 1, 1);
		assertFalse(l.iterator().hasNext());
	}
	
	@Test
	public void testLargerGraphAndNoPath(){
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list1.add(1);
		list1.add(2);
		list1.add(4);
		list1.add(6);
		list2.add(5);
		list2.add(3);
		list2.add(7);
		Iterable<Integer> l1 = s.getShortestPath(G2, 1, 6);
		Iterable<Integer> l2 = s.getShortestPath(G2, 5, 7);
		int index = 0;
		for(Integer c: l1){
			assertEquals(c, list1.get(index));
			index++;
		}
		assertFalse(l2.iterator().hasNext());
	}

}
