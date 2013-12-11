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
	public static JSONArray getApprovals(Context context) {
		try {
	        JSONObject jsonData = getJSON(context);
	        System.out.println(jsonData.toString());
	        JSONArray approvals = jsonData.getJSONArray("approvals");
	        return approvals;
		} catch(JSONException e) {
	    	System.err.println("ERROR: Failed to get approval: " + e.getMessage());
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
		} catch(JSONException e) {
    		System.err.println("ERROR: Failed to claim reward: " + e.getMessage());
    		System.exit(1);
		}
	}
	

	public static void finishChore(Context context, String id) {
		try {
			JSONObject jsonData = getJSON(context);
			JSONArray chores = jsonData.getJSONArray("chores");
			JSONArray approvals = jsonData.getJSONArray("approvals");
			int removeIndex = findIndexById(context, "chores", id);
			JSONObject finished_chore = chores.getJSONObject(removeIndex);
			
			/* Remove element by creating new JSONArray */
			JSONArray updatedChores = new JSONArray();  
			int rewardsLength = chores.length();
			if(chores != null) { 
			   for (int i=0;i<rewardsLength;i++) { 
			        if (i != removeIndex) {
			        	updatedChores.put(chores.get(i));
			        }
			   } 
			}
			chores = updatedChores;
			jsonData.put("chores", chores);
			approvals.put(finished_chore);
	        saveJSON(context, jsonData);
		} catch(JSONException e) {
    		System.err.println("ERROR: Failed to finish chore: " + e.getMessage());
    		System.exit(1);
		}
	}
	
	public static void denyFinish(Context context, String id) {
		try {
			JSONObject jsonData = getJSON(context);
			JSONArray chores = jsonData.getJSONArray("chores");
			JSONArray approvals = jsonData.getJSONArray("approvals");
			int reAddIndex = findIndexById(context, "approvals", id);
			JSONObject reAddChore = approvals.getJSONObject(reAddIndex);
			
			/* Remove element by creating new JSONArray */
			JSONArray updatedApprovalList = new JSONArray();  
			int approvalsLength = approvals.length();
			if(approvals != null) { 
			   for (int i=0;i<approvalsLength;i++) { 
			        if (i != reAddIndex) {
			        	updatedApprovalList.put(approvals.get(i));
			        }
			   } 
			}
			approvals = updatedApprovalList;
			jsonData.put("approvals", approvals);
			chores.put(reAddChore);
	        saveJSON(context, jsonData);
		} catch(JSONException e) {
    		System.err.println("ERROR: Failed to deny finished chore: " + e.getMessage());
    		System.exit(1);
		}
	}
	
	public static void approveFinish(Context context, String id) {
		try {
			JSONObject jsonData = getJSON(context);
			JSONArray approvals = jsonData.getJSONArray("approvals");
			int finishedIndex = findIndexById(context, "approvals", id);
			JSONObject finishedChore = approvals.getJSONObject(finishedIndex);
			
			
			// add the points of finished chore to userPoints
			int add_points = finishedChore.getInt("points");
			int current_points = jsonData.getInt("userPoints");
			jsonData.put("userPoints", current_points + add_points);
			
			/* Remove element by creating new JSONArray */
			JSONArray updatedApprovalList = new JSONArray();  
			int approvalsLength = approvals.length();
			if(approvals != null) { 
			   for (int i=0;i<approvalsLength;i++) { 
			        if (i != finishedIndex) {
			        	updatedApprovalList.put(approvals.get(i));
			        }
			   }
			}
			approvals = updatedApprovalList;
			jsonData.put("approvals", approvals);
			
	        saveJSON(context, jsonData);
		} catch(JSONException e) {
    		System.err.println("ERROR: Failed to deny finished chore: " + e.getMessage());
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
				 JSONArray approvals = new JSONArray();
				 int userPoints = 0;
				 
				 newJson.put("rewards", rewards);
				 newJson.put("chores", chores);
				 newJson.put("claims", claims);
				 newJson.put("approvals", approvals);
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
