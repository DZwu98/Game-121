import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.graph.SimpleGraph;


public class ForestIterator<V extends Comparable<V>> implements Iterator<V> {
	private Stack<V> stack;
	private Set<V> vertices = new HashSet<V>();
	private int size;
	private SimpleGraph<V> g;
	private Stack<V> ReversePostOrder;
	private Stack<V> buffer;
	private Map<V, Integer> map;

	//Constructor for default
	public ForestIterator(SimpleGraph<V> g){
		for(V v: g.vertexSet()){
			vertices.add(v);
		}
		stack = new Stack<V>();
		stack.push(Collections.min(g.vertexSet()));
		//vertices.remove(Collections.min(g.vertexSet()));
		size = g.vertexSet().size();
		this.g = g;
		ReversePostOrder = new Stack<V>();
		buffer = new Stack<V>();
		map = new HashMap<V, Integer>();
		//check if we need to implement additional function
		
		if(g instanceof DirectedGraph){
			DirectedGraph<V> d = (DirectedGraph<V>) g;
			for(V v: vertices){
				map.put(v, d.outNeighbors(v).size());
			}
		}
	}
	
	
	//start at a specific node; clear all contents
	public void Start(V v){
		//at this point we assume the stack is empty; this method is only called when 
		//we know hasNextInComponent is false
		//however, at the beginning, we need to clear the stack
		stack.clear();
		stack.push(v);
	}
	
	@Override
	public boolean hasNext() {
		return vertices.size() > 0;
	}

	@Override
	public V next(){
		//if we finished current component, add the least vertex in the remaining vertices
		if(!hasNextInComponent()){
			stack.push(Collections.min(vertices));
			//vertices.remove(Collections.min(vertices));
		}
		//simply use the method	
		return nextInComponent();
	}
	
	public V nextInComponent(){
		
		V out = stack.pop();
		if(!buffer.isEmpty()){
			//update the remaining unvisited neighbor count of the top element in buffer
			//(the last parent in the current path);
			//when the counter reaches 0, this parent is finished
			Integer c = map.get(buffer.peek()) - 1;
			map.put(buffer.peek(), c);
		}
		vertices.remove(out);
		buffer.push(out);
		//push this vertex to the buffer
		//visit this vertex
		//visited.add(out);
		Set<V> neighbours = new HashSet<V>();
		//check if our graph is directed or not

		if(g instanceof WeaklyConGraphWrapper || !(g instanceof DirectedGraph)){
			for(V v: g.neighbors(out)){
				neighbours.add(v);	
			}
		}else if(g instanceof DirectedGraph){
			//cast the graph and get neighbors
			DirectedGraph<V> d = (DirectedGraph<V>) g;
			for(V v: d.outNeighbors(out)){
				neighbours.add(v);	
			}
		}

		//push vertices on the stack 
		while(!neighbours.isEmpty()){
			//notice to visit the vertex in increasing order, 
			//we need to push them in decreasing order
			V v = Collections.max(neighbours);
			if(vertices.contains(v)){
				stack.push(v);
			}
			//remove the vertex from neighbors and the total set
			neighbours.remove(v);
		}
		//see whether we have finished any vertex; notice there could be multiple all at once
		while(!buffer.isEmpty()&&map.get(buffer.peek()) == 0){
			ReversePostOrder.push(buffer.pop());
		}
		while(!stack.isEmpty() && !vertices.contains(stack.peek())){
			stack.pop();
		}
		return out;
	}
	
	
	public boolean hasNextInComponent(){
		//we simply check if the stack is empty
		return !stack.isEmpty();
	}
	
	public Stack<V> ReturnReversePostOrder(){
		Stack<V> s = new Stack<V>();
		while(!ReversePostOrder.isEmpty()){
			s.push(ReversePostOrder.pop());
		}
		return s;
	}
	
	public boolean visited(V v){
		return !vertices.contains(v);
	}

}
