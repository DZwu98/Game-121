import edu.upenn.cis121.project.ConnectedComponents;
import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.graph.SimpleGraph;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 * Provides access to canonical connected components algorithms for a graph.
 *
 * @param <V> the type of vertices of the graph. The vertices must implement {@link Comparable}, in
 * order to provide a deterministic ordering for the iterative depth-first order iterator.
 *
 * For testing purposes, this class must provide a public default no-argument constructor. This
 * means that you do not need to write any constructor at all; if you do write other constructors,
 * be sure you have one that is public and takes no arguments.
 *
 * @author davix
 */
public class ConnectedComponentsImpl<V extends Comparable<V>>
        implements ConnectedComponents<V> {
	
	//constructor
	public ConnectedComponentsImpl(){
		
	}

    /**
     * Returns an {@link Iterator} over the vertices of a specified graph in depth-first order. The
     * vertices will be visited according to their {@linkplain Comparable natural ordering}.
     *
     * @param graph the graph
     * @return a lazy iterator over vertices of the graph, in depth-first order
     */
    @Override
    public Iterator<V> iterativeDepthFirstOrderIterator(SimpleGraph<V> graph) {
    	//we create another java class for the iterator
    	ForestIterator<V> it = new ForestIterator<V>(graph);
        return it;
    }

    /**
     * Computes the weakly connected components of the specified graph.
     *
     * @param graph the graph
     * @return an immutable set of sets of vertices that comprise the weakly connected components
     * of the specified graph
     */
    @Override
    public Set<Set<V>> weaklyConnectedComponents(SimpleGraph<V> graph) {
        Set<Set<V>> result = new HashSet<Set<V>>();
        ForestIterator<V> it;
        //check if the graph is directed; if not, we leave it be; if it is, we need to wrap
        //it in a class so that when iterator calls for outNeighbors we include all neighbors
        if(graph instanceof DirectedGraph){
        	WeaklyConGraphWrapper<V> weak = new WeaklyConGraphWrapper<V>(graph);
        	it = (ForestIterator<V>) iterativeDepthFirstOrderIterator(weak);
        }else{
        	it = (ForestIterator<V>) iterativeDepthFirstOrderIterator(graph);
        }
        
        while(it.hasNext()){
        	Set<V> curComponent = new HashSet<V>();
        	//always initiate the set with a next() call; otherwise,
        	//we will be stuck at the nested while loop
        	curComponent.add(it.next());
        	while(it.hasNextInComponent()){
        		V v = it.nextInComponent();
        		curComponent.add(v);
        	}
        	
        	result.add(curComponent);
        }
        return result;
    }
    

    /**
     * Computes the strongly connected components of the specified graph.
     *
     * @param graph the graph
     * @return an immutable set of sets of vertices that comprise the strongly connected components
     * of the specified graph
     */
    @Override
    public Set<Set<V>> stronglyConnectedComponents(DirectedGraph<V> graph) {
    	Set<Set<V>> result = new HashSet<Set<V>>();
    	ForestIterator<V> it = (ForestIterator<V>) iterativeDepthFirstOrderIterator(graph);
    	//we run the entire DFS
    	while(it.hasNext()){
    		it.next();
    	}
    	//get reverse post order
    	Stack<V> ReversePostOrder = it.ReturnReversePostOrder();
        //get the transpose graph
    	TransposeGraphWrapper<V> g = new TransposeGraphWrapper<V>(graph);
        ForestIterator<V> newIt = (ForestIterator<V>) iterativeDepthFirstOrderIterator(g);
    	//output every component
    	while(!ReversePostOrder.isEmpty()){
    		V v = ReversePostOrder.pop();
    		if(!newIt.visited(v)){
        		newIt.Start(v);
        		Set<V> set = new HashSet<V>();
        		while(newIt.hasNextInComponent()){
        			set.add(newIt.nextInComponent());
        		}
        		result.add(set);
    		}
    	}
        return result;
    }
}
