package syntax.org.il.gameproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    final String TABLE_NAME = "Score_table";

    final String CREATE_TABLE_CMD ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,score INTEGER);";

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        database = openOrCreateDatabase("database.sql",MODE_PRIVATE,null);
        database.execSQL(CREATE_TABLE_CMD);
        int score = getIntent().getIntExtra("score",0);
        //int score = 0;
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Score> scores = new ArrayList<>();
        ScoreAdapter scoreAdapter = new ScoreAdapter(scores);
        recyclerView.setAdapter(scoreAdapter);
        SharedPreferences sp = getSharedPreferences("details",MODE_PRIVATE);
        if (score > 0) {
            String name = sp.getString("user_name", null);

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("score", score);
            database.insert(TABLE_NAME, null, contentValues);
            scoreAdapter.notifyDataSetChanged();
        }

        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
        int NameIndex = cursor.getColumnIndex("name");
        int ScoreIndex = cursor.getColumnIndex("score");
        while (cursor.moveToNext())
        {
            scores.add(new Score(cursor.getString(NameIndex),cursor.getInt(ScoreIndex)));
        }
        scoreAdapter.notifyDataSetChanged();
        Collections.sort(scores);


        //scores.add(new Score("David",5));
        //scores.add(new Score("Koby",6));
        //scores.add(new Score("Amit",9));
        //scores.add(new Score("Test",1));
        //scores.add(new Score("Moshe",11));
        //scores.add(new Score("Test123",21));
        //scores.add(new Score("Test",335));


    }

}