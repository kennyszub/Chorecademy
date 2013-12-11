package edu.berkeley.cs160.crappymalefemaleratio.chore;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ParentLoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parent_login);
		addListenerOnLoginButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.parent_login, menu);
		return true;
	}
	
	private void addListenerOnLoginButton() {

		final Button loginButton = (Button) findViewById(R.id.loginButton);
		final Intent i = new Intent(this, ParentActivity.class);

		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(i);
			}
		});

	}

}
