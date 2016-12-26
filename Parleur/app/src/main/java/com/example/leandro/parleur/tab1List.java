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
                android.R.layout.simple_list_item_1,
                mWoorden);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Word newWord = dataSnapshot.getValue(Word.class);
                mWoorden.add(newWord.getWoord());
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

        /*GET LISTVIEW BY ID
        listview = (ListView) view.findViewById(R.id.listView_list);

        //Database connection
        dref= FirebaseDatabase.getInstance().getReference();
        //When something changes in database pass it to our list
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                lijstWoorden.add(value);
                adapter.notifyDataSetChanged();
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

        //New ArrayAdapter
        adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                lijstWoorden
        );
        */


        /* RAW DATA
        String[] lijstWoorden = {
                "Frans woord 1",
                "Frans woord 2",
                "Frans woord 3"
        };





        //ADAPTER DAT LISTVIEW ZEGT WELKE ITEMS ER MOETEN GEBRUIKT WORDEN
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                lijstWoorden
        );
        */



        return view;
    }
}
