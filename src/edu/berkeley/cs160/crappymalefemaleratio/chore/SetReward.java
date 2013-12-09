package edu.berkeley.cs160.crappymalefemaleratio.chore;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SetReward extends Activity {


	static String filename = "RewardInfo";
	Button btn;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_reward);
		saveReward();
		context = this;
	}
	
	public JSONObject getFieldData() {
		TextView name, description, points;
		name = (TextView) findViewById(R.id.reward_name);
		description = (TextView) findViewById(R.id.reward_details);
		points = (TextView) findViewById(R.id.reward_cost);
		
		try {
			JSONObject reward = new JSONObject();
			reward.put("name", name.getText().toString());
			reward.put("description", description.getText().toString());
			reward.put("points", points.getText());
			return reward;
		} catch (JSONException e) {
			System.err.println("ERROR: Failed to create reward: " + e.getMessage());
			System.exit(1);
			return null;
		}
	}

	protected void saveReward() {
		final Button addReward = (Button) findViewById(R.id.addReward);
		//add intent for going back to main parent
		addReward.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				JSONObject rewardInfo = getFieldData();
				try {
					if (rewardInfo.getString("name").isEmpty() ||
						rewardInfo.getString("description").isEmpty() ||
						rewardInfo.getString("points").isEmpty()) {

						CharSequence text = "Please fill in all fields";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					} else {
						DataModel.addReward(context, rewardInfo);
						finish();
					}
				} catch (JSONException e) {
					System.err.println("ERROR: malformed choreInfo");
					System.exit(1);
				}	
			}
		});
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_reward, menu);
		return true;
	}



}
