import java.util.ArrayList;
import java.util.HashMap;

import edu.upenn.cis121.project.graph.DoubleWeightedDirectedGraph;
import edu.upenn.cis121.project.graph.ShortestPath;

/**
 * @param <V> {@inheritDoc}
 *
 * @author eyeung, 16sp
 */
public class ShortestPathImpl<V> implements ShortestPath<V> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<V> getShortestPath(DoubleWeightedDirectedGraph<V> G, V src, V tgt) {
    	//exceptions
    	if(G == null|| src == null || tgt == null || 
    			!G.vertexSet().contains(src) || !G.vertexSet().contains(tgt)){
    		throw new IllegalArgumentException();
    	}
    	
    	
    	ArrayList<V> out = new ArrayList<V>(); 
    	ArrayList<V> output = new ArrayList<V>();
    	//if the source and tgt are the same, we just output empty list
    	if(tgt != src){
        	BinaryMinHeapImpl<V, Double> heap = new BinaryMinHeapImpl<V, Double>();
        	HashMap<V, Double> store = new HashMap<V, Double>();
        	//add all vertices to the heap with maximum distance
        	for(V v: G.vertexSet()){
        		heap.add(v, Double.MAX_VALUE);
        		store.put(v, Double.MAX_VALUE);
        	}
        	heap.decreaseKey(src, 0.0);
        	store.put(src, 0.0);
        	//maintain a map of parent vertices for the shortes path
        	HashMap<V, V> parent = new HashMap<V, V>();
        	V last = src;
        	
        	while(heap.containsValue(tgt)){
        		V vertex = heap.extractMin();
        		//put it in our list
        		//out.add(vertex);
        		
        		//relax
        		for(V v: G.outNeighbors(vertex)){
        			Double dist = store.get(vertex) + G.getWeight(vertex, v).get();
        			if(store.get(v) > dist){
        				heap.decreaseKey(v, dist);
        				store.put(v, dist);
        				//when we relax, this vertex will be in the temporary min dist tree
        				parent.put(v, vertex);
        			}
        		}
        		last = vertex;
        	}
        	out.add(tgt);
        	
        	//see if we can reach tgt from src; if not, the parent map does not contain it
        	//if it contains it, we print the path
        	if(parent.containsKey(tgt)){
        		//print the path we need from the parent map
            	while(!parent.get(tgt).equals(src)){
            		tgt = parent.get(tgt);	
            		out.add(tgt);	
            	}
            	out.add(src);
            	
            	//notice the list is reversed. We reverse it back
            	for(int i = out.size()-1; i >= 0; i--){
            		output.add(out.get(i));
            	}	
        	}
    	}
        return output;
    }
}
