import edu.upenn.cis121.project.data.BinaryMinHeap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @param <V> {@inheritDoc}
 * @param <Key> {@inheritDoc}
 *
 * @author joncho, 16sp
 */
public class BinaryMinHeapImpl<V, Key extends Comparable<Key>> implements BinaryMinHeap<V, Key> {
	private ArrayList<Entry<V,Key>> heap;
	private HashSet<V> values;
	
	
	public BinaryMinHeapImpl(){
		heap = new ArrayList<Entry<V, Key>>();
		values = new HashSet<V>();
	}
	
	
    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        
        return heap.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(V value) {
        
        return values.contains(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(V value, Key key) {
        if(key == null || values.contains(value)){
        	throw new IllegalArgumentException();
        }
        //first add it to the end of the heap
        heap.add(new Entry<V, Key>(value, key));
        values.add(value);
        int i = heap.size() - 1;
        
        //recursively compare it to its parent, and swap them if the parent is bigger
        propogate(i);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseKey(V value, Key newKey) {
        if(!values.contains(value)){
        	throw new NoSuchElementException();
        }
        //go through the heap array to find value
        int i = 0;
        while(!heap.get(i).getValue().equals(value)){
        	i++;
        }
        //check if the new key is smaller
        if(heap.get(i).getKey().compareTo(newKey) < 0){
        	throw new IllegalArgumentException();
        }
        
        heap.set(i, new Entry<V, Key>(value, newKey));
        propogate(i);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V peek() {
        if(heap.size() == 0){
        	throw new NoSuchElementException();
        }
        return heap.get(0).getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V extractMin() {
    	if(heap.size() == 0){
    		throw new NoSuchElementException();
    	}
    	
        //swap the first index and the last index.
    	V out = heap.get(0).getValue();
    	int size = heap.size();
    	swap(0, size-1);
    	//also remove this element from our elements set
    	values.remove(out);
    	heap.remove(size-1);
    	//call minheapify from the start to regularize the heap
    	minheapify(0);
        return out;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<V> values() {

        return Collections.unmodifiableSet(values);
    }
    
    
    /**
     *helper method min heapify 
     */
    void minheapify(int i){
    	int l = left(i);
    	int r = right(i);
    	int smallest = i;
    	//check the left/right child and find the smallest value's index
    	if(l < heap.size() && heap.get(l).getKey().compareTo(heap.get(i).getKey()) < 0){
    		smallest = l;
    	}
    	if(r < heap.size() && heap.get(r).getKey().compareTo(heap.get(smallest).getKey()) < 0){
    		smallest = r;
    	}
    	//if our index is not the smallest, we swap it with its 
    	//smallest children and recursively call the method
    	if(smallest != i){
    		swap(i, smallest);
    		minheapify(smallest);
    	}
    }
    
    //helper methods to calculate index
    int parent(int i){
    	return (i-1)/2;
    }
    
    int left(int i){
    	return 2*i + 1;
    }
    
    int right(int i){
    	return 2*i + 2;
    }
    
    //helper method to swap to entries given the index
    void swap(int i, int j){
    	Entry<V, Key> temp = new Entry<V, Key>(heap.get(j).getValue(), heap.get(j).getKey());
    	heap.set(j, heap.get(i));
    	heap.set(i, temp);
    }
    
    //helper method to check the min invaraiant all the way to the top
    void propogate(int i){
        while(i != 0 && heap.get(parent(i)).key.compareTo(heap.get(i).key) > 0){
        	swap(i, parent(i));
            i = parent(i);
        }
    }
    
    /**
     * Helper entry class for maintaining value-key pairs.
     * The underlying indexed list for your heap will contain
     * these entries.
     *
     * You are not required to use this, but we recommend it.
     */
    class Entry<A, B> {

        private A value;
        private B key;

        public Entry(A value, B key) {
            this.value = value;
            this.key = key;
        }

        /**
         * @return  the value stored in the entry
         */
        public A getValue() {
            return this.value;
        }

        /**
         * @return  the key stored in the entry
         */
        public B getKey() {
            return this.key;
        }

    }



}
