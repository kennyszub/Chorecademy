package edu.berkeley.cs160.crappymalefemaleratio.chore;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddChoreActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
	Button addChore;
	RelativeLayout currentView;
	public GregorianCalendar dueDate;
	public String dateText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_chore);
		setAddChoreOnClick();
		setDatePickerOnClick();
		setTimePickerOnClick();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_chore, menu);
		return true;
	}
	
	
	protected void setAddChoreOnClick() {
		final Button addChore = (Button) findViewById(R.id.addChore);
		//add intent for going back to main parent
		addChore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//start main parent activity
				//save current chore data
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
		dateText = monthText + " " + dayText + ", " + yearText;
		
		TextView dueText = (TextView) findViewById(R.id.dueDateText);
	    //System.out.println(getDateAsText());
	    dueText.setText(dateText);
	    dueText.setBackgroundColor(this.getResources().getColor(R.color.gray));
	    dueText.setTextColor(this.getResources().getColor(R.color.WhiteSmoke));
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
			return new DatePickerDialog(getActivity(), (AddChoreActivity)getActivity(), year, month, day);
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

}
