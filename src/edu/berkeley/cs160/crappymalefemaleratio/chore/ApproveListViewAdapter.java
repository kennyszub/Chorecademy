package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.*;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ApproveListViewAdapter extends BaseAdapter {
	public ArrayList<HashMap<String, String>> list;
    private Activity activity;
 
    public ApproveListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
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
       holder.date.setText(map.get(DATE));
       if(map.get(DATE).equals("Today")){
    	   holder.date.setTextColor(activity.getResources().getColor(R.color.OrangeRed));
       }
       
       holder.points.setText(map.get(POINTS));

       return convertView;
	}

	public void updateList(ArrayList<HashMap<String, String>> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
