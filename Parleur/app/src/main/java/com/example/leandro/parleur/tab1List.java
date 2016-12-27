package com.example.leandro.parleur;

/**
 * Created by Leandro on 20/12/16.
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leandro.parleur.Models.Word;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class tab1List extends Fragment{

    private DatabaseReference mDatabase;
    private ArrayList<String>  mWoorden = new ArrayList<>();
    private ArrayList<String> mVertalingen = new ArrayList<>();
    private ListView mListView;
    private FirebaseListAdapter<Word> mAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab1_list, container, false);

        // Get ListView by ID
        mListView = (ListView)view.findViewById(R.id.listView_list);

        // DB connection
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://examen-mobdev.firebaseio.com/woorden");

        //Firebase Adapter
        mAdapter = new FirebaseListAdapter<Word>(
                getActivity(),
                Word.class, android.R.layout.simple_list_item_2,
                mDatabase
        ) {
            @Override
            protected void populateView(View v, Word model, int position) {
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getWoord());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getVertaling());
            }
        };
        mListView.setAdapter(mAdapter);
        return view;
    }
}
