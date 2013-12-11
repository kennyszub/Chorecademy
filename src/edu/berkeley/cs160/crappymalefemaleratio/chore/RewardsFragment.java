package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.*;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RewardsFragment extends Fragment {
    private Activity activity;
    private ArrayList<HashMap<String, String>> list;
    private String mode;
    RewardsListViewAdapter adapter;
    Context context;
    private View thisView;
    
	public RewardsFragment() {
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	Bundle settings = this.getArguments();
    	mode = settings.getString(MODE);
        activity = this.getActivity();
        context = activity.getApplicationContext();
        // ADDED
        
    	View rootView, rowView = null;

		// Use child chores fragment
    	if (mode.equals(CHILD)) {
    		rootView = inflater.inflate(R.layout.activity_child_rewards_fragment, container, false);
    		rowView = inflater.inflate(R.layout.rewards_listview_row, container, false);	
    	} else {
    		rootView = inflater.inflate(R.layout.activity_parent_rewards_fragment, container, false);
    		addListenerOnAddChoreButton(rootView);
    	}
    	thisView = rootView;
        ListView lview = (ListView) rootView.findViewById(R.id.rewards_listview);
        populateList();
        
        if(mode.equals(CHILD)){
    		/* Modify Reward Bar */
    		generateRewardBar(rootView);
            updateUserPoints();

        }
        /* Display default "No Rewards" image if no rewards */
        if(list.size() == 0){
        	ImageView noRewards = (ImageView) rootView.findViewById(R.id.noRewardsImage);
        	noRewards.setVisibility(View.VISIBLE);
        }
        
        adapter = new RewardsListViewAdapter(this.getActivity(), lview, list, mode, context);
        adapter.setFragment(this);
        lview.setAdapter(adapter);
        lview.setOnItemClickListener(new ItemClickListener());

        return rootView;
    }
    
    
    @Override
    public void onResume() {
    	super.onResume();
    	populateList();
    	if(list.size() > 0){
	    	ImageView noRewards = (ImageView) thisView.findViewById(R.id.noRewardsImage);
	    	noRewards.setVisibility(View.GONE);
    	}
    	adapter.updateList(list);
    }
    
    public void updateUserPoints() {
    	int userPoints = DataModel.getUserPoints(context);
    	
    	final TextView points = (TextView) thisView.findViewById(R.id.numPoints);
    	points.setText("You have " + userPoints + " points!");
    	
    }
    

    
    private void addListenerOnAddChoreButton(View rootView) {
    	final Button addRewardButton = (Button) rootView.findViewById(R.id.addRewardButton);
    	final Intent i = new Intent(activity, SetReward.class);
    	
    	addRewardButton.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			startActivity(i);
    		}
    	});
    }
    
    private void generateRewardBar(View v){
    	ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.RewardBar);
		TextView nextReward = (TextView) v.findViewById(R.id.RewardBarItem);
		int userPoints = DataModel.getUserPoints(context);
		int minPoints = (int) Double.POSITIVE_INFINITY;
		String hashKey, nextRewardName = "";
		HashMap<String, String> hash;
		int hashValue, pointsToReward, percentageOfBar;
		boolean hasNextReward = false;
		for(int i=0; i<list.size(); i++){
			hash = list.get(i);
			hashKey = (String) hash.get(REWARD);
			hashValue = Integer.parseInt(hash.get(VALUE));
			if((hashValue < minPoints) && (userPoints < hashValue)){
				hasNextReward = true;
				nextRewardName = hashKey;
				minPoints = hashValue;
			}
		}
		if(list.size() > 0){
			if(hasNextReward){
	    		if(userPoints < minPoints){
	    			percentageOfBar = (int)(((double) userPoints)/minPoints * 100);
	    		}else{
	    			percentageOfBar = 0;
	    		}
	    		progressBar.setProgress(percentageOfBar);
	    		nextReward.setText(nextRewardName+" ("+percentageOfBar +"/"+minPoints+")");
			}else{
	    		progressBar.setProgress(100);
	    		nextReward.setText(userPoints+"/--");
			}
		}else{
    		progressBar.setProgress(0);
    		nextReward.setText(userPoints+"/--");
		}
    }
    /** Populate list items. */
    private void populateList() {
    	 
        list = new ArrayList<HashMap<String, String>>();
        
        // TEMP VALUES
        // TODO temp hack to remove iPad
        /*
        Bundle settings = this.getArguments();
        if (settings.getString("iPad") == null) {
            HashMap<String, String> ipad = new HashMap<String, String>();
            ipad.put(REWARD,"iPad");
            ipad.put(VALUE, "400");
            ipad.put(CLAIM, "false");
            ipad.put(ID, "12345603");
            list.add(ipad);
        }
        
        HashMap<String, String> ps3 = new HashMap<String, String>();
        ps3.put(REWARD,"PS3");
        ps3.put(VALUE, "300");
        ps3.put(CLAIM, "false");
        ps3.put(ID, "12345604");
        list.add(ps3);

        HashMap<String, String> legos = new HashMap<String, String>();
        legos.put(REWARD,"Lego Set");
        legos.put(VALUE, "100");
        legos.put(CLAIM, "false");
        legos.put(ID, "12345605");
        list.add(legos);

        HashMap<String, String> cards = new HashMap<String, String>();
        cards.put(REWARD,"Trading Cards");
        cards.put(VALUE, "60");
        cards.put(CLAIM, "false");
        cards.put(ID, "12345606");
        list.add(cards);
        */
        
        
        JSONArray rewards = DataModel.getRewards(activity);
        JSONObject rewardObject;
        
        try {
	        for (int i = 0; i < rewards.length(); i++) {
	        	rewardObject = rewards.getJSONObject(i);
	        	HashMap<String, String> new_reward = new HashMap<String, String>();
	        	new_reward.put(REWARD, rewardObject.getString("name"));
	        	new_reward.put(VALUE, rewardObject.getString("points"));
	        	new_reward.put(ID, rewardObject.getString("id"));
	            new_reward.put(CLAIM, "false");
	        	list.add(new_reward);
	        }
        } catch (JSONException e) {
        	System.err.println("ERROR: Failed to populate list: " + e.getMessage());
	    	System.exit(1);
        }                
	

    }

    private class ItemClickListener implements OnItemClickListener {
    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    		
    		JSONArray rewards = DataModel.getRewards(activity);
    		JSONObject rewardObject;

    		HashMap<String, String> itemMap = list.get(position);
    		
    		Intent intent = new Intent(activity, SetReward.class);
    		intent.putExtra(REWARD, itemMap.get(REWARD));
    		intent.putExtra(VALUE, itemMap.get(VALUE));
    		intent.putExtra(ID, itemMap.get(ID));

    		activity.startActivity(intent);
    	}
    }
    
    


}
