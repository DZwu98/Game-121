import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.upenn.cis121.project.data.AbstractIdentifiable;
import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.graph.DoubleWeightedDirectedGraph;
import edu.upenn.cis121.project.engine.WikipediaGame;

/**
 * Implementation of a solver for the Wikipedia game. It finds the shortest path between two
 * Wikipedia pages. In this case, it also solves the game when there is a twist: when it is
 * beneficial to go through less "important" Wikipedia pages. This twist is implemented by
 * altering the edge weights on the graph. The constructor for the class takes in the
 * unweighted, directed graph you created from the Wikidump.
 * <p>
 * You are free to implement this class however you like. We will be testing your code through
 * the {@link WikipediaGameFactoryImpl} class.
 */
class WikipediaGameImpl<V extends AbstractIdentifiable> implements WikipediaGame<V> {
	
	private DoubleWeightedDirectedGraphImpl<V> degree;
	private DoubleWeightedDirectedGraphImpl<V> unweighted;

    public WikipediaGameImpl(DirectedGraph<V> g) {
        Set<V> s = g.vertexSet(); 
        degree = new DoubleWeightedDirectedGraphImpl<V>(s);
        unweighted = new DoubleWeightedDirectedGraphImpl<V>(s);
        //now we need to calculate the edge for this two graph      
        //this is done by looping over all out going edges for all vertices
        for(V v : g.vertexSet()){
        	for(V c: g.outNeighbors(v)){
        		//here we add each c into the graph, as an edge of v-c
        		unweighted.addEdge(v, c, 1.0);
        		//for degree, we check for every vetex, its target's indegree
        		double indegree = (double) g.inNeighbors(c).size();
        		degree.addEdge(v, v, (Double) indegree);
        	}
        }
    }

    /**
     * Solves the Wikipedia Game.
     *
     * @param e   how the edges should be weighted. DEGREE means that each
     *            edge has weight equal to the in-degree of its target node and
     *            UNWEIGHTED means all edges have weight equal to 1.
     * @param src the starting page
     * @param tgt the ending page
     * @throws IllegalArgumentException if any of the parameters are invalid
     * @return - the shortest path (considering the edge weights) from src to tgt
     *         - an empty Iterable if there is no path
     *         - an Iterable consisting of just src if src.equals(tgt)
     */
    @Override
    public Iterable<V> solveWikiGame(EdgeWeightType e, V src, V tgt) {
    	if(e == null || src == null || tgt == null){
    		throw new IllegalArgumentException();
    	}
    	
    	ArrayList<V> result = new ArrayList<V>();
    	//check if the src is tgt; if not, we need to solve it; if it is, add the src to the list
    	if(src != tgt){
    		ShortestPathImpl<V> solver = new ShortestPathImpl<V>();
    		//check the input type
    		switch (e){
    		case DEGREE : result = 
    				(ArrayList<V>) solver.getShortestPath(this.degree, src, tgt);
    		case UNWEIGHTED : result = 
    				(ArrayList<V>) solver.getShortestPath(this.unweighted, src, tgt);
    		}
    	}else{
    		result.add(src);
    	}
        return result;
    }
}