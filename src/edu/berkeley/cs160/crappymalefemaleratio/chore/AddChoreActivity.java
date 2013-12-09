package edu.berkeley.cs160.crappymalefemaleratio.chore;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONException;
import org.json.JSONObject;

import edu.berkeley.cs160.crappymalefemaleratio.chore.R.color;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;

import android.content.DialogInterface;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddChoreActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
	Button addChore;
	RelativeLayout currentView;
	private GregorianCalendar dueDate;
	private GregorianCalendar defaultDate;
	private String dateText;
	private Context context;
	private long timeInMillis;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_chore);
		setDefaultDate();
		setDefaultPoints();
		
		setAddChoreOnClick();
		setDatePickerOnClick();
		setTimePickerOnClick();

		setPointPickerOnClick();
		
		
		
		

		context = this;

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_chore, menu);
		return true;
	}
	
	protected void setDefaultDate() {
		defaultDate = new GregorianCalendar();
		String yearText = Integer.toString(defaultDate.get(Calendar.YEAR));
		
		String monthText = getMonthName(defaultDate.get(Calendar.MONTH));
		String dayText = Integer.toString(defaultDate.get(Calendar.DATE));
		dateText = monthText + " " + dayText + ", " + yearText;
		TextView dueText = (TextView) findViewById(R.id.dueDateText);
	    
	    dueText.setText(dateText);
	    dueText.setTextColor(getResources().getColor(R.color.gray));
	}
	
	protected void setDefaultPoints() {
		TextView pointValue = (TextView)findViewById(R.id.pointValue);
		pointValue.setText(Integer.toString(5));
		pointValue.setTextColor(getResources().getColor(R.color.gray));
	}
	
	protected void setAddChoreOnClick() {
		final Button addChore = (Button) findViewById(R.id.addChore);
		//add intent for going back to main parent
		addChore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				JSONObject choreInfo = getFieldData();
				try {
					if (choreInfo.getString("name").isEmpty() ||
						choreInfo.getString("description").isEmpty() ||
						choreInfo.getString("date").isEmpty() ||
						choreInfo.getString("points").isEmpty()) {
						
						CharSequence text = "Please fill in all fields";
						int duration = Toast.LENGTH_SHORT;
						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					} else {
						DataModel.addChore(context, choreInfo);
						finish();
					}
				} catch (JSONException e) {
					System.err.println("ERROR: malformed choreInfo");
					System.exit(1);
				}	
			}
		});
		
	}
	
	protected void setDatePickerOnClick() {
		final Button setDate = (Button) findViewById(R.id.dueDateButton);
		setDate.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				showDatePickerDialog(v);
				
			}
		});
	}
	
	protected void setPointPickerOnClick() {
		final Button setPoints = (Button) findViewById(R.id.pointButton);
		setPoints.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPointPickerDialog(v);
			}
		});
	}
	
	public void showDatePickerDialog(View v) {
		
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	    
	}
	
	protected void setTimePickerOnClick() {
//		final Button setTime = (Button) findViewById(R.id.dueTimeButton);
//		setTime.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				showTimePickerDialog(v);
//			}
//		});
	}
	
	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}
	
	public void showPointPickerDialog(View v) {
		DialogFragment newFragment = new NumberPickerFragment();
		newFragment.show(getFragmentManager(), "pointPicker");
	}
	
	protected String getMonthName(int i){
		switch (i) {
		case 0:
			return "January";
		case 1:
			return "February";
		case 2:
			return "March";
		case 3:
			return "April";
		case 4:
			return "May";
		case 5:
			return "June";
		case 6:
			return "July";
		case 7:
			return "August";
		case 8:
			return "September";
		case 9:
			return "October";
		case 10:
			return "November";
		case 11:
			return "December";
		default:
			return "Moo";
					
		}
	}
	
	protected String getDayOfWeek(int i) {
		switch (i) {
		case Calendar.SUNDAY:
			return "Sunday";
		case Calendar.MONDAY:
			return "Monday";
		case Calendar.TUESDAY:
			return "Tuesday";
		case Calendar.WEDNESDAY:
			return "Wednesday";
		case Calendar.THURSDAY:
			return "Thursday";
		case Calendar.FRIDAY:
			return "Friday";
		case Calendar.SATURDAY:
			return "Saturday";
		default:
			return "Anytime";
		}
	}
	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
		//TextView date = findViewById(R.id.dueDateText);
		dueDate = new GregorianCalendar(year, month, day);
		//System.out.println(dueDate.getDisplayName(month, STYLE_NORMAL, locale));
		System.out.println(dueDate.get(Calendar.YEAR));
		System.out.println(dueDate.get(Calendar.MONTH));
		System.out.println(dueDate.get(Calendar.DATE));
		
		String yearText = Integer.toString(dueDate.get(Calendar.YEAR));
		//String monthText = Integer.toString(dueDate.get(Calendar.MONTH));
		String monthText = getMonthName(dueDate.get(Calendar.MONTH));
		String dayText = Integer.toString(dueDate.get(Calendar.DATE));
		
		String dayOfWeek = getDayOfWeek(dueDate.get(Calendar.DAY_OF_WEEK));
		
		//dateText = monthText + " " + dayText + ", " + yearText;
		dateText = dayOfWeek + ", " + monthText + " " + dayText;
		timeInMillis = dueDate.getTimeInMillis();
		
		TextView dueText = (TextView) findViewById(R.id.dueDateText);
	    //System.out.println(getDateAsText());
	    dueText.setText(dateText);
	    dueText.setTextColor(getResources().getColor(R.color.DodgerBlue));
	    
	}
	
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
//		TextView dueText = (TextView) findViewById(R.id.dueTimeText);
//		dueText.setText(Integer.toString(hourOfDay) + Integer.toString(minute));
//		System.out.println(hourOfDay);
//		System.out.println(minute);
    }
	
	public String getDateAsText() {
		return this.dateText;
	}
	
	public JSONObject getFieldData() {
		TextView name, description, points;
		name = (TextView) findViewById(R.id.editChore);
		description = (TextView) findViewById(R.id.editDetails);
		points = (TextView) findViewById(R.id.pointValue);
		
		try {
			JSONObject chore = new JSONObject();
			chore.put("name", name.getText().toString());
			chore.put("description", description.getText().toString());
			chore.put("date", dateText);
			chore.put("points", points.getText());
			chore.put("millis", timeInMillis);
			return chore;
		} catch (JSONException e) {
			System.err.println("ERROR: Failed to create chore: " + e.getMessage());
			System.exit(1);
			return null;
		}
	}

	
	public static class DatePickerFragment extends DialogFragment {
		//Date dueDate;

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			//final Activity parent = this.getActivity();
			
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), (AddChoreActivity) getActivity(), year, month, day);
		}

	}
	
	public static class TimePickerFragment extends DialogFragment {
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			
			return new TimePickerDialog(getActivity(), (AddChoreActivity)getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
			
			
		}
	}
	
	public static class NumberPickerFragment extends DialogFragment {
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			Context context = getActivity();
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			//LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			LayoutInflater li = getActivity().getLayoutInflater();
			LinearLayout newView = new LinearLayout(context);
			//final NumberPicker numPicker = new NumberPicker(context);
			//int points = numPicker.get
			
			
			//newView.addView(numPicker);
			//newView.setId(9);
			View ourView = (View) li.inflate(R.layout.number_picker, null);
			//View ourView = (View) li.inflate(R.layout.activity_add_chore, null);
			//NumberPicker temp = findViewById(R.id.numberPicker1);
			final NumberPicker numPicker = (NumberPicker) ourView.findViewById(R.id.numberPicker1);
			System.out.println(numPicker);
			
			numPicker.setMaxValue(100);
			numPicker.setMinValue(1);
			
			numPicker.setFocusable(true);
			numPicker.setFocusableInTouchMode(true);
			
			builder.setView(ourView);
			builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					TextView pointValue = (TextView) getActivity().findViewById(R.id.pointValue);
					pointValue.setText(Integer.toString(numPicker.getValue()));
					pointValue.setTextColor(getResources().getColor(R.color.DodgerBlue));
					
				}
			});
			builder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			
			
			//ourView.a
			
			return builder.create();
			
		}
	}
	
	

}
