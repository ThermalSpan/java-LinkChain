

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class LinkChain {
	
	public LinkChain() {
		
	}
	
	public void findChain(String url1, String url2) {
		
		boolean cont = true;
		int layer = 1;
		ArrayList<String> checkList = new ArrayList<String>();
		checkList.add(url1);
	
		for(int i = 0; i < 4; i++){
			checkList = getLinks(checkList);
			System.out.println(layer++ + "\t" + checkList.size());
		}
	}
	
	
	public boolean isURL(String url){
		boolean result = true;
		
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			result = false;
		}
		
		return result;
	}
	
	public ArrayList<String> getLinks(ArrayList<String> linkList) {
		ArrayList<String> result = new ArrayList<String>();
		
		for(String link: linkList) {
			result.addAll(getLinks(link));
		}
		
		return result;
	}
	
	public ArrayList<String> getLinks(String url) {
		ArrayList<String> result = new ArrayList<String>();
		
		//Here we try to get all links from the url, and all found urls are checked before they are added.
		try{
    		Document doc = Jsoup.connect(url).get();
    		Elements linkElements = doc.select("a[href]"); 
    		for(Element e: linkElements) {
    			String link = e.attr("abs:href");
    			if(isURL(link)) result.add(link);
    		}
    	} catch(Exception e) {
    		System.out.println("Failed to get URLs from Link: " + url);
    		System.out.println("You exception was:");
    		System.out.println(e.getMessage());
    	}
		
		return result;
	}
	
    public static void main(String[] args) {
    	
    	LinkChain test = new LinkChain();
    	
    	Scanner input = new Scanner(System.in);
    	String url1 = input.next();
    	//String url2 = input.next();
    	input.close();
    	
    	for(String l: test.getLinks(url1)) {
    		System.out.println(l);
    	}
    	
    //	test.findChain(url1, url2);
    }
    	
}
