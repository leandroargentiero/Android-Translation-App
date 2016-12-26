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

import com.google.firebase.database.DatabaseReference;

public class tab1List extends Fragment{

    private DatabaseReference mDatabaseReference; //locatie in db

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_list, container, false);

        // RAW DATA
        String[] lijstWoorden = {
                "Frans woord 1",
                "Frans woord 2",
                "Frans woord 3"
        };

        //FIND LISTVIEW BY ID
        ListView listView = (ListView) view.findViewById(R.id.listView_list);

        //ADAPTER DAT LISTVIEW ZEGT WELKE ITEMS ER MOETEN GEBRUIKT WORDEN
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                lijstWoorden
        );

        //GEEF LISTVIEW DE JUISTE ADAPTER MEE
        listView.setAdapter(listViewAdapter);

        return view;
    }
}
