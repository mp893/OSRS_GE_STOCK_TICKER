package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONObject;


//Construct an itemIndex : itemName map
public class LibraryBuilder implements Runnable{
	

	private HashMap internalMap = new HashMap();
	
	private int min = 1;
	
	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	private int max;
	
	public LibraryBuilder(int min, int max){
		this.min = min;
		this.max = max;
	}
	
	public LibraryBuilder(){
		
	}
	
	public HashMap getInternalMap() {
		return internalMap;
	}

	public void setInternalMap(HashMap internalMap) {
		this.internalMap = internalMap;
	}

	public HashMap fromFile(File f){
		HashMap itemMap = new HashMap();
		try {
			BufferedReader fr = new BufferedReader(new FileReader(f));
			String item;
			while((item = fr.readLine()) != null){
				String[] keyValue = item.split(",");
				itemMap.put(keyValue[0], keyValue[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return itemMap;
	}
	
	public HashMap generateRESTMap(int range){
		HashMap itemMap = new HashMap();
		RestService service = new RestService();
		
		for(int i = min; i <= range; i++){
			String url = service.constructUrl(i);
			try{
				JSONObject item = service.getData(url);
				String name = item.getString("name");
				itemMap.put(name,i);
			   }
			catch (Exception e){
			}
			
			}
		return itemMap;
		}

	@Override
	public void run() {
		internalMap = generateRESTMap(max);
	}
	}
