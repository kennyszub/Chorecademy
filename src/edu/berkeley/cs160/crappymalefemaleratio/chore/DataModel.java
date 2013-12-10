package edu.berkeley.cs160.crappymalefemaleratio.chore;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataModel {
	
	public static int getUserPoints(Context context){
		try {
	        JSONObject jsonData = getJSON(context);
	        int userPoints = (Integer) jsonData.get("userPoints");
	        return userPoints;
		} catch(JSONException e) {
	    	System.err.println("ERROR: Failed to get user points: " + e.getMessage());
	    	System.exit(1);
	    	return 0;
	    }
	}
	
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

	public static JSONArray getClaims(Context context) {
		try {
	        JSONObject jsonData = getJSON(context);
	        System.out.println(jsonData.toString());
	        JSONArray claims = jsonData.getJSONArray("claims");
	        return claims;
		} catch(JSONException e) {
	    	System.err.println("ERROR: Failed to get claim: " + e.getMessage());
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

	public static int findIndexById(Context context, String directory, String id) {
		try {
			JSONObject jsonData = getJSON(context); // get master JSON
			JSONArray jsonSubDirectory = jsonData.getJSONArray(directory); // access the SubDirectory (example: "chores")
			JSONObject objectCheck = new JSONObject(); // temporary JSON Object used to swap and check every entry
	
			for (int i = 0; i < jsonSubDirectory.length(); i++) {
				objectCheck = jsonSubDirectory.getJSONObject(i);
				if (objectCheck.getString("id").equals(id)) {
					return i;
				}
			}
			return 999; // 999 indicates that the element was not found
		} catch(JSONException e) {
    		System.err.println("ERROR: Failed to index into JSONArray: " + e.getMessage());
    		System.exit(1);
    		return 999; // 999 indicates that the element was not found
		}
    }
	
	public static void claimReward(Context context, String id) {
		try {
			JSONObject jsonData = getJSON(context);
			JSONArray rewards = jsonData.getJSONArray("rewards");
			JSONArray claims = jsonData.getJSONArray("claims");
			int removeIndex = findIndexById(context, "rewards", id);
			JSONObject claimed_reward = rewards.getJSONObject(removeIndex);
			
			/* Remove element by creating new JSONArray */
			JSONArray updatedRewards = new JSONArray();  
			int rewardsLength = rewards.length();
			if(rewards != null) { 
			   for (int i=0;i<rewardsLength;i++) { 
			        if (i != removeIndex) {
			        	updatedRewards.put(rewards.get(i));
			        }
			   } 
			}
			rewards = updatedRewards;
			jsonData.put("rewards", rewards);
			claims.put(claimed_reward);
	        saveJSON(context, jsonData);
			/*
			JSONArray rewardsMinusOne = new JSONArray();
			for (int i = 0; i < rewards.length(); i++) {
				if (rewards.getJSONObject(i).getString(reward_name).equals(reward_name))
				rewardsMinusOne.put(rewards.getJSONObject(i));
			}
			jsonData.put("rewards", rewardsMinusOne);
			claims.put(claimed_reward);
			*/
		} catch(JSONException e) {
    		System.err.println("ERROR: Failed to claim reward: " + e.getMessage());
    		System.exit(1);
		}
	}
	/* Takes id of chore object, and url of picture, and adds the url to that chore object. */
	public static void addURL(Context context, long id, String url) {
		try {
			JSONObject jsonData = getJSON(context);
			JSONArray chores = jsonData.getJSONArray("chores");
			JSONObject chore;
			String choreID;
			for (int i = 0; i < chores.length(); i++) {
				chore = chores.getJSONObject(i);
				choreID = chore.getString("id");
				if (choreID.equals(Long.toString(id))) {
					chore.put("url", url);
					break;
				}
			}
			
	        saveJSON(context, jsonData);

		} catch (JSONException e) {
			System.err.println("ERROR: Could not add url: " + e.getMessage());
    		System.exit(1);
		}
	}
	
	
	 public static JSONObject getJSON(Context context) {
		 SharedPreferences preferences = context.getSharedPreferences("chorecademyData", Context.MODE_PRIVATE);
	    
//		   uncomment to clear preferences
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
