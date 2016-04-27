package com.smu_bme.jigsaw;

import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bme-lab2 on 4/28/16.
 */
public class mAdapter extends RecyclerView.Adapter<mAdapter.ViewHolder> {

    private int [] id;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;

        public ViewHolder(View v){
            super(v);
            mTextView = (TextView) v.findViewById(R.id.card_text_view);
        }
    }

    public mAdapter(int [] id){
        this.id = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText("Hello");
    }

    @Override
    public int getItemCount() {
        return id.length;
    }
}
