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
	
	
	public static void addReward(Context context, JSONObject rewardInfo) {
		try {
	        JSONObject jsonData = getJSON(context);
	        System.out.println(jsonData.toString());

	        JSONArray rewards = jsonData.getJSONArray("rewards");
	        rewards.put(rewardInfo);
	        saveJSON(context, jsonData);
		} catch(JSONException e) {
	    	System.err.println("ERROR: Failed to add reward: " + e.getMessage());
	    	System.exit(1);
	    }
	}
	
	
	public static JSONArray getRewards(Context context) {
		try {
	        JSONObject jsonData = getJSON(context);
	        
	        System.out.println(jsonData.toString());
	        
	        JSONArray rewards = jsonData.getJSONArray("rewards");
	        return rewards;
		} catch(JSONException e) {
	    	System.err.println("ERROR: Failed to get reward: " + e.getMessage());
	    	System.exit(1);
	    	return null;
	    }
	}
	
								// this	     	// "chores"       // "clean room"
	public static int findIndex(Context context, String directory, String entry_name) 
	{
		try 
		{
			JSONObject jsonData = getJSON(context); // get master JSON
			JSONArray jsonSubDirectory = jsonData.getJSONArray(directory); // access the SubDirectory (example: "chores")
			JSONObject objectCheck = new JSONObject(); // temporary JSON Object used to swap and check every entry
	
			for (int i = 0; i < jsonSubDirectory.length(); i++)
			{
				objectCheck = jsonSubDirectory.getJSONObject(i);
				
				if (objectCheck.getString("name").equals(entry_name))
				{
					return i;
				}
			}
			
			return 999; // 999 indicates that the element was not found
		}
		catch(JSONException e)
		{
    		System.err.println("ERROR: Failed to index into JSONArray: " + e.getMessage());
    		System.exit(1);
    		return 999; // 999 indicates that the element was not found
		}
    }
	
	
	public static void claimReward(Context context, String reward_name)
	{
		try
		{
			JSONObject jsonData = getJSON(context);
			JSONArray rewards = jsonData.getJSONArray("rewards");
			JSONArray claims = jsonData.getJSONArray("claims");
			int index = findIndex(context, "rewards", reward_name);
			JSONObject claimed_reward = rewards.getJSONObject(index);
			// rewards.remove(index);
			
			JSONArray rewardsMinusOne = new JSONArray();
			for (int i = 0; i < rewards.length(); i++)
			{
				if (rewards.getJSONObject(i).getString(reward_name).equals(reward_name))
				rewardsMinusOne.put(rewards.getJSONObject(i));
			}
			
			jsonData.put("rewards", rewardsMinusOne);
			claims.put(claimed_reward);
			
			
		}
		catch(JSONException e)
		{
    		System.err.println("ERROR: Failed to claim reward: " + e.getMessage());
    		System.exit(1);
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
				 JSONArray claims = new JSONArray();
				 int userPoints = 0;
				 
				 newJson.put("rewards", rewards);
				 newJson.put("chores", chores);
				 newJson.put("claims", claims);
				 newJson.put("userPoints", userPoints);
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
