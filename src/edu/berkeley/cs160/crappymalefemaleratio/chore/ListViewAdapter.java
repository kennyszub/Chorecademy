package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHORE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.DATE;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.POINTS;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.MILLIS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Calendar;

public class ListViewAdapter extends BaseAdapter {
	public ArrayList<HashMap<String, String>> list;
    private Activity activity;
 
    public ListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	public void updateList(ArrayList<HashMap<String, String>> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	
	private class ViewHolder {
         TextView chore;
         TextView date;
         TextView points;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listview_row, null);
            holder = new ViewHolder();
            holder.chore = (TextView) convertView.findViewById(R.id.Chore);
            holder.date = (TextView) convertView.findViewById(R.id.Date);
            holder.points = (TextView) convertView.findViewById(R.id.Points);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        HashMap<String, String> map = list.get(position);
        holder.chore.setText(map.get(CHORE));
        

        
        // modify due date texts
        Calendar currentCalendar = Calendar.getInstance();
        Calendar choreCalendar = Calendar.getInstance();
        choreCalendar.setTimeInMillis(Long.parseLong(map.get(MILLIS)));

        if (sameDay(currentCalendar, choreCalendar)) {
        	holder.date.setText("Today");
        	holder.date.setTextColor(activity.getResources().getColor(R.color.Crimson));
        } else if (nextDay(currentCalendar, choreCalendar)) {
        	holder.date.setText("Tomorrow");
        } else if (overDue(currentCalendar, choreCalendar)) {
        	holder.date.setText("Overdue!");
        	holder.date.setTextColor(activity.getResources().getColor(R.color.Crimson));
        	holder.date.setTypeface(null, Typeface.BOLD);
        } else {
        	holder.date.setText(map.get(DATE));
        	holder.date.setTextColor(activity.getResources().getColor(R.color.LightSlateGray));
        	holder.date.setTypeface(null, Typeface.NORMAL);
        }
        
        holder.points.setText(map.get(POINTS));

        return convertView;
	}
	
	private boolean sameDay(Calendar cal1, Calendar cal2) {
		boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) ==
						  cal2.get(Calendar.DAY_OF_YEAR);
		boolean sameYear = cal1.get(Calendar.YEAR) ==
						   cal2.get(Calendar.YEAR);
		return sameDay && sameYear;
	}
	
	/* Return true if cal2 is one day after cal1. */
	private boolean nextDay(Calendar cal1, Calendar cal2) {
		cal1.add(Calendar.DATE, 1);
		return sameDay(cal1, cal2);
	}
	
	/* Return true if cal2 is before cal1. */
	private boolean overDue(Calendar cal1, Calendar cal2) {
		return cal2.getTimeInMillis() < cal1.getTimeInMillis();
	}

}
