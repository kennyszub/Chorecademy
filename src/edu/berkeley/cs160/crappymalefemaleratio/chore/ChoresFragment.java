package edu.berkeley.cs160.crappymalefemaleratio.chore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChoresFragment extends Fragment {
	
	public ChoresFragment() {
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_chores_fragment, container, false);    
        return rootView;
    }

}
