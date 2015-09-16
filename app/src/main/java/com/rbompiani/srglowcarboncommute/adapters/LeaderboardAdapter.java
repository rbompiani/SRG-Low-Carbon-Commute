package com.rbompiani.srglowcarboncommute.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseUser;
import com.rbompiani.srglowcarboncommute.R;

/**
 * Created by bompi_000 on 8/31/2015.
 */
public class LeaderboardAdapter extends BaseAdapter{

    private Context mContext;
    private ParseUser[] mUsers;

    public LeaderboardAdapter(Context context, ParseUser[] users){
        mUsers = users;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mUsers.length;
    }

    @Override
    public Object getItem(int position) {
        return mUsers[position];
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

        ParseUser user = mUsers[position];

        holder.employeeTextView.setText(user.getUsername());

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
