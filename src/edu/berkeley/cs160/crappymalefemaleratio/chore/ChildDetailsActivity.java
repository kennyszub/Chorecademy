package edu.berkeley.cs160.crappymalefemaleratio.chore;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ChildDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_child_details);
		
		Bundle session = getIntent().getExtras();
		if (session != null) {
			// Grab SESSION variables
			String chore_name, chore_description, chore_duedate, chore_points;
		    chore_name = session.getString("CHORE_NAME");
		    chore_description = session.getString("CHORE_DESCRIPTION");
		    chore_duedate = session.getString("CHORE_DUEDATE");
		    chore_points = session.getString("CHORE_POINTS");
		    // Set variables to TextView
		    TextView choreName, choreDescription, choreDuedate, chorePoints;
		    choreName = (TextView) findViewById(R.id.choreName);
		    choreName.setText(chore_name);
		    choreDescription = (TextView) findViewById(R.id.choreDescription);
		    choreDescription.setText(chore_description);
		    choreDuedate = (TextView) findViewById(R.id.choreDuedate);
		    choreDuedate.setText(chore_duedate);
		    chorePoints = (TextView) findViewById(R.id.chorePoints);
		    chorePoints.setText(chore_points+" Points");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	

}
