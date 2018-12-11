import java.util.List;
import java.util.Set;

import edu.upenn.cis121.project.data.AbstractIdentifiable;
import edu.upenn.cis121.project.util.mediawiki.MediaWikiText;

//each wikipage in the graph
public class WikiNode extends AbstractIdentifiable {
	private final List<String> outbounds;
	private final String textbody;

	public WikiNode(String id, List<String> out, String text) {
		super(id);
		this.outbounds = out;
		this.textbody = text;
		// TODO Auto-generated constructor stub
	}
	
	//get the outlinks
	public List<String> getContent(){
		return this.outbounds;
	}
	
	//get textbody
	public String getBody(){
		return this.textbody;
	}
	
	
	
	public boolean isNode(){
		return outbounds == null;
	}

}
