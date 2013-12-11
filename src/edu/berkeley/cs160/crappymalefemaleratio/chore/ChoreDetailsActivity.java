package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHORE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DATE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DESCRIPTION;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.POINTS;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ChoreDetailsActivity extends Activity {
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
	public Uri ourURI;
	Context context;
	private long choreID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chore_details);
		
		context = getApplicationContext();
		setTextValues();
		addListenerOnDoneButton();
		takePictureListener();
		
		
		showPicture();
		

	}
	private void takePictureListener(){
	 	/* Take Picture Mode */
	    final Button pic_button = (Button) findViewById(R.id.takePicButton);
	    pic_button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
         	 		Toast.makeText(getApplicationContext(), "Picture Mode", Toast.LENGTH_SHORT).show();
		            //Intent picmode = new Intent(ChoreDetailsActivity.this.getApplicationContext(), PictureActivity.class);
		            //startActivity(picmode);
         	 		initCamera();
             }
	    });
	}
	
	private void addListenerOnDoneButton() {
		final Button doneButton = (Button) findViewById(R.id.doneButton);
		
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView idView = (TextView) findViewById(R.id.choreId);
            	Long id = Long.parseLong((String) idView.getText());
            	System.out.println("ID: "+id+"");
            	
	        	DataModel.finishChore(context, (String) idView.getText());
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
		    choreID = session.getLong("id");

		    
		    // Set variables to TextView
		    TextView choreName, choreDescription, choreDuedate, chorePoints, choreIDValue;
		    choreIDValue = (TextView) findViewById(R.id.choreId);
		    choreName = (TextView) findViewById(R.id.choreName);
		    choreDescription = (TextView) findViewById(R.id.choreDescription);
		    choreDuedate = (TextView) findViewById(R.id.choreDuedate);
		    chorePoints = (TextView) findViewById(R.id.chorePoints);

		    choreIDValue.setText(String.valueOf(choreID));
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
	@SuppressLint("SimpleDateFormat")
	private void initCamera(){
		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);	
		
		/* Create File Directory */
		//String pic_directory = Environment.getExternalStorageDirectory() + "/GetAwayCam/";
		//File getawaycam = new File(pic_directory);
		//getawaycam.mkdirs();

		/* Create File to Save To */
		SimpleDateFormat current_time = new SimpleDateFormat("ddMMyyyyhhmmss");
		String current_date = current_time.format(new Date());
		// Toast.makeText(this.getApplicationContext(), "Date: "+current_date, Toast.LENGTH_LONG).show();
		
		File imageFolder = new File(Environment.getExternalStorageDirectory(),"/Chorecademy/");
		imageFolder.mkdirs();
		if (imageFolder.exists()) {
			File filename = new File(
					imageFolder, current_date + choreID + ".jpg");
			//filename.mkdirs();
			//if (filename.exists()) {
				ourURI = Uri.fromFile(filename);	
				System.out.println(filename);
				
				/* Start Camera */
				intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, ourURI);
				System.out.println(intent.getData());
			 	startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			//}
			
		} else {
			Toast.makeText(this, "No dice", Toast.LENGTH_LONG).show();
		}
		
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

    	//System.out.println("here1");
        
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
        	//System.out.println("here");
        	if (resultCode == RESULT_OK) {
        		//System.out.println("here2");
        		//Uri ourURI = data.getData();
        		
        		//Toast.makeText(this, "Image saved to:\n" +
                //        ourURI, Toast.LENGTH_LONG).show();
        		
        		DataModel.addURL(this, choreID, ourURI.toString());
            	showPicture();
                //System.out.println(data.toString());
        	} else {
        		System.out.println("CameraDemo Pics Not Saved ");
        	}
        }
    }
    
    public void showPicture() {
    	ImageView choreImage = (ImageView) findViewById(R.id.chorePicture);
    	JSONArray chores = DataModel.getChores(context);
    	System.out.println("chore ID: "+ choreID);
    	int ourIndex = DataModel.findIndexById(context, "chores", Long.toString(choreID));
    	try {
    		//if chores
    		Button doneButton = (Button)findViewById(R.id.doneButton);
    		JSONObject chore = chores.getJSONObject(ourIndex);
    		if (chore.has("url")) {
    			
        		doneButton.setBackgroundResource(R.drawable.red_button);
        		doneButton.setEnabled(true);
    			String choreURI = chore.getString("url");
        		ourURI = Uri.parse(choreURI);
        		choreImage.setImageURI(ourURI);
    		} else {
    			doneButton.setBackgroundResource(R.drawable.gray_button);
    			doneButton.setEnabled(false);
    		}
    		
    	} catch (JSONException e) {
    		System.err.println("ERROR: Failed to index into JSONArray In Chore Details: " + e.getMessage());
    		System.exit(1);
    		//return 999;
    	}
    	
    	
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	

}
