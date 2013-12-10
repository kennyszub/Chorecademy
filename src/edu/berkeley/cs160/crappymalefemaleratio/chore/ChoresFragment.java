package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHILD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHORE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DATE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DESCRIPTION;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.MILLIS;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.MODE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.POINTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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
import android.widget.ListView;

public class ChoresFragment extends Fragment {

    private Activity activity;
    
    private ArrayList<HashMap<String, String>> list;
    
    private String mode;
    private ListViewAdapter listViewAdapter;
    
	public ChoresFragment() {
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Bundle settings = this.getArguments();
    	mode = settings.getString(MODE);
    	
        activity = this.getActivity();
    	View rootView;
    	
		// Use child chores fragment
    	if (mode.equals(CHILD)) {
    		rootView = inflater.inflate(R.layout.activity_child_chores_fragment, container, false);
    	// Use parent chores fragment
    	} else {
    		rootView = inflater.inflate(R.layout.activity_parent_chores_fragment, container, false);
    		addListenerOnAddChoreButton(rootView);
    	}
        ListView lview = (ListView) rootView.findViewById(R.id.listview);
        populateList();
        listViewAdapter = new ListViewAdapter(this.getActivity(), list);
        lview.setAdapter(listViewAdapter);
        lview.setOnItemClickListener(new ItemClickListener());
        
        
        return rootView;
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	populateList();
    	listViewAdapter.updateList(list);
    }
    
    private void addListenerOnAddChoreButton(View rootView) {
    	final Button addChoreButton = (Button) rootView.findViewById(R.id.addChoreButton);
    	final Intent i = new Intent(activity, AddChoreActivity.class);
    	
    	addChoreButton.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			startActivity(i);
    		}
    	});
    }
    
    /** Populate list items. */
    private void populateList() {
    	 
        list = new ArrayList<HashMap<String, String>>();
        
        JSONArray chores = DataModel.getChores(activity);
        JSONObject choreObject;
        
        try {
	        for (int i = 0; i < chores.length(); i++) {
	        	choreObject = chores.getJSONObject(i);
	        	HashMap<String, String> chore = new HashMap<String, String>();
	        	chore.put(CHORE, choreObject.getString("name"));
	        	chore.put(DESCRIPTION, choreObject.getString("description"));
	        	chore.put(DATE, choreObject.getString("date"));
	        	chore.put(POINTS, choreObject.getString("points"));
	        	chore.put(MILLIS, choreObject.getString("millis"));
	        	chore.put("id", choreObject.getString("id"));
	        	list.add(chore);
	        }
	        Collections.sort(list, new MapComparator(MILLIS));
        } catch (JSONException e) {
        	System.err.println("ERROR: Failed to populate list: " + e.getMessage());
	    	System.exit(1);
        }        
    }
    
    private class ItemClickListener implements OnItemClickListener {
    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    		ViewHolder holder = (ViewHolder) view.getTag();
    		
			HashMap<String, String> itemMap = list.get(position);
    		Intent intent = new Intent(activity, ChoreDetailsActivity.class);
    		intent.putExtra(CHORE, itemMap.get(CHORE));
    		intent.putExtra(DESCRIPTION, itemMap.get(DESCRIPTION));
    		intent.putExtra(DATE, itemMap.get(DATE));
    		intent.putExtra(POINTS, itemMap.get(POINTS));
    		intent.putExtra("id", holder.id);
            
        	activity.startActivity(intent);

    		
    	}
    }
    
    class MapComparator implements Comparator<Map<String, String>>
    {
        private final String key;

        public MapComparator(String key)
        {
            this.key = key;
        }

        public int compare(Map<String, String> first,
                           Map<String, String> second)
        {
            // TODO: Null checking, both for maps and values
            Long firstDate = Long.parseLong(first.get(key));
            Long secondDate = Long.parseLong(second.get(key));
            return firstDate.compareTo(secondDate);
        }
    }
    
    
    


}
