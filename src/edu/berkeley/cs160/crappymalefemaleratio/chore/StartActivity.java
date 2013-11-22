package edu.berkeley.cs160.crappymalefemaleratio.chore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
        addListenerOnChildButton();
        addListenerOnParentButton();
        //TODO
        //final Button parentButton = (Button) findViewById(R.id.parentButton)
        //addListenerOnParentButton();

	}
	
	private void addListenerOnChildButton() {
        final Button childButton = (Button) findViewById(R.id.childButton);
		final Intent i = new Intent(this, ChildActivity.class);
        childButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		startActivity(i);
        	}
        });
	}
	
	private void addListenerOnParentButton() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	

}
