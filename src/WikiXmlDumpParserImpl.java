import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import edu.upenn.cis121.project.WikiXmlDumpParser;
import edu.upenn.cis121.project.data.AbstractIdentifiable;
import edu.upenn.cis121.project.graph.DirectedGraph;
import edu.upenn.cis121.project.util.mediawiki.MediaWikiParseException;
import edu.upenn.cis121.project.util.mediawiki.MediaWikiText;
import  edu.upenn.cis121.project.util.mediawiki.MediaWikiUtils;


public class WikiXmlDumpParserImpl implements WikiXmlDumpParser {

	@Override
	public DirectedGraphImpl<WikiNode> parseXmlDump(File arg0)
			throws IOException, XMLStreamException {
		
		
		// Create the factory first
		XMLInputFactory xmlif = XMLInputFactory.newInstance ();
		DirectedGraphImpl<WikiNode> g;
		String title = "";
		String curFormat = "";
		String curText = "";
		Map<String, WikiNode> mapping = new HashMap<String, WikiNode>();
		Set<WikiNode> s = new HashSet<WikiNode>();
		MediaWikiText mwk = null;
		try ( BufferedReader br = Files.newBufferedReader (arg0.toPath())) {		
			
			//first pass to create a mapping from the title to wikiNode
		    // Get a reader for XML from the factory
		    XMLEventReader r = xmlif.createXMLEventReader(br) ;
		    while ( r.hasNext()) {
			    XMLEvent event = r.nextEvent();
			    if(event.isStartElement()){
			    	StartElement e = event.asStartElement();
			    	String tag = e.getName().getLocalPart();
			    	//match the string
			    	if(tag.equals("title")){
			    		title = r.getElementText();
			    	}else if(tag.equals("text")){
			    		//check what kind of format it is
			    		if(curFormat.equals("text/plain")){
			    			curText = r.getElementText();
			    			WikiNode n = new WikiNode(title, null, curText);
			    			mapping.put(title, n);
			    			s.add(n);
			    		}else if(curFormat.equals("text/x-wiki")){
			    			curText = r.getElementText();
			    			try{
			    				mwk = MediaWikiUtils.parseWikiText(curText);
			    			}catch(MediaWikiParseException ex){
			    				
			    			}
			    			WikiNode n = new WikiNode(title, mwk.getOutlinks(), mwk.getText());
			    			mapping.put(title, n);
			    			s.add(n);
			    		}
			    	}else if(tag.equals("format")){
			    		//record the format
			    		event = r.nextEvent();
			    		curFormat = event.asCharacters().getData();
			    	}
			    }
		    }
		}catch(IOException | XMLStreamException e){
	    	e.printStackTrace();
	    }
		
		
	    //now construct the graph using the set we have in our previous pass
	    g = new DirectedGraphImpl<WikiNode>(s);
	    
	    try ( BufferedReader br = Files.newBufferedReader (arg0.toPath())){
	    	XMLEventReader r = xmlif.createXMLEventReader(br) ;
		    r = xmlif.createXMLEventReader(br) ;
		    //in the second pass, we add the edge.
		    //acquire a new event reader
		    while ( r.hasNext()) {
			    XMLEvent event2 = r.nextEvent();
			    if(event2.isStartElement()){
			    	StartElement e = event2.asStartElement();
			    	String tag = e.getName().getLocalPart();
			    	//match the string
			    	if(tag.equals("title")){
			            title = r.getElementText();
			    	}else if(tag.equals("text")){
			    		//check the format;since this is the 2nd pass, we only care about outlink
			    		if(curFormat.equals("text/x-wiki")){
			    			//get the current wikinode derived in last iteration
			    			WikiNode n = mapping.get(title);
			    			List<String> l = n.getContent();
			    			for(String st: l){
			    				//add the corresponding edge to the graph
			    				g.add(n, mapping.get(st));
			    			}
			    		}
			    	}else if(tag.equals("format")){
			    		curFormat = r.getElementText();
			    	}
			    }   	
		    }
	    }catch(IOException | XMLStreamException e){
	    	e.printStackTrace();
	    }


		return g;
	}

}
