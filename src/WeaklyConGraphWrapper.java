import java.util.Set;

import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.graph.SimpleGraph;


public class WeaklyConGraphWrapper<V extends Comparable<V>> implements DirectedGraph<V>{
	private DirectedGraph<V> g;
	//just get another graph
	public WeaklyConGraphWrapper(SimpleGraph<V> g) {
		this.g = (DirectedGraph<V>) g;
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
		
		return g.inNeighbors(arg0);
	}

	@Override
	public Set<V> outNeighbors(V arg0) {
		// for this one we just change it to neighbors
		return g.neighbors(arg0);
	}
	

}
