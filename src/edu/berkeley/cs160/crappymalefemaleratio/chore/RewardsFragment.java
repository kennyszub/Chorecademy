package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHILD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHORE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DATE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.MODE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.POINTS;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DESCRIPTION;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.REWARD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.VALUE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIM;


import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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
import android.widget.ListView;
import android.widget.Toast;

public class RewardsFragment extends Fragment {
    private Activity activity;
    private ArrayList<HashMap<String, String>> list;
    private String mode;
    
	public RewardsFragment() {
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	Bundle settings = this.getArguments();
    	mode = settings.getString(MODE);
    	
        activity = this.getActivity();
    	View rootView, rowView = null;

		// Use child chores fragment
    	if (mode.equals(CHILD)) {
    		rootView = inflater.inflate(R.layout.activity_child_rewards_fragment, container, false);
    		rowView = inflater.inflate(R.layout.rewards_listview_row, container, false);	
    	} else {
    		rootView = inflater.inflate(R.layout.activity_parent_rewards_fragment, container, false);
    		addListenerOnAddChoreButton(rootView);
    	}
        ListView lview = (ListView) rootView.findViewById(R.id.rewards_listview);
        populateList();
        RewardsListViewAdapter adapter = new RewardsListViewAdapter(this.getActivity(), list, mode);
        lview.setAdapter(adapter);
        lview.setOnItemClickListener(new ItemClickListener());

        
        return rootView;
    }
    
    private void addListenerOnAddChoreButton(View rootView) {
    	final Button addRewardButton = (Button) rootView.findViewById(R.id.addRewardButton);
    	final Intent i = new Intent(activity, AddChoreActivity.class);
    	
    	addRewardButton.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			startActivity(i);
    		}
    	});
    }
    
    
    /** Populate list items. */
    private void populateList() {
    	 
        list = new ArrayList<HashMap<String, String>>();
        
        // TEMP VALUES
        // TODO temp hack to remove iPad
        Bundle settings = this.getArguments();
        if (settings.getString("iPad") == null) {
            HashMap<String, String> ipad = new HashMap<String, String>();
            ipad.put(REWARD,"iPad");
            ipad.put(VALUE, "400 Points");
            ipad.put(CLAIM, "false");
            list.add(ipad);
        }
        
        HashMap<String, String> ps3 = new HashMap<String, String>();
        ps3.put(REWARD,"PS3");
        ps3.put(VALUE, "300 Points");
        ps3.put(CLAIM, "false");
        list.add(ps3);

        HashMap<String, String> legos = new HashMap<String, String>();
        legos.put(REWARD,"Lego Set");
        legos.put(VALUE, "100 Points");
        legos.put(CLAIM, "false");
        list.add(legos);

        HashMap<String, String> cards = new HashMap<String, String>();
        cards.put(REWARD,"Trading Cards");
        cards.put(VALUE, "60 Points");
        cards.put(CLAIM, "false");
        list.add(cards);
    }

    private class ItemClickListener implements OnItemClickListener {
    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    		HashMap<String, String> itemMap = list.get(position);
    		Intent intent = new Intent(activity, ChoreDetailsActivity.class);
    		intent.putExtra(CHORE, itemMap.get(CHORE));
    		intent.putExtra(DESCRIPTION, itemMap.get(DESCRIPTION));
    		intent.putExtra(DATE, itemMap.get(DATE));
    		intent.putExtra(POINTS, itemMap.get(POINTS));
    		activity.startActivity(intent);
    	}
    }
    
    


}
