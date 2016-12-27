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

import com.example.leandro.parleur.Models.Word;
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


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_list, container, false);

        // Get ListView by ID
        mListView = (ListView)view.findViewById(R.id.listView_list);

        // DB connection
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://examen-mobdev.firebaseio.com/woorden");

        // Create new ArrayAdapter
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_2,
                mWoorden);


        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Word newWord = dataSnapshot.getValue(Word.class);
                mWoorden.add(newWord.getWoord());
                mVertalingen.add(newWord.getVertaling());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mListView.setAdapter(arrayAdapter);

        return view;
    }
}
