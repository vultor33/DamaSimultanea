package com.example.android.damasimultanea;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.damasimultanea.database.PieceEntry;

import java.util.Arrays;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private BoardDrawings boardDrawings;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<PieceEntry> allBoardDb;

    MyRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        boardDrawings = new BoardDrawings(context);
    }


    public void setAllBoardDb(List<PieceEntry> allBoardDb_in){
        if(allBoardDb_in == null)
            Log.d("fredmudar", "nao conseguiu ler no banco de dados");
        allBoardDb = allBoardDb_in;
    }

    public int getBoardPos(int pos){
        if(allBoardDb == null)
            Log.d("fredmudar", "e null essa joca");
        return allBoardDb.get(pos).getPosition();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        boardDrawings.addHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return boardDrawings.getTableSize(); //cell count
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return String.valueOf(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void playPiece(int position){
        boardDrawings.playPiece(position);
    }

    public void endTurn(){
        boardDrawings.resolveAllMovements();
        boardDrawings.gameEndConditions();

    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
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


}