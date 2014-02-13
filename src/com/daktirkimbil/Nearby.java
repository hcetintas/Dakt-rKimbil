package com.daktirkimbil;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class Nearby {
	public List<Place>  getvicinity(String content){
		content= content.split("\"results\" : ")[1];
		content = content.split("\"status\" : \"OK\"")[0];
		List<Place> listPlace = new ArrayList<Place>();

		try{
		 JSONArray jArray = new JSONArray(content);
         for(int i=0;i<jArray.length();i++){
         	JSONObject json_data = jArray.getJSONObject(i);
         	Place place = new Place();
         	
         	place.setIcon(json_data.getString("icon"));
         	place.setId(json_data.getString("id"));
         	place.setName(json_data.getString("name"));
         	JSONArray jGeow = new JSONArray("["+json_data.getString("geometry")+"]");
         	JSONObject json_Geo = jGeow.getJSONObject(0);
         	JSONArray jLocation = new JSONArray("["+json_Geo.getString("location")+"]");
         	JSONObject json_Location = jLocation.getJSONObject(0);
         	place.setLat(Double.valueOf(json_Location.getString("lat")));
         	place.setLng(Double.valueOf(json_Location.getString("lng")));
         	listPlace.add(place);
         }
		}
         catch(Exception ex)
         {
         	Log.v("getvicinity(String content)",ex.toString());
         }
		return listPlace;
	}
	

}
