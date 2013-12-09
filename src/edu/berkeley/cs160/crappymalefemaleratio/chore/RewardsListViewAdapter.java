package edu.berkeley.cs160.crappymalefemaleratio.chore;

import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CHILD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.CLAIM;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.PARENT;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.REWARD;
import static edu.berkeley.cs160.crappymalefemaleratio.chore.Constants.Constant.VALUE;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RewardsListViewAdapter extends BaseAdapter {
	public ArrayList<HashMap<String, String>> list;
    private Activity activity;
    private String mode;
 
    public RewardsListViewAdapter(Activity activity, ArrayList<HashMap<String, String>> list, String m) {
        super();
        this.activity = activity;
        this.list = list;
        this.mode = m;
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
            convertView = inflater.inflate(R.layout.rewards_listview_row, null);
            holder = new ViewHolder();
            holder.reward = (TextView) convertView.findViewById(R.id.Reward);
            holder.value = (TextView) convertView.findViewById(R.id.Value);
            holder.claim = (Button) convertView.findViewById(R.id.Claim);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        HashMap<String, String> map = list.get(position);
        holder.reward.setText(map.get(REWARD));
        holder.value.setText(map.get(VALUE));

       if(mode.equals(PARENT)){
        	holder.claim.setVisibility(View.GONE);
        }else{
	        if(map.get(CLAIM) == "false"){
	        	holder.claim.setText("Claim");
	        }else{
	        	holder.claim.setText("Pending");
	        }

	        final Button claimButton = (Button) holder.claim;//(Button) rowView.findViewById(R.id.Claim);
	        claimButton.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	RelativeLayout row = (RelativeLayout) claimButton.getParent();
	            	TextView rewardView = (TextView) row.findViewById(R.id.Reward);
	            	TextView pointsView = (TextView) row.findViewById(R.id.Value);
	            	String reward = (String) rewardView.getText();
	            	String points = (String) pointsView.getText();
	            	
	            	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	            	    @Override
	            	    public void onClick(DialogInterface dialog, int which) {
	            	        switch (which){
	            	        case DialogInterface.BUTTON_POSITIVE:
	            	            //Yes button clicked
	            	            break;

	            	        case DialogInterface.BUTTON_NEGATIVE:
	            	            //No button clicked
	            	            break;
	            	        }
	            	    }
	            	};

	            	AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
	            	String claim_message = "Claim " + reward + "?";
	            	builder.setMessage(claim_message).setPositiveButton("Yes", dialogClickListener)
	            	    .setNegativeButton("No", dialogClickListener).show();
	            }
	        });
        }
        return convertView;
	}

}
