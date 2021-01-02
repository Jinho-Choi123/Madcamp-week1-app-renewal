package com.example.myapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fourth_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fourth_fragment extends Fragment {

    public Fourth_fragment() {
        // Required empty public constructor
    }

    public static Fourth_fragment newInstance(String param1, String param2) {
        Fourth_fragment fragment = new Fourth_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fourth_fragment, container, false);

        ChatView chatview = view.findViewById(R.id.chat_view);

        //event handler when send button is clicked
        chatview.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {

                return true;
            }
        });

        return view;
    }
}