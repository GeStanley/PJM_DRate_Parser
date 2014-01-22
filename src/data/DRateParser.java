package data;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DRateParser {
	
	static final String URL = "http://oasis.pjm.com/drate.html";
	private List<String> parsedData = new LinkedList<String>();

		
	public List<String> retrieveData(){
		
		Document doc=null;
		
		try {
			
			doc = Jsoup.connect(URL).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
				
			return null;
		}
		
		Elements body = doc.getElementsByTag("TD");
		
		for(Element src : body){
			parsedData.add(src.text());
		}
		
		return parsedData;

	}
}
