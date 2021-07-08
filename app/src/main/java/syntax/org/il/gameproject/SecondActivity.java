package syntax.org.il.gameproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Score> scores = new ArrayList<>();
        scores.add(new Score("Test",5));
        scores.add(new Score("Test",6));
        scores.add(new Score("Test",9));
        scores.add(new Score("Test",1));
        scores.add(new Score("Test",11));
        scores.add(new Score("Test",21));
        scores.add(new Score("Test",335));

        Collections.sort(scores);
        ScoreAdapter scoreAdapter = new ScoreAdapter(scores);
        recyclerView.setAdapter(scoreAdapter);

    }
}