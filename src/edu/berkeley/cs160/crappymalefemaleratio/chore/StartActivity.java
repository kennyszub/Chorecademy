package edu.berkeley.cs160.crappymalefemaleratio.chore;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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
		final Intent i = new Intent(this, ChildActivity.class);
		final MediaPlayer mp = MediaPlayer.create(this, R.raw.chewie);
    	
        childButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		mp.start();
        		startActivity(i);
        	}
        });
	}
	
	private void addListenerOnParentButton() {

		final Button parentButton = (Button) findViewById(R.id.parentButton);
		final Intent i = new Intent(this, ParentLoginActivity.class);
		final MediaPlayer mp = MediaPlayer.create(this, R.raw.darth);

		parentButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mp.start();
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
