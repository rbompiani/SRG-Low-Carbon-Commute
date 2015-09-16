package com.rbompiani.srglowcarboncommute.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.rbompiani.srglowcarboncommute.R;
import com.rbompiani.srglowcarboncommute.data.Rider;

/**
 * Created by bompi_000 on 8/31/2015.
 */
public class LeaderboardAdapter2 extends BaseAdapter{

    private Context mContext;
    private Rider[] mRiders;

    public LeaderboardAdapter2(Context context, Rider[] riders){
        mRiders = riders;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mRiders.length;
    }

    @Override
    public Object getItem(int position) {
        return mRiders[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;//not used
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            //brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.leaderboard_list_item, null);
            holder= new ViewHolder();
            holder.employeeTextView = (TextView) convertView.findViewById(R.id.employee);
            holder.milesTextView = (TextView) convertView.findViewById(R.id.miles);
            holder.tripsTextView = (TextView) convertView.findViewById(R.id.trips);
            holder.commuteTextView = (TextView) convertView.findViewById(R.id.commute_type);
            holder.officeTextView = (TextView) convertView.findViewById(R.id.city);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Rider rider = mRiders[position];

        holder.employeeTextView.setText(rider.getFirstName()+" "+rider.getLastName());
        holder.commuteTextView.setText(rider.getCommute());
        holder.milesTextView.setText(Double.toString(rider.getMiles()));
        holder.officeTextView.setText(rider.getOffice().substring(0,1));

        if(rider.getOffice().equals("Seattle")){
            holder.officeTextView.setTextColor(mContext.getResources().getColor(R.color.blue));
        } else {
            holder.officeTextView.setTextColor(mContext.getResources().getColor(R.color.rorange));
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView employeeTextView;
        TextView milesTextView;
        TextView tripsTextView;
        TextView commuteTextView;
        TextView officeTextView;
    }
}
