package com.example.myapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Board_myPage_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Board_myPage_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public Board_myPage_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Board_myPage_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Board_myPage_fragment newInstance(String param1, String param2) {
        Board_myPage_fragment fragment = new Board_myPage_fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_my_page_fragment, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoginDialog();
            }
        });
        return view;
    }


    private void showLoginDialog() {
        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout loginLayout = (LinearLayout) vi.inflate(R.layout.dialog, null);
        final EditText Title = (EditText)loginLayout.findViewById(R.id.title);
        final EditText Context = (EditText)loginLayout.findViewById(R.id.pcontext);
        SharedPreferences sf = this.getActivity().getSharedPreferences("googleAccount", MODE_PRIVATE);
        String userId = sf.getString("userId", "");

        new AlertDialog.Builder(getActivity()).setTitle("Posting").setView(loginLayout).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which)
            {
                Board_DB bdb = new Board_DB(userId);

                Board_content b = new Board_content();

                b.Board_content(Title.getText().toString(),
                        "kim@naver.com",
                        Context.getText().toString()
                        , "2020-12-04");



                Bundle bundle = getActivity().getIntent().getExtras();
                String email =bundle.getString("email");

                Date date = Calendar.getInstance().getTime();
                String date_text = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);

                //bdb.writeNewPost(Title.getText().toString(),"author",
                  //      email,date_text);

                bdb.writeNewPost(Title.getText().toString(),
                        email,Context.getText().toString(),date_text);
            }
        }).show();
    }

}