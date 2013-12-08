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

public class ClaimsListViewAdapter extends BaseAdapter {
	public ArrayList<HashMap<String, String>> list;
    private Activity activity;
 
    public ClaimsListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
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
         TextView reward;
         TextView value;
         TextView claim;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.claims_listview_row, null);
            holder = new ViewHolder();
            holder.reward = (TextView) convertView.findViewById(R.id.Claimed_Reward);
            holder.value = (TextView) convertView.findViewById(R.id.Claimed_Value);
            holder.claim = (TextView) convertView.findViewById(R.id.Claimed_Claim);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        HashMap<String, String> map = list.get(position);
        holder.reward.setText(map.get(CLAIMED_REWARD));
        holder.value.setText(map.get(CLAIMED_VALUE));
        holder.claim.setText("Claimed!");

        return convertView;
	}

}
