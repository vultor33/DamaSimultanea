package com.example.android.damasimultanea;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.damasimultanea.database.GameController;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private GameController gameController;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    MyRecyclerViewAdapter(Context context, GameController gameController_in) {
        this.mInflater = LayoutInflater.from(context);
        this.gameController = gameController_in;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        gameController.addHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return gameController.getTableSize(); //cell count
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView myTextView;
        public ImageView pieceImage;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.rv_item_text);
            pieceImage = itemView.findViewById(R.id.rv_item_circle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }





    //CLICK OPTIONS
    String getItem(int id) {
        return String.valueOf(id);
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }






    //GAME OPTIONS
    public void playPiece(int position){
        gameController.playPiece(position);
    }
    public void endTurn(){
        gameController.sendMove();
        gameController.gameEndConditions();

    }

    public void resetDatabase(){
        gameController.resetDatabase();
    }


}