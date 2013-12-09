package edu.berkeley.cs160.crappymalefemaleratio.chore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.widget.Toast;

public class PictureActivity extends Activity {
	/* Variables */
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
	Bitmap final_bitmap;
	String result_photo_url;
	String test1 = "out";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		initCamera();
	}
	
	@SuppressLint("SimpleDateFormat")
	private void initCamera(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);	
		
		/* Create File Directory */
		String pic_directory = Environment.getExternalStorageDirectory() + "/GetAwayCam/";
		File getawaycam = new File(pic_directory);
		getawaycam.mkdirs();

		/* Create File to Save To */
		SimpleDateFormat current_time = new SimpleDateFormat("ddMMyyyyhhmmss");
		String current_date = current_time.format(new Date());
		// Toast.makeText(this.getApplicationContext(), "Date: "+current_date, Toast.LENGTH_LONG).show();
		File filename = new File(
				Environment.getExternalStorageDirectory() + "/Chorecademy/", current_date + ".jpg");
		Uri	file_uri = Uri.fromFile(filename);	
		
		/* Start Camera */
		intent.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);	
	 	startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	protected Uri getOutputMediaFileUri(int mediaTypeImage) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

    	System.out.println("here1");
        
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
        	System.out.println("here");
        	if (resultCode == RESULT_OK) {
        		Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            	
                System.out.println(data.toString());
        	} else {
        		System.out.println("CameraDemo Pics Not Saved ");
        	}
        }
}


}
