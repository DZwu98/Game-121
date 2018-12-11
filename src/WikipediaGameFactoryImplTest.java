import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis121.project.engine.WikipediaGame;
import edu.upenn.cis121.project.engine.WikipediaGame.EdgeWeightType;


public class WikipediaGameFactoryImplTest {
	DoubleWeightedDirectedGraphImpl<WikiNode> G1;
	DoubleWeightedDirectedGraphImpl<WikiNode> G2;
	private WikiNode a1 = new WikiNode("a1", null, null);
	private WikiNode a2 = new WikiNode("a2", null, null);
	private WikiNode a3 = new WikiNode("a3", null, null);
	private WikiNode a4 = new WikiNode("a4", null, null);
	private WikiNode a5 = new WikiNode("a5", null, null);
	private WikiNode a6 = new WikiNode("a6", null, null);
	private WikiNode a7 = new WikiNode("a7", null, null);
	private WikipediaGameFactoryImpl w = new WikipediaGameFactoryImpl();
	
	@Before
	public void initialize(){
		HashSet<WikiNode> s1 = new HashSet<WikiNode>();
		s1.add(a1);
		s1.add(a2);
		s1.add(a3);
		s1.add(a4);
		s1.add(a5);
		s1.add(a6);
		s1.add(a7);
		
		G1 = new DoubleWeightedDirectedGraphImpl<WikiNode>(s1);
		G1.addEdge(a1, a2, 1.0);
		G1.addEdge(a1, a3, 0.1);
		G1.addEdge(a3, a4, 0.2);
		G1.addEdge(a4, a2, 0.3);
		G2 = new DoubleWeightedDirectedGraphImpl<WikiNode>(s1);
		G2.addEdge(a1, a2, 1.2);
		G2.addEdge(a1, a3, 0.7);
		G2.addEdge(a2, a4, 1.4);
		G2.addEdge(a4, a6, 1.6);
		G2.addEdge(a2, a5, 1.0);
		G2.addEdge(a5, a6, 20.1);
		G2.addEdge(a3, a5, 1.5);
		G2.addEdge(a3, a7, 0.2);
	}

	@Test
	public void testNecessity() {
		WikipediaGame<WikiNode> s = w.create(G1); 
		ArrayList<WikiNode> degree = new ArrayList<WikiNode>();
		degree.add(a1);
		degree.add(a2);
		assertEquals(degree, s.solveWikiGame(EdgeWeightType.DEGREE, a1, a2));
	}

}
