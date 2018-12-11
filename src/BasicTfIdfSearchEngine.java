import edu.upenn.cis121.project.engine.SearchEngine;
import edu.upenn.cis121.project.engine.SearchResult;

import javax.xml.stream.XMLStreamException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author davix
 */
public class BasicTfIdfSearchEngine implements SearchEngine {
	private DirectedGraphImpl<WikiNode> graph;
	private RecordInvertedIndexImpl<String> totalRecord;
	private Map<String, Map<String, Integer>> individualRecord;
	private Integer size;

    /**
     * Loads a dataset from a particular XML file. Guaranteed to be called exactly once before
     * calling any other methods.
     *
     * @param file the file
     * @throws IOException if an I/O error occurs
     * @throws XMLStreamException if an error occurs during XML processing
     */
    @Override
    public void loadXmlDump(File file) throws IOException, XMLStreamException {
    	try{
    		WikiXmlDumpParserImpl p = new WikiXmlDumpParserImpl();
    		this.graph = p.parseXmlDump(file);
    	}catch(IOException e){
    		System.out.println("IOExceptions");
    	}catch(XMLStreamException e){
    		System.out.println("XMLStreamExceptions");
    	} 	
    	this.size = this.graph.vertexSet().size();
    	
    	//put all the words inside the recordInvertedIndexImpl
    	for(WikiNode n: this.graph.vertexSet()){
    		//split the text body
    		String[] text = n.getBody().split("\\s+");
    		//the record of the number of time each word appears in the current map
    	    HashMap<String, Integer> curMap = new HashMap<String, Integer>();
    		//remove non character, and lower all cases
    		for(int i = 0; i < text.length; i++){
    			text[i] = text[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
    			//update the current recorder through the words of the current page
    			totalRecord.addRecord(text[i], n.getId());
    			//increment the counter for curMap
    			if(curMap.containsKey(text[i])){
    				Integer c = curMap.get(text[i]) + 1;
    				curMap.put(text[i], c);
    			}else{
    				curMap.put(text[i], 1);
    			}
    		}
    		//we add the curMap to the field of individualRecord
    		individualRecord.put(n.getId(), curMap);
    	}	
    }

    /**
     * Retrieves search results for a query in order of descending relevance.
     *
     * @param text the query
     * @param maxResults the maximum number of results to return
     * @return search results for the specified query in order of descending relevance
     */
    @Override
    public Iterable<? extends SearchResult> search(String text,
                                                   int maxNumResults) {
        ArrayList<SearchResult> results = new ArrayList<SearchResult>();
        PriorityQueue<entry> pr = new PriorityQueue<entry>();
        String[] s = text.split("\\s+");
        //split the input string and remove all non character; then lower its cases
        for(int i = 0; i < s.length; i++){
        	s[i] = s[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
        }
        //for each document, calculate its td-idf score for our query,
        //and put it in the priority queue
        for(WikiNode n: graph.vertexSet()){
        	String title = n.getId();
        	double TdIdf = 0;
        	//sum up all the scores for each words
        	for(int i = 0; i < s.length; i++){
        		//if the current document doesn't have it, 
        		//the score for current document is unchanged for now
        		if(individualRecord.get(title).containsKey(s[i])){
        			TdIdf += individualRecord.get(title).get(s[i]) *
        					Math.log(size/(1 + totalRecord.count(s[i])));
        		}
        	}
        	//now construct the entry, then put it in the priority queue
        	entry e = new entry(title, TdIdf);
        	pr.add(e);
        }
        //now, we finished priority queue for all documents; we need to pop off the 
        //top maxNumResults elements
        int i = 0;
        while(!pr.isEmpty() && i < maxNumResults){
        	//keep popping off the top and add it to the result
        	entry cur = pr.poll();
        	SearchResultImpl re = new SearchResultImpl(cur.getScore(), cur.getTitle());
        	results.add(re);
        	i++;
        }   
        return results;
    }
    
    //introduce a simple private class that helps compare the value of each document
    private class entry implements Comparable<entry>{
    	private String title;
    	private double score;
    	
    	//constructor
    	public entry(String t, double s){
    		this.title = t;
    		this.score = s;
    	}

		@Override
		public int compareTo(entry arg0) {
			return Double.compare(this.score, arg0.getScore());
		}
		
		public String getTitle(){
			return title;
		}
		
		public double getScore(){
			return score;
		}		
    }

}
