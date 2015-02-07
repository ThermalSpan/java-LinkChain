/*
 * LinkChain.java
 * by Russell Bentley
 * 
 * This program will take two urls and a maximum search depth as input.
 * It will then perform a Depth First Search of all links starting at the first url.
 * The search will go up the specified depth. 
 * The chain of links to the second url1 will be printed if found. 
 * 
 * Built with jsoup:
 * http://jsoup.org
 */
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkChain {
	public static ArrayList<String> DFS(int depth, int layer, String url1, String url2) {
		//System.out.println("Entering Layer: " + layer);
	
		ArrayList<String> result = null;
	
		//Find all the links from url1
		ArrayList<String> foundLinks = getLinks(url1);
		//Are any of the found links url2? If so we have our result
		for(String link: foundLinks) {
			if(link.equals(url2)) {
				System.out.println("Match found!");
				result = new ArrayList<String>();
				//url1 -> url2 is the end our chain, now recur to build path. 
				result.add(0, url2);
				result.add(0, url1);
				break;
			}
		}
		//If we have not yet found url2, and the maximum depth is not yet reached then we need to descend further.
		if(result == null && layer < depth) for(String link: foundLinks) {
			//System.out.println("layer: " + layer + "descending on: " + link);
			ArrayList<String> chain = DFS(depth, layer+1, link, url2);
			if(chain != null) {
				result = chain;
				result.add(0, url1);
				break;
			}
		}
		
		//if(result ==null) System.out.println("null recur on layer: " + layer);
		//result is null if no result is possible 
		return result;
	}

	public static boolean isURL(String url){
		boolean result = true;
		//This seems to be the accepted best practice on testing validity of URLs.    
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			result = false;
		}
		
		return result;
	}
	
	public static ArrayList<String> getLinks(ArrayList<String> linkList) {
		ArrayList<String> result = new ArrayList<String>();
		
		for(String link: linkList) {
			result.addAll(getLinks(link));
		}
		
		return result;
	}
	
	public static ArrayList<String> getLinks(String url) {
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
    	Scanner input = new Scanner(System.in);
    	System.out.println("Enter First url:");
    	String url1 = input.next();
    	System.out.println("Enter Second url:");
    	String url2 = input.next();
    	System.out.println("Enter Maximum Depth:");
    	int depth = input.nextInt();
    	input.close();
    	
    	//Run the search, will max depth, starting on the first layer.
    	ArrayList<String> chain = LinkChain.DFS(depth,1, url1, url2);
    	if(chain != null) for(String link: chain) System.out.println(link);
    	if(chain == null) System.out.println("No solution found within search space.");
    }
}
