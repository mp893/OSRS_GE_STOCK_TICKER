package main;

import org.json.*;
import java.io.*;
import java.net.*;

public class RestService {
	
	public JSONObject getData(String url) throws Exception{
		
		URL link = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) link.openConnection();
	    connection.setRequestMethod("GET");
	    connection.setRequestProperty("Content-Type", "text/html; charset=ISO-8859-1");
	    
	    connection.connect();
		
	    String rawData = null;
	    String jsonAsString = null;
	    
	    BufferedReader inputRead = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    while((rawData = inputRead.readLine()) != null){
	    	jsonAsString = rawData;
	    }
	    
	    JSONObject data = new JSONObject(jsonAsString);
	    return data.getJSONObject("item");
	}
	
	public String constructUrl(int itemVal){
		return Constants.baseURL + Constants.catalogue + itemVal;
	}

}
