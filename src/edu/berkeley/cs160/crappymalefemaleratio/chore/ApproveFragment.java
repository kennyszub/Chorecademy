package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHILD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHORE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DATE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.MILLIS;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.MODE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.POINTS;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DESCRIPTION;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.ID;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIMED_REWARD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIMED_VALUE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIMED_CLAIM;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.berkeley.cs160.crappymalefemaleratio.chore.ChoresFragment.MapComparator;

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

public class ApproveFragment extends Fragment {
    private Activity activity;
    private ArrayList<HashMap<String, String>> list;
    private String mode;
    private View thisView;
    ApproveListViewAdapter adapter;
    
	public ApproveFragment() {
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Bundle settings = this.getArguments();
    	mode = settings.getString(MODE);
    	
        activity = this.getActivity();
    	View rootView;
    	ListView lview;
    	
		rootView = inflater.inflate(R.layout.activity_parent_approve_fragment, container, false);
		thisView = rootView;
        lview = (ListView) rootView.findViewById(R.id.approve_listview);
        populateList();
        /* Display default "Nothing to Approve" image if no chores to approve */
        if(list.size() == 0){
        	ImageView noApprove = (ImageView) rootView.findViewById(R.id.noApproveImage);
        	noApprove.setVisibility(View.VISIBLE);
        }
        adapter = new ApproveListViewAdapter(this.getActivity(), list);
        lview.setAdapter(adapter);
        lview.setOnItemClickListener(new ItemClickListener());
        return rootView;
    }

    @Override
    public void onResume() {
    	super.onResume();
    	populateList();
    	ImageView noApprove = (ImageView) thisView.findViewById(R.id.noApproveImage);
    	if(list.size() > 0){
	    	noApprove.setVisibility(View.GONE);
    	}else{
	    	noApprove.setVisibility(View.VISIBLE);
    	}
    	adapter.updateList(list);
    }
    
    /** Populate list items. */
    private void populateList() {
        list = new ArrayList<HashMap<String, String>>();

        JSONArray approvals = DataModel.getApprovals(activity);
        JSONObject approveObject;
        
        try {
	        for (int i = 0; i < approvals.length(); i++) {
	        	approveObject = approvals.getJSONObject(i);
	        	HashMap<String, String> chore = new HashMap<String, String>();
	        	chore.put(CHORE, approveObject.getString("name"));
	        	chore.put(DESCRIPTION, approveObject.getString("description"));
	        	chore.put(DATE, approveObject.getString("date"));
	        	chore.put(POINTS, approveObject.getString("points"));
	        	chore.put(MILLIS, approveObject.getString("millis"));
	        	chore.put("id", approveObject.getString("id"));
	        	list.add(chore);
	        }
        } catch (JSONException e) {
        	System.err.println("ERROR: Failed to populate list: " + e.getMessage());
	    	System.exit(1);
        }        

    }

    private class ItemClickListener implements OnItemClickListener {
    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    		System.out.println("in");
			HashMap<String, String> itemMap = list.get(position);
    		Intent intent = new Intent(activity, ApproveDetailsActivity.class);
    		intent.putExtra(CHORE, itemMap.get(CHORE));
    		intent.putExtra(DESCRIPTION, itemMap.get(DESCRIPTION));
    		intent.putExtra(DATE, itemMap.get(DATE));
    		intent.putExtra(POINTS, itemMap.get(POINTS));
    		intent.putExtra(ID, itemMap.get(ID));
            System.out.println("CHORE: "+itemMap.get(CHORE)+", ID: "+itemMap.get(ID));
            
        	activity.startActivity(intent);
    	}
    }
    


}
