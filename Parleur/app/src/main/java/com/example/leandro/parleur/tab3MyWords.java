package com.example.leandro.parleur;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Leandro on 20/12/16.
 */

public class tab3MyWords extends Fragment {

    private ListView mListView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_my_words, container, false);

        TextView txtWoord = (TextView)view.findViewById(R.id.txtTest);
        mListView = (ListView)view.findViewById(R.id.listview_tab3);

        final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String result = app_preferences.getString("frans_woord", "");

        return view;
    }
}
