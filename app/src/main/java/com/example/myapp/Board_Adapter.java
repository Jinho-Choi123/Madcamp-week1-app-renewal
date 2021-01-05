package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Board_Adapter extends RecyclerView.Adapter<Board_Adapter.ViewHolder>{
    private Context context;
    private ArrayList<Board_content> boardContents;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView dateView;
        public TextView contentView;
        public TextView authorView;

        public ViewHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.card_title);
            dateView = (TextView) view.findViewById(R.id.card_date);
            contentView = (TextView) view.findViewById(R.id.card_content);
            authorView = (TextView) view.findViewById(R.id.byauthor);
        }
    }

    public Board_Adapter(ArrayList<Board_content> board_contents) {
        this.boardContents = board_contents;
    }

    @Override
    public Board_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_content_recycleview, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleView.setText(this.boardContents.get(position).getTitle());
        holder.dateView.setText(this.boardContents.get(position).getDate());
        holder.contentView.setText(this.boardContents.get(position).getContent());
        holder.authorView.setText(this.boardContents.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return boardContents.size();
    }
}
