package edu.berkeley.cs160.crappymalefemaleratio.chore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class DataModel {
	
	public static void addChore(Context context, String name, String description, String points, String date) {
		try {
			JSONObject chore = new JSONObject();
			chore.put("name", name);
			chore.put("description", description);
			chore.put("points", points);
		
	        JSONObject jsonData = getJSON(context);
	        JSONArray chores = jsonData.getJSONArray("chores");
	        chores.put(chore);
		} catch(JSONException e) {
	    	System.err.println("ERROR: Failed to add chore: " + e.getMessage());
	    	System.exit(1);
	    }
	}
	
	public static JSONArray getChores(Context context, String name) {
		try {
	        JSONObject jsonData = getJSON(context);
	        JSONArray chores = jsonData.getJSONArray("chores");
	        return chores;
		} catch(JSONException e) {
	    	System.err.println("ERROR: Failed to add chore: " + e.getMessage());
	    	System.exit(1);
	    	return null;
	    }
	}
	
	 public static String getUser(Context context, String key, String _default){
	        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
	        return preferences.getString(key, _default);
	 }
	
	 public static JSONObject getJSON(Context context) {
		 SharedPreferences preferences = context.getSharedPreferences("codecademyData", Context.MODE_PRIVATE);
		 String jsonData = preferences.getString("jsonData", null);
		 try {
			 if (jsonData == null) {
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
	 
	 
	 public static void saveJSON(JSONObject json, SharedPreferences preferences) {
	     Editor edit = preferences.edit();
	     edit.putString("jsonData", json.toString());
	     edit.commit();
	 }

}
