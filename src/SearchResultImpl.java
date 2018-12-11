import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.upenn.cis121.project.engine.QueryContext;
import edu.upenn.cis121.project.engine.SearchResult;


public class SearchResultImpl implements SearchResult{
	private Map<String, Double> SubValueMap = new HashMap<String, Double>();
	private String title;
	
	//constructor
	public SearchResultImpl(Double score, String t){
		this.SubValueMap.put("td-idf", score);
		this.title = t;
	}

	@Override
	public double getOverallValue() {
		// TODO Auto-generated method stub
		return this.SubValueMap.get("td-idf");
	}

	@Override
	public Collection<? extends QueryContext> getQueryContexts() {
		// we just return null
		return null;
	}

	@Override
	public Map<String, Double> getSubValueMap() {
		// TODO Auto-generated method stub
		return SubValueMap;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	@Override
	public URL getURL() {
		URL u = null;
		try{
			u = new URL("https://simple.wikipedia.org/wiki/" + title);
		}catch(MalformedURLException e){
			
		}
		return u;
	}

}
