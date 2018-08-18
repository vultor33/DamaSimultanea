package com.example.android.damasimultanea;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private PiecesPositions pieces = new PiecesPositions();

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
        if (pieces.isPlayablePosition(position)) {
            holder.myTextView.setBackgroundColor(Color.BLACK);
            holder.pieceImage.setVisibility(View.VISIBLE);
        } else {
            holder.myTextView.setBackgroundColor(Color.WHITE);
            holder.pieceImage.setVisibility(View.INVISIBLE);
        }

        switch (pieces.whichPiece(position)){
            case 0:
                holder.pieceImage.setVisibility(View.INVISIBLE);
                break;

            case 1:
                holder.pieceImage.setVisibility(View.VISIBLE);
                holder.pieceImage.setColorFilter( Color.BLUE, PorterDuff.Mode.MULTIPLY );
                break;

            case 2:
                holder.pieceImage.setVisibility(View.VISIBLE);
                holder.pieceImage.setColorFilter( Color.GREEN, PorterDuff.Mode.MULTIPLY );
                break;

                default:
                    holder.pieceImage.setVisibility(View.INVISIBLE);//fredmudar esses dois swirchs tambem nao tem muita condicao throw exception
        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return pieces.getTableSize();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return String.valueOf(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void changeBackgroundColor(int position){ //fredmudar adicionar o viewholder aqui e mudar a cor do background.
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
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
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}