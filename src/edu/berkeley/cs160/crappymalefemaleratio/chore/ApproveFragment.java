package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHILD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHORE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DATE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.MODE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.POINTS;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DESCRIPTION;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIMED_REWARD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIMED_VALUE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIMED_CLAIM;


import java.util.ArrayList;
import java.util.HashMap;

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
    	if(list.size() > 0){
	    	ImageView noApprove = (ImageView) thisView.findViewById(R.id.noApproveImage);
	    	noApprove.setVisibility(View.GONE);
    	}
    	adapter.updateList(list);
    }
    
    /** Populate list items. */
    private void populateList() {
        list = new ArrayList<HashMap<String, String>>();
        
        // TEMP VALUES
        
        // TODO temp hack to remove laundry
        Bundle settings = this.getArguments();
        if (settings.getString("Laundry") == null) {
	    	HashMap<String, String> laundry = new HashMap<String, String>();
	    	laundry.put(CHORE,"Do the Laundry");
	    	laundry.put(DESCRIPTION, "Wash your clothes, put them in the dryer, and fold them.");
	        laundry.put(DATE, "Today");
	        laundry.put(POINTS, "5");
	        list.add(laundry);
        }
        
        HashMap<String, String> dishes = new HashMap<String, String>();
    	dishes.put(CHORE,"Wash the dishes");
    	dishes.put(DESCRIPTION, "Clean food off the dishes and put in dishwasher");
        dishes.put(DATE, "Tomorrow");
        dishes.put(POINTS, "3");
        list.add(dishes);

    }
    
    private class ItemClickListener implements OnItemClickListener {
    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    		
    	}
    }
    
    


}
