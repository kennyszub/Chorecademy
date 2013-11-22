package edu.berkeley.cs160.crappymalefemaleratio.chore;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

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
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private class ViewHolder {
         TextView txtFirst;
         TextView txtSecond;
         TextView txtThird;
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
            holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
            holder.txtThird = (TextView) convertView.findViewById(R.id.ThirdText);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        Button detailsButton = (Button) convertView.findViewById(R.id.detailsButton);
        addListenerOnDetailsButton(detailsButton);

        HashMap<String, String> map = list.get(position);
        holder.txtFirst.setText(map.get("First"));
        holder.txtSecond.setText(map.get("Second"));
        holder.txtThird.setText(map.get("Third"));

        return convertView;
	}
	
	
	 private void addListenerOnDetailsButton(Button detailsButton) {
		final Intent intent = new Intent(activity, ChildDetailsActivity.class);
        detailsButton.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		// Access Row's values
            	String chore_name, chore_description, chore_duedate, chore_points;
            	chore_name = "Chore Name";
            	chore_description = "Some Description";
            	chore_duedate = "10/8";
            	chore_points = "180";
            	// Pass SESSION variables to be accessed in Detailed View
            	intent.putExtra("CHORE_NAME", chore_name);
            	intent.putExtra("CHORE_DESCRIPTION", chore_description);
            	intent.putExtra("CHORE_DUEDATE", chore_duedate);
            	intent.putExtra("CHORE_POINTS", chore_points);

                activity.startActivity(intent);
        	}
        });
	}

}
