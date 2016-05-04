package com.smu_bme.jigsaw;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bme-lab2 on 4/28/16.
 */
public class mAdapter extends RecyclerView.Adapter<mAdapter.ViewHolder> implements View.OnClickListener{

    private Context context;
    private String calendar;
    private DbHelper dbHelper;
    private List<DbData> list;
    private CardView cardView;
    private int j;


    public mAdapter(List<DbData> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        j = holder.getAdapterPosition();
        holder.name.setText(list.get(position).getName());
        Log.d("DEBUGGING1", list.get(position).getTime());
        holder.time.setText(list.get(position).getTime());
        holder.duration.setText(String.valueOf(list.get(position).getDuration()));
        holder.more.setOnClickListener(this);
        holder.cardView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView time;
        public TextView duration;
        public ImageButton more;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.card_view_name);
            time = (TextView) v.findViewById(R.id.card_view_time);
            duration = (TextView) v.findViewById(R.id.card_view_duration);
            more = (ImageButton) v.findViewById(R.id.card_more);
            cardView = (CardView) v.findViewById(R.id.card);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, CardActivity.class);
        intent.putExtra("Event", list.get(j));
        context.startActivity(intent);
    }
}
