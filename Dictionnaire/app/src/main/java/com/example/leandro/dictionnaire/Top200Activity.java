package com.example.leandro.dictionnaire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leandro.dictionnaire.Models.Woord;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Top200Activity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    private RecyclerView mWoordenList;

    private int mLikes;
    private int mLikeCount = 5;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top200);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("woorden");

        mWoordenList = (RecyclerView) findViewById(R.id.woorden_list_top);
        mWoordenList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mWoordenList.setLayoutManager(mLayoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Query query_desc_likes = mDatabase.orderByChild("likes");

        FirebaseRecyclerAdapter<Woord, MainActivity.WoordViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Woord, MainActivity.WoordViewHolder>(

                Woord.class,
                R.layout.woord_row,
                MainActivity.WoordViewHolder.class,
                query_desc_likes

        ) {
            @Override
            protected void populateViewHolder(final MainActivity.WoordViewHolder viewHolder, final Woord model, int position) {

                final String post_key = getRef(position).getKey();

                //fills in every field in our row
                viewHolder.setWoord(model.getWoord());
                viewHolder.setVertaling(model.getVertaling());
                viewHolder.setStudentnaam(model.getStudentnaam());
                viewHolder.setLikes(model.getLikes());


                viewHolder.mLikebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (mLikeCount <= 5){
                            //get current likes + 1
                            mLikes = model.getLikes() + 1;
                            // store new likes
                            mDatabase.child(post_key).child("likes").setValue(mLikes);
                            // User has total amout onf 5 likes to use
                            mLikeCount = mLikeCount - 1;

                            Toast.makeText(Top200Activity.this, "Je kan nog " + mLikeCount + " stemmen uitbrengen.", Toast.LENGTH_SHORT).show();

                        }if(mLikeCount == 1){

                            Toast.makeText(Top200Activity.this, "Opgepast! Je kan nog maar " + mLikeCount + " stem uitbrengen.", Toast.LENGTH_SHORT).show();

                        }
                        if(mLikeCount <= 0){

                            Toast.makeText(Top200Activity.this, "Sorry, je kan maar 5 keer op een woord stemmen.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        };

        mWoordenList.setAdapter(firebaseRecyclerAdapter);

    }

    // custom viewholder for recyclerview
    public static class WoordViewHolder extends RecyclerView.ViewHolder{

        View mView;
        ImageButton mLikebtn, mSharebtn;

        public WoordViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mLikebtn = (ImageButton) mView.findViewById(R.id.btnLike);
            mSharebtn = (ImageButton) mView.findViewById(R.id.btnShare);
        }

        public void setWoord(String woord){
            TextView post_woord = (TextView) mView.findViewById(R.id.post_woord);
            post_woord.setText(woord);
        }

        public void setVertaling(String vertaling){
            TextView post_vertaling = (TextView) mView.findViewById(R.id.post_vertaling);
            post_vertaling.setText(vertaling);
        }

        public void setStudentnaam(String studentnaam){
            TextView post_studentnaam = (TextView) mView.findViewById(R.id.post_studentnaam);
            post_studentnaam.setText(studentnaam);
        }

        public void setLikes(int likes){
            TextView post_likes = (TextView) mView.findViewById(R.id.post_likes);
            post_likes.setText("Aantal stemmen: " + String.valueOf(likes));
        }

    }

    // inflate custom menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    // Menu click handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // when clicked on "+" add
        if(item.getItemId() == R.id.action_add){

            startActivity(new Intent(Top200Activity.this, PostActivity.class));

        }

        if(item.getItemId() == R.id.action_lijst){

            startActivity(new Intent(Top200Activity.this, MainActivity.class));

        }

        if(item.getItemId() == R.id.action_top200){

            startActivity(new Intent(Top200Activity.this, Top200Activity.class));

        }

        if(item.getItemId() == R.id.action_mijnwoorden){

            startActivity(new Intent(Top200Activity.this, MijnWoordenActivity.class));

        }

        // when click on logout
        if(item.getItemId() == R.id.action_logout){

            logout();

        }


        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
    }
}
