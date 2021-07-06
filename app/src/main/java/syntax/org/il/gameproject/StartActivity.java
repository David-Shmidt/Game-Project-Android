package syntax.org.il.gameproject;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;



public class StartActivity extends AppCompatActivity {

    //Play Button
    private Button button;
    ImageView Title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Play button
        button = findViewById(R.id.play_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        //Score button + intent
        Button scoreBtn =findViewById(R.id.score_Btn);
        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2nd = new Intent(StartActivity.this,SecondActivity.class);
                startActivity(intent2nd);
            }
        });

        //animation Title
        Title = findViewById(R.id.imageView5);
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                Title,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaley", 1.2f)
        );
        objectAnimator.setDuration(650);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.start(); //end of write for animation title
    }

    //Play button - Intent
    public void openMainActivity() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
//try to fix