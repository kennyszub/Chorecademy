package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHORE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DATE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DESCRIPTION;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.POINTS;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ChoreDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chore_details);
		
		setTextValues();
		addListenerOnDoneButton();
		takePictureListener();
	}
	private void takePictureListener(){
	 	/* Take Picture Mode */
	    final Button pic_button = (Button) findViewById(R.id.takePicButton);
	    pic_button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
         	 		Toast.makeText(getApplicationContext(), "Picture Mode", Toast.LENGTH_SHORT).show();
		            Intent picmode = new Intent(ChoreDetailsActivity.this.getApplicationContext(), PictureActivity.class);
		            startActivity(picmode);
             }
	    });
	}
	
	private void addListenerOnDoneButton() {
		final Button doneButton = (Button) findViewById(R.id.doneButton);
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void setTextValues() {
		Bundle session = getIntent().getExtras();
		if (session != null) {
			// Grab SESSION variables
			String chore_name, chore_description, chore_duedate, chore_points;
		    chore_name = session.getString(CHORE);
		    chore_description = session.getString(DESCRIPTION);
		    chore_duedate = session.getString(DATE);
		    chore_points = session.getString(POINTS);
		    
		    // Set variables to TextView
		    TextView choreName, choreDescription, choreDuedate, chorePoints;
		    choreName = (TextView) findViewById(R.id.choreName);
		    choreDescription = (TextView) findViewById(R.id.choreDescription);
		    choreDuedate = (TextView) findViewById(R.id.choreDuedate);
		    chorePoints = (TextView) findViewById(R.id.chorePoints);

		    choreName.setText(chore_name);
		    choreDescription.setText(chore_description);
		    choreDuedate.setText(chore_duedate);
		    chorePoints.setText(chore_points + " Points");
		    
		    // make due date Today red
		    if (chore_duedate.equals("Today")) {
		    	choreDuedate.setTextColor(this.getResources().getColor(R.color.OrangeRed));
		    }
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	

}
