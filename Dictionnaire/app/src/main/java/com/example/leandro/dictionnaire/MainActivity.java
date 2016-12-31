package com.example.leandro.dictionnaire;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
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

public class MainActivity extends AppCompatActivity {

    private RecyclerView mWoordenList;
    private DatabaseReference mDatabase;

    private int mLikes;
    private int mLikeCount = 5;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if user is logged in
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() == null){
                    // if user is NOT logged in redirect to...

                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // User cannot go back
                    startActivity(loginIntent);

                }


            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("woorden");

        mWoordenList = (RecyclerView) findViewById(R.id.woorden_list);
        mWoordenList.setHasFixedSize(true);
        mWoordenList.setLayoutManager(new LinearLayoutManager(this)); // vertical format

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        FirebaseRecyclerAdapter<Woord, WoordViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Woord, WoordViewHolder>(

                Woord.class,
                R.layout.woord_row,
                WoordViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(final WoordViewHolder viewHolder, final Woord model, int position) {

                final String post_key = getRef(position).getKey();

                //fills in every field in our row
                viewHolder.setWoord(model.getWoord());
                viewHolder.setVertaling(model.getVertaling());
                viewHolder.setStudentnaam(model.getStudentnaam());
                viewHolder.setLikes(model.getLikes());

                // LIKE BUTTON CLICK LISTENER
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

                            Toast.makeText(MainActivity.this, "Je kan nog " + mLikeCount + " stemmen uitbrengen.", Toast.LENGTH_SHORT).show();

                        }if(mLikeCount == 1){

                            Toast.makeText(MainActivity.this, "Opgepast! Je kan nog maar " + mLikeCount + " stem uitbrengen.", Toast.LENGTH_SHORT).show();

                        }
                        if(mLikeCount <= 0){

                            Toast.makeText(MainActivity.this, "Sorry, je kan maar 5 keer op een woord stemmen.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                // SHARE BUTTON CLICK LISTENER
                viewHolder.mSharebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Un mot trés cool sur Dictionnaire.");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Un mot trés cool sur Dictionnaire.");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, model.getWoord()+ ": " + model.getVertaling());
                        startActivity(Intent.createChooser(shareIntent, "Deel je woord via: "));

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

            startActivity(new Intent(MainActivity.this, PostActivity.class));

        }

        if(item.getItemId() == R.id.action_lijst){

            startActivity(new Intent(MainActivity.this, MainActivity.class));

        }

        if(item.getItemId() == R.id.action_top200){

            startActivity(new Intent(MainActivity.this, Top200Activity.class));

        }

        if(item.getItemId() == R.id.action_mijnwoorden){

            startActivity(new Intent(MainActivity.this, MijnWoordenActivity.class));

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
