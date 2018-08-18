package com.example.android.damasimultanea;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private int[] enabledPositions = {
            0,2,4,6,
            9,11,13,15,
            16,18,20,22,
            25,27,29,31,
            32,34,36,38,
            41,43,45,47,
            48,50,52,54,
            57,59,61,63};

    int numberOfElements = 64;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isPlayablePosition(position)) {
            holder.myTextView.setBackgroundColor(Color.BLACK);
            holder.pieceImage.setVisibility(View.VISIBLE);
        } else {
            holder.myTextView.setBackgroundColor(Color.WHITE);
            holder.pieceImage.setVisibility(View.INVISIBLE);
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return numberOfElements;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView pieceImage;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.rv_item_text);
            pieceImage = (ImageView) itemView.findViewById(R.id.rv_item_circle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return String.valueOf(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    boolean isPlayablePosition(int position){
        for (int enabled : enabledPositions){
            if(position == enabled)
                return true;
        }
        return false;
    }

}