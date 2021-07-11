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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    //creating a table +data base for sql
    final String TABLE_NAME = "Score_table";
    final String CREATE_TABLE_CMD ="CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,score INTEGER);";
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        database = openOrCreateDatabase("database.sql",MODE_PRIVATE,null);
        database.execSQL(CREATE_TABLE_CMD);
        //score from wher you finished the game
        int score = getIntent().getIntExtra("score",0);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Score> scores = new ArrayList<>();
        ScoreAdapter scoreAdapter = new ScoreAdapter(scores);
        recyclerView.setAdapter(scoreAdapter);
        SharedPreferences sp = getSharedPreferences("details",MODE_PRIVATE);
        //add your score only if click finish you have score>0
        if (score > 0) {
            String name = sp.getString("user_name", null);

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name);
            contentValues.put("score", score);
            database.insert(TABLE_NAME, null, contentValues);
            scoreAdapter.notifyDataSetChanged();
        }
        //show total scores
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
        int NameIndex = cursor.getColumnIndex("name");
        int ScoreIndex = cursor.getColumnIndex("score");
        while (cursor.moveToNext())
        {
            scores.add(new Score(cursor.getString(NameIndex),cursor.getInt(ScoreIndex)));
        }
        scoreAdapter.notifyDataSetChanged();
        Collections.sort(scores);

        //back button
        Button backBtn =findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2nd = new Intent(SecondActivity.this,StartActivity.class);
                startActivity(intent2nd);
            }
        });
        //just in case score = 0
        score = 0;

    }

    //back press on phone go to start activity!!!
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manue,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.swichSound:
                Toast.makeText(this, "sound selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.lobbyMenu:
                Intent intent = new Intent(SecondActivity.this, StartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}