package edu.berkeley.cs160.crappymalefemaleratio.chore;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

public class AddChoreActivity extends Activity {
	Button addChore;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_chore);
		addChore = new Button(this);
		//balls
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_chore, menu);
		return true;
	}
	
	
	protected void setUpTextFields(Context c) {
		
	}

}
