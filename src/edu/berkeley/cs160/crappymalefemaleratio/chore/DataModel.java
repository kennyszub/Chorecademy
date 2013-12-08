package edu.berkeley.cs160.crappymalefemaleratio.chore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataModel {
	
	public static void addChore(Context context, JSONObject choreInfo) {
		try {
	        JSONObject jsonData = getJSON(context);
	        System.out.println(jsonData.toString());

	        JSONArray chores = jsonData.getJSONArray("chores");
	        chores.put(choreInfo);
	        saveJSON(context, jsonData);
		} catch(JSONException e) {
	    	System.err.println("ERROR: Failed to add chore: " + e.getMessage());
	    	System.exit(1);
	    }
	}
	
	public static JSONArray getChores(Context context) {
		try {
	        JSONObject jsonData = getJSON(context);
	        
	        System.out.println(jsonData.toString());
	        
	        JSONArray chores = jsonData.getJSONArray("chores");
	        return chores;
		} catch(JSONException e) {
	    	System.err.println("ERROR: Failed to add chore: " + e.getMessage());
	    	System.exit(1);
	    	return null;
	    }
	}
	
	
	 public static JSONObject getJSON(Context context) {
		 SharedPreferences preferences = context.getSharedPreferences("chorecademyData", Context.MODE_PRIVATE);
	    
		 //  uncomment to clear preferences
//		 Editor edit = preferences.edit();
//	     edit.clear();
//	     edit.commit();
		 
		 String jsonData = preferences.getString("jsonData", null);
		 try {
			 if (jsonData == null) {
				 System.out.println("json data is null");
				 
				 JSONObject newJson = new JSONObject();
				 JSONArray rewards = new JSONArray();
				 JSONArray chores = new JSONArray();
				 
				 newJson.put("rewards", rewards);
				 newJson.put("chores", chores);
				 return newJson;
			 } else {
				 return new JSONObject(jsonData);
			 }
		 } catch (JSONException e) {
			 System.err.println("ERROR: Failed to add chore: " + e.getMessage());
		     System.exit(1);
		     return null;
		 }
	 }
	 
	 /* Saves json to "chorecademyData". Overwrites any existing data. */
	 public static void saveJSON(Context context, JSONObject json) {
		 SharedPreferences preferences = context.getSharedPreferences("chorecademyData", Context.MODE_PRIVATE);
	     Editor edit = preferences.edit();
	     edit.putString("jsonData", json.toString());
	     edit.commit();
	 }

}
