package com.smu_bme.jigsaw;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bme-lab2 on 4/28/16.
 */
public class mAdapter extends RecyclerView.Adapter<mAdapter.ViewHolder> {

    private Context context;
    private String calendar;
    private DbHelper dbHelper;
    private List<DbData> list;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView time;
        public TextView duration;
        public ImageButton more;

        public ViewHolder(View v){
            super(v);
            name = (TextView) v.findViewById(R.id.card_name);
            time = (TextView) v.findViewById(R.id.card_time);
            duration = (TextView) v.findViewById(R.id.card_duration);
            more = (ImageButton) v.findViewById(R.id.card_more);
        }
    }

    public mAdapter(String calendar, Context context){
        this.calendar = calendar;
        this.context = context;
        dbHelper = new DbHelper(this.context);
        this.list = dbHelper.queryData("date", "1970-1-1");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        TODO  Using Database here:
        if (list.isEmpty()){
            Log.d("DEBUGGING", "list is empty");
        } else {
            Log.d("DEBUGGING", "list not empty");
            holder.name.setText(list.get(position).getName());
            holder.time.setText(list.get(position).getTime());
            holder.duration.setText(list.get(position).getDuration());
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CardActivity.class);
                    intent.putExtra("Event", 1);
                    context.startActivity(intent);
                }
            });}
    }

    @Override
    public int getItemCount() {
        return new DbHelper(context).querySum(calendar);
    }

}
