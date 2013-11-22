package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHILD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.MODE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.PARENT;
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


	}
	
	private void addListenerOnChildButton() {
        final Button childButton = (Button) findViewById(R.id.childButton);
		final Intent i = new Intent(this, ScheduleActivity.class);
    	i.putExtra(MODE, CHILD);

    	
        childButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		startActivity(i);
        	}
        });
	}
	
	private void addListenerOnParentButton() {

		final Button parentButton = (Button) findViewById(R.id.parentButton);
		final Intent i = new Intent(this, ScheduleActivity.class);
    	i.putExtra(MODE, PARENT);

		parentButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
	

}
