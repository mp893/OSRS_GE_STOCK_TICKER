package main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.json.JSONException;
import org.json.JSONObject;

public class TickTask extends TimerTask{
		
	   private List<JLabel> labels = new ArrayList<>();
	   private JSONObject item = null;
	   private MainController mainCtrl = new MainController();
	   private Timer owner = null;
		
	   public TickTask(List<JLabel> labels, JSONObject item, Timer owner){
		   this.labels = labels;
		   this.item = item;
		   this.owner = owner;
		   
	   }


	   @Override
	   public void run(){
		   try {
			mainCtrl.setLabel(labels.get(0), item.getString("name"));
			ImageIcon itemImage = new ImageIcon(new URL(item.getString("icon_large")));
			labels.get(1).setIcon(itemImage);
			mainCtrl.setLabel(labels.get(2),"Change Today : " + item.getJSONObject("today").getString("price"));
			mainCtrl.setLabel(labels.get(5),"Price : " + item.getJSONObject("current").getString("price"));
			mainCtrl.setLabel(labels.get(3),"30 Days : " + item.getJSONObject("day30").getString("change"));
			mainCtrl.setLabel(labels.get(4),"90 Days : "  + item.getJSONObject("day90").getString("change"));
			mainCtrl.setLabel(labels.get(6),"180 Days : " + item.getJSONObject("day180").getString("change"));
			mainCtrl.setLabel(labels.get(7),"Description : " + item.getString("description"));
			mainCtrl.setLabel(labels.get(8),"Tick time : " + System.currentTimeMillis());
		} catch (JSONException | MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }

	}
