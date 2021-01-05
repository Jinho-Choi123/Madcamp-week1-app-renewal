package com.example.myapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;

public class Board_myPage_Adapter extends RecyclerView.Adapter<Board_myPage_Adapter.ViewHolder>{
    private Context context;
    private ArrayList<Board_content> boardContents;
    public static Board_DB db;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public TextView dateView;
        public TextView contentView;

        public Button deletebtn;
        public Button updatebtn;


        public ViewHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.card_title);
            dateView = (TextView) view.findViewById(R.id.card_date);
            contentView = (TextView) view.findViewById(R.id.card_content);

            view.findViewById(R.id.delete_board).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Logger.log("dddddddddddddddddelete!!!!!!!!!", "deleting");
                    db.Ref.child((String)titleView.getText()).setValue(null);
                }
            });

            view.findViewById(R.id.card_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.log("uuuuuuuuuuuuuuuuuuupdate!!!!", "updating");
                    db.Ref.child((String)titleView.getText()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Board_content board_content = snapshot.getValue(Board_content.class);
                            showUpdateDialog(board_content);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

        }
    }

    private void showUpdateDialog(Board_content board_content) {
        LayoutInflater vi = (LayoutInflater) ((MainActivity) this.context).getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout loginLayout = (LinearLayout) vi.inflate(R.layout.dialog, null);
        final EditText Title = (EditText)loginLayout.findViewById(R.id.title);
        final EditText Context = (EditText)loginLayout.findViewById(R.id.pcontext);
        Title.setText(board_content.getTitle());
        Context.setText(board_content.getContent());

        new AlertDialog.Builder(((MainActivity) this.context)).setTitle("Posting").setView(loginLayout).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override public void onClick(DialogInterface dialog, int which)
                    {
                        Board_content b = new Board_content();

                        b.Board_content(Title.getText().toString(),
                                Context.getText().toString(), LocalDate.now().toString()
                                , board_content.getAuthor());
                        db.Ref.child(board_content.getTitle()).setValue(null);

                        db.writeNewPost(b);
                    }
                }).show();

    }


    public Board_myPage_Adapter(ArrayList<Board_content> board_contents, Board_DB db, Context context) {
        this.boardContents = board_contents;
        this.db = db;
        this.context = context;
    }

    @Override
    public Board_myPage_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_content_mypage_recycleview, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleView.setText(this.boardContents.get(position).getTitle());
        holder.dateView.setText(this.boardContents.get(position).getDate());
        holder.contentView.setText(this.boardContents.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return boardContents.size();
    }
}
