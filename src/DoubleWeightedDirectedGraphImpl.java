import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import edu.upenn.cis121.project.graph.DoubleWeightedDirectedGraph;


public class DoubleWeightedDirectedGraphImpl<V> implements
		DoubleWeightedDirectedGraph<V> {
	private Set<V> vertices;
	private HashMap<V, HashMap<V, Double>> edgeList;
	private HashMap<V, HashSet<V>> inList;
	private HashMap<V, HashSet<V>> outList;
	
	public DoubleWeightedDirectedGraphImpl(Set<V> ver){
		vertices = new HashSet<V>();
		edgeList = new HashMap<V, HashMap<V, Double>>();
		inList = new HashMap<V, HashSet<V>>();
		outList = new HashMap<V, HashSet<V>>();
		for(V v: ver){
			vertices.add(v);
			edgeList.put(v, new HashMap<V, Double>());
			inList.put(v, new HashSet<V>());
			outList.put(v, new HashSet<V>());
		}
	}
	

	@Override
	public Optional<Double> getWeight(V arg0, V arg1) {
        if(!vertices.contains(arg0) || !vertices.contains(arg1)){
        	throw new IllegalArgumentException();
        }      
        if(!edgeList.get(arg0).containsKey(arg1)){
        	throw new NoSuchElementException();
        }
        
        return Optional.of(edgeList.get(arg0).get(arg1));
	}

	@Override
	public Set<V> neighbors(V arg0) {
		if(!vertices.contains(arg0)){
			throw new IllegalArgumentException();
		}
		HashSet<V> out = inList.get(arg0);
		out.addAll(outList.get(arg0));
		return Collections.unmodifiableSet(out);
	}

	@Override
	public Set<V> vertexSet() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableSet(vertices);
	}

	@Override
	public Set<V> inNeighbors(V arg0) {
		if(!vertices.contains(arg0)){
			throw new NoSuchElementException();
		}
		return Collections.unmodifiableSet(inList.get(arg0));
	}

	@Override
	public Set<V> outNeighbors(V arg0) {
		if(!vertices.contains(arg0)){
			throw new NoSuchElementException();
		}
		return Collections.unmodifiableSet(outList.get(arg0));
	}
	
	void addEdge(V arg0, V arg1, Double edge){
		HashSet<V> in = new HashSet<V>();
		in = inList.get(arg1);
		in.add(arg0);
		inList.put(arg1, in);
		
		HashSet<V> out = new HashSet<V>();
		out = outList.get(arg0);
		out.add(arg1);
		outList.put(arg0, out);
		
		HashMap<V, Double> l = new HashMap<V, Double>();
		l = edgeList.get(arg0);
	    l.put(arg1, edge);
		edgeList.put(arg0, l);

		
	}
	

}
