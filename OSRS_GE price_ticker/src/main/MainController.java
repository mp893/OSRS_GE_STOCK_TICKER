package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import org.json.JSONObject;

public class MainController {

	public JSONObject getItemData(int Item) throws Exception{
		
		RestService service = new RestService();
		
		String itemUrl = service.constructUrl(Item);
		return service.getData(itemUrl);
	}
	
	public void setLabel(JLabel label, String text){
		if(text.contains("-")){
			label.setForeground(Constants.red);
		}
		if(text.contains("+")){
			label.setForeground(Constants.green);
		}
		label.setText(text);
	}
	
	public void setData(List<JLabel> labels, JSONObject item) throws Exception{
		setLabel(labels.get(0), item.getString("name"));
		ImageIcon itemImage = new ImageIcon(new URL(item.getString("icon_large")));
		labels.get(1).setIcon(itemImage);
		setLabel(labels.get(2),"Change Today : " + item.getJSONObject("today").getString("price"));
		setLabel(labels.get(5),"Price : " + item.getJSONObject("current").getString("price"));
		setLabel(labels.get(3),"30 Days : " + item.getJSONObject("day30").getString("change"));
		setLabel(labels.get(4),"90 Days : "  + item.getJSONObject("day90").getString("change"));
		setLabel(labels.get(6),"180 Days : " + item.getJSONObject("day180").getString("change"));
		setLabel(labels.get(7),"Description : " + item.getString("description"));
		setLabel(labels.get(8),"Tick time : " + System.currentTimeMillis());
	}
	
	public List<LibraryBuilder> mapDataSets(int numOfSets, int total){
		
		List<LibraryBuilder> dataSets = new ArrayList<>();
		int setSize = total/numOfSets;
		
		int min = 1;
		int max = setSize;
		
		for(int i = 1; i <= numOfSets; i++){
			LibraryBuilder current = new LibraryBuilder(min,max);
			dataSets.add(current);
			min = min + setSize;
			max = max + setSize;
		}
		
		return dataSets;
	}
	
	public List<Thread> generateLibraryThreads(List<LibraryBuilder> dataSets){
		
		List<Thread> threads = new ArrayList<>();
		
		for(LibraryBuilder dataSet : dataSets){
			Thread t = new Thread(dataSet);
			threads.add(t);
		}
		return threads;
	}

	public int executeLibraryThreads(List<Thread> threads) {
		for(Thread t : threads){
			t.start();
		}
		
		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 1;
			}	
			
		}
		return 0;
	}

	public HashMap reduceDataSets(List<LibraryBuilder> dataSets) {
		HashMap Library = new HashMap();
		
		for(LibraryBuilder dataSet : dataSets){
			Library.putAll(dataSet.getInternalMap());
		}
		
		return Library;
	}

	public void saveLibrary(HashMap library) {
		File output = new File("OSRS_item_library.csv");
		BufferedWriter fileout = null;
		try {
			fileout = new BufferedWriter(new FileWriter(output));
			Map<String, Integer> mp = library;
			for (Map.Entry<String,Integer> entry : mp.entrySet()) {
			 fileout.write(entry.getKey() + "," + entry.getValue());
			 fileout.newLine();
			}
			fileout.flush();
			fileout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap mapFromCSV() {
		FileReader fr = null;
		HashMap library = new HashMap();
		try {
			fr = new FileReader("OSRS_item_library.csv");
			BufferedReader filein = new BufferedReader(fr);
			String line = null;
			while((line = filein.readLine()) != null){
				String[]keyVal = line.split(",");
				library.put(keyVal[0], keyVal[1]);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return library;
	}

	public void populateMenu(JList list, HashMap library) {
		Map<String, Integer> mp = library;
		list.removeAll();
		for (Map.Entry<String,Integer> entry : mp.entrySet()) {
			JButton current = new JButton();
			current.setSize(10, 10);
			current.setText(entry.getKey());
			list.add(entry.getKey(), new JLabel());
		}
	}
}
