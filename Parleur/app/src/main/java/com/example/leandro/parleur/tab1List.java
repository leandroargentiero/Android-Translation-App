package com.example.leandro.parleur;

/**
 * Created by Leandro on 20/12/16.
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leandro.parleur.Models.Word;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class tab1List extends Fragment{

    private DatabaseReference mDatabase;
    private ListView mListView;
    private FirebaseListAdapter<Word> mAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_list, container, false);

        // Get ListView by ID
        mListView = (ListView)view.findViewById(R.id.listView_tab1);

        // DB connection
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://examen-mobdev.firebaseio.com/woorden");

        //Firebase Adapter
        mAdapter = new FirebaseListAdapter<Word>(
                getActivity(),
                Word.class,
                R.layout.custom_row_listview,
                mDatabase
        ) {
            @Override
            protected void populateView(View v, Word model, int position) {

                final String word_key = getRef(position).getKey();

                ((TextView)v.findViewById(R.id.txtWoord)).setText(model.getWoord());
                ((TextView)v.findViewById(R.id.txtVertaling)).setText(model.getVertaling());

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(), word_key, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        mListView.setAdapter(mAdapter);

        return view;
    }


}
