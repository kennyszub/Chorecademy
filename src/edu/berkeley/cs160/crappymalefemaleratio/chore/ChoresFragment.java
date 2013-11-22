package edu.berkeley.cs160.crappymalefemaleratio.chore;

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
import android.widget.Button;
import android.widget.ListView;

public class ChoresFragment extends Fragment {
	public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";
    public static final String THIRD_COLUMN = "Third";
    public static final String FOURTH_COLUMN = "Fourth";
    private Activity activity;
    
    private ArrayList<HashMap<String, String>> list;
    
    private String mode;

    
	public ChoresFragment() {
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	Bundle settings = this.getArguments();
    	mode = settings.getString("mode");
    	
        activity = this.getActivity();
    	View rootView;
    	if (mode.equals("child")) {
    		// Use child chores fragment
    		rootView = inflater.inflate(R.layout.activity_chores_fragment, container, false);
    	} else {
    		// Use parent chores fragment
    		rootView = inflater.inflate(R.layout.activity_parent_chores_fragment, container, false);
    		addListenerOnAddChoreButton(rootView);
    	}
        ListView lview = (ListView) rootView.findViewById(R.id.listview);
        populateList();
        ListViewAdapter adapter = new ListViewAdapter(this.getActivity(), list);
        lview.setAdapter(adapter);
        
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
    
    
    private void populateList() {
    	 
        list = new ArrayList<HashMap<String, String>>();
        
        for (int i = 0; i < 20; i++) {
        	HashMap<String, String> temp = new HashMap<String, String>();
            temp.put(FIRST_COLUMN,"Chore" + Integer.toString(i));
            temp.put(SECOND_COLUMN, "October " + Integer.toString(i));
            temp.put(THIRD_COLUMN, "" + Integer.toString(i));
            list.add(temp);
        }
    }


}
