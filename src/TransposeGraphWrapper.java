import java.util.Set;

import edu.upenn.cis121.project.graph.DirectedGraph;


public class TransposeGraphWrapper<V extends Comparable<V>> implements DirectedGraph<V> {

	private DirectedGraph<V> g;
	
	//constructor
	public TransposeGraphWrapper(DirectedGraph<V> g){
		this.g = g;
	}
	@Override
	public Set<V> neighbors(V arg0) {
		
		return g.neighbors(arg0);
	}

	@Override
	public Set<V> vertexSet() {
		
		return g.vertexSet();
	}

	@Override
	public Set<V> inNeighbors(V arg0) {
		//transpose will return outNeighbors
		return g.outNeighbors(arg0);
	}

	@Override
	public Set<V> outNeighbors(V arg0) {
		// transpose will return inNeighbors
		return g.inNeighbors(arg0);
	}

}
