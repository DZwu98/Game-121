import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.graph.SimpleGraph;


public class ConnectedComponentsImplTest {
	
	private DirectedGraphImpl<Integer> G1;
	
	@Before
	public void init(){
		Set<Integer> g1 = new HashSet<Integer>();
		for(Integer i = 0; i < 5; i++){
			g1.add(i);
		}
		G1 = new DirectedGraphImpl<Integer>(g1);
		for(Integer i = 0; i < 4; i++){
			G1.add(i, i + 1);
		}
		G1.add(2, 4);
		
	}

	@Test
	public void testWeakWrapper() {
		WeaklyConGraphWrapper<Integer> r = new WeaklyConGraphWrapper<Integer>(G1);
		Set<Integer> s1 = G1.outNeighbors(1);
		Set<Integer> s2 = r.outNeighbors(1);
		Set<Integer> a1 = new HashSet<Integer>();
		a1.add(2);
		Set<Integer> a2 = new HashSet<Integer>();
		a2.add(0);
		a2.add(2);
		assertEquals(s1, a1);
		assertEquals(s2, a2);
	}
	
	@Test
	public void NormalIterator(){
		ForestIterator<Integer> it = new ForestIterator<Integer>(G1);
		Integer i = 0;
		while(it.hasNext()){
			assertTrue(i == it.next());
			i++;
		}
		//System.out.println(it.ReturnReversePostOrder());
	}
	
	@Test
	public void hey(){
		DirectedGraph<Integer> g1 = G1;
		SimpleGraph<Integer> g 	= (SimpleGraph<Integer>)(new WeaklyConGraphWrapper<Integer>(G1));
		assertTrue(g instanceof WeaklyConGraphWrapper);
	}
	
	@Test
	public void WCC(){
		ConnectedComponentsImpl<Integer> c = new ConnectedComponentsImpl<Integer>();
		TransposeGraphWrapper<Integer> g = new TransposeGraphWrapper<Integer>(G1); 
		System.out.println(c.weaklyConnectedComponents(G1));
	}

}
