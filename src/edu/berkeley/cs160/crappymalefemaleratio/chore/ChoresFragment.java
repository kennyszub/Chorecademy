package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHILD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHORE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DATE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.MODE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.POINTS;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DESCRIPTION;


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
import android.widget.ListView;

public class ChoresFragment extends Fragment {

    private Activity activity;
    
    private ArrayList<HashMap<String, String>> list;
    
    private String mode;

    
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
        ListViewAdapter adapter = new ListViewAdapter(this.getActivity(), list);
        lview.setAdapter(adapter);
        lview.setOnItemClickListener(new ItemClickListener());
        
        return rootView;
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
        
        
        // TEMP VALUES
        
        // TODO temp hack to remove laundry
        Bundle settings = this.getArguments();
        if (settings.getString("Laundry") == null) {
	    	HashMap<String, String> laundry = new HashMap<String, String>();
	    	laundry.put(CHORE,"Do the Laundry and some other stuff");
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
        
        HashMap<String, String> rake = new HashMap<String, String>();
    	rake.put(CHORE,"Rake leaves");
    	rake.put(DESCRIPTION, "Put in leaf bags by the door");
        rake.put(DATE, "Tomorrow");
        rake.put(POINTS, "3");
        list.add(rake);
        
        HashMap<String, String> bathroom = new HashMap<String, String>();
    	bathroom.put(CHORE,"Clean the bathroom");
    	bathroom.put(DESCRIPTION, "Use windex to wipe down the bathroom");
        bathroom.put(DATE, "Thursday");
        bathroom.put(POINTS, "4");
        list.add(bathroom);
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
