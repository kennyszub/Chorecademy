package edu.berkeley.cs160.crappymalefemaleratio.chore;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetReward extends Activity implements OnClickListener {

	EditText reward_name;
	EditText reward_cost;
	TextView view_reward_name;
	TextView view_reward_cost;
	static String filename = "RewardInfo";
	SharedPreferences prefs;
	Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_reward);
		prefs = getSharedPreferences(filename, 0); // retrieves and holds contents of the name file, 0 sets a writable mode
		setupVariables();
	}

	public void setupVariables() {
		btn = (Button)findViewById(R.id.save_button);
		
		reward_name = (EditText)findViewById(R.id.reward_name);
		reward_cost = (EditText)findViewById(R.id.reward_cost);
		
		view_reward_name = (TextView)findViewById(R.id.load_cost);
		view_reward_cost = (TextView)findViewById(R.id.load_name);
		
		btn.setOnClickListener(this); // set the button here
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.save_button:
			SharedPreferences.Editor editor = prefs.edit();
			String name = reward_name.getText().toString();
			String cost = reward_cost.getText().toString();	
			editor.putString("name", name);
			editor.putString("cost", cost);
			editor.commit();
		break;
		
		case R.id.load_button:
			prefs = getSharedPreferences(filename, 0);
			String load_name = prefs.getString("name", "couldnt get name");
			String load_cost = prefs.getString("cost", "couldnt get cost");
			view_reward_name.setText(load_name);
			view_reward_cost.setText(load_cost);
		break;
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_reward, menu);
		return true;
	}



}
