import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.upenn.cis121.project.graph.DirectedGraph;


public class DirectedGraphImpl<V> implements DirectedGraph<V> {
	private Set<V> vertices;
	private HashMap<V, V> edgeList;
	private HashMap<V, HashSet<V>> inList;
	private HashMap<V, HashSet<V>> outList;
	
	//constructor
	public DirectedGraphImpl(Set<V> ver){
		vertices = new HashSet<V>();
		edgeList = new HashMap<V, V>();
		inList = new HashMap<V, HashSet<V>>();
		outList = new HashMap<V, HashSet<V>>();
		for(V v: ver){
			vertices.add(v);
			inList.put(v, new HashSet<V>());
			outList.put(v, new HashSet<V>());
		}
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
			throw new IllegalArgumentException();
		}
	
		return Collections.unmodifiableSet(inList.get(arg0));
	}

	@Override
	public Set<V> outNeighbors(V arg0) {
		if(!vertices.contains(arg0)){
			throw new IllegalArgumentException();
		}
		return Collections.unmodifiableSet(outList.get(arg0));
	}
	
	public void add(V u, V v){
		if(!vertices.contains(u) || !vertices.contains(v)){
			throw new IllegalArgumentException();
		}
		edgeList.put(u, v);
		HashSet<V> s = inList.get(v);
		s.add(u);
		inList.put(v, s);
		HashSet<V> r = outList.get(u);
		r.add(v);
		outList.put(u, r);
	}

}
