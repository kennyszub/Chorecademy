package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHILD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHORE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIM;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DATE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.ID;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.MODE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.POINTS;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DESCRIPTION;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIMED_REWARD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIMED_VALUE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIMED_CLAIM;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIMED_ID;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.REWARD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.VALUE;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class ClaimsFragment extends Fragment {
    private Activity activity;
    private ArrayList<HashMap<String, String>> list;
    private String mode;
    private View thisView;
    ClaimsListViewAdapter adapter;
    
	public ClaimsFragment() {
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Bundle settings = this.getArguments();
    	mode = settings.getString(MODE);
    	
        activity = this.getActivity();
    	View rootView;
    	ListView lview;
    	
		rootView = inflater.inflate(R.layout.activity_child_claims_fragment, container, false);
		thisView = rootView;
        lview = (ListView) rootView.findViewById(R.id.claims_listview);
        populateList();
        /* Display default "No Claims" image if no claims */
        if(list.size() == 0){
        	ImageView noClaims = (ImageView) rootView.findViewById(R.id.noClaimsImage);
        	noClaims.setVisibility(View.VISIBLE);
        }
        adapter = new ClaimsListViewAdapter(this.getActivity(), list);
        lview.setAdapter(adapter);
        lview.setOnItemClickListener(new ItemClickListener());
        return rootView;
    }

    @Override
    public void onResume() {
    	super.onResume();
    	populateList();
    	if(list.size() > 0){
	    	ImageView noClaims = (ImageView) thisView.findViewById(R.id.noClaimsImage);
	    	noClaims.setVisibility(View.GONE);
    	}
    	adapter.updateList(list);
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
            ipad.put(CLAIMED_REWARD,"iPad");
            ipad.put(CLAIMED_VALUE, "400 Points");
            ipad.put(CLAIMED_CLAIM, "false");
            list.add(ipad);
        }
        
        HashMap<String, String> ps3 = new HashMap<String, String>();
        ps3.put(CLAIMED_REWARD,"PS3");
        ps3.put(CLAIMED_VALUE, "300 Points");
        ps3.put(CLAIMED_CLAIM, "false");
        list.add(ps3);

        HashMap<String, String> legos = new HashMap<String, String>();
        legos.put(CLAIMED_REWARD,"Lego Set");
        legos.put(CLAIMED_VALUE, "100 Points");
        legos.put(CLAIMED_CLAIM, "false");
        list.add(legos);
        */
        
        JSONArray claims = DataModel.getClaims(activity);
        JSONObject claimObject;
        
        try {
	        for (int i = 0; i < claims.length(); i++) {
	        	claimObject = claims.getJSONObject(i);
	        	HashMap<String, String> new_reward = new HashMap<String, String>();
	        	new_reward.put(CLAIMED_REWARD, claimObject.getString("name"));
	        	new_reward.put(CLAIMED_VALUE, claimObject.getString("points"));
	            new_reward.put(CLAIMED_CLAIM, "false");
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
    		
    	}
    }
    
    


}
