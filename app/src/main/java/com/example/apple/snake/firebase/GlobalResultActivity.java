package com.example.apple.snake.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.apple.snake.R;
import com.example.apple.snake.ResultAdapter;
import com.example.apple.snake.Score;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GlobalResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_result);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<Score> arrayOfUsers = new ArrayList<Score>();
        final ResultAdapter adapter = new ResultAdapter(this, arrayOfUsers);
//        listScore = Score.listAll(Score.class);

        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref2;
        ref2 = ref1.child("users");

        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Score> listScore = new ArrayList<>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    Score scoreClass = new Score(Integer.parseInt(dsp.child("score").getValue().toString()), dsp.child("name").getValue().toString());
                    Log.d("score", scoreClass.getName());
                    listScore.add(scoreClass);
                }


                Collections.sort(listScore, new Comparator<Score>() {
                    public int compare(Score c1, Score c2) {
                        if (c1.getScore() > c2.getScore()) return -1;
                        if (c1.getScore() < c2.getScore()) return 1;
                        return 0;
                    }
                });

                adapter.addAll(listScore);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ListView listView = (ListView) findViewById(R.id.lvGlobalScore);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
