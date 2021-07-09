package syntax.org.il.gameproject;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class StartActivity extends AppCompatActivity {

    //Play Button
    private Button playBtn;
    private TextInputLayout textInputUsername;
    ImageView Title;
    SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        //share preferences
        textInputUsername = findViewById(R.id.textInputLayout);
        sp = getSharedPreferences("details",MODE_PRIVATE);

        //Play button
        playBtn = findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = textInputUsername.getEditText().getText().toString().trim();
                String input = getResources().getString(R.string.usernameToast) + textInputUsername.getEditText().getText().toString();
                //username
                if (username.isEmpty()) {
                    textInputUsername.setError(getResources().getString(R.string.Fieldcantbeempty));
                    Toast.makeText(StartActivity.this, input, Toast.LENGTH_SHORT).show();
                } else if (username.length() > 20) {
                    textInputUsername.setError(getResources().getString(R.string.Usernametolong));
                    Toast.makeText(StartActivity.this, input, Toast.LENGTH_SHORT).show();
                } else {
                    textInputUsername.setError(null);
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }
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


    @Override
    protected void onPause() {
        super.onPause();


        SharedPreferences.Editor editor =sp.edit();
        //
        editor.putString("user_name",textInputUsername.getEditText().getText().toString());
        editor.putInt("score",0);
        editor.commit();
    }
}
