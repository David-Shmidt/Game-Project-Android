package syntax.org.il.gameproject;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import com.google.android.gms.common.util.Strings;
import com.google.android.material.textfield.TextInputLayout;


public class StartActivity extends AppCompatActivity {


    private Button playBtn;
    private Button difficultBtn;
    private TextInputLayout textInputUsername;
    private MediaPlayer mediaPlayer;
    ImageView Title;
    SharedPreferences sp;
    AlertDialog diffDialog;
    int diff = 0;

    String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //backround music
        mediaPlayer = MediaPlayer.create(StartActivity.this,R.raw.backroundsound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


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
                }
                else if(diff==0){
                    String difficultyToast = getResources().getString(R.string.choosedifficulty);
                    Toast.makeText(StartActivity.this,difficultyToast , Toast.LENGTH_LONG).show();
                }
                else {
                    textInputUsername.setError(null);
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        difficultBtn = findViewById(R.id.difficult);
        difficultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDiff(R.layout.difficulty_dialog);


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
    //run permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                String allowToast = getResources().getString(R.string.allow);
                Toast.makeText(this,allowToast, Toast.LENGTH_SHORT).show();
            }else{
                String deniedToast = getResources().getString(R.string.DENIED);
                Toast.makeText(this,deniedToast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manue,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.lobbyMenu:
                Intent intent = new Intent(StartActivity.this, StartActivity.class);
                startActivity(intent);
                return true;
            case R.id.MediaPermission:
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    requestPermissions(permissions,1);
                }
                return true;
            case R.id.Musiconoff:
               if(mediaPlayer.isPlaying()){
                   mediaPlayer.pause();
                   Toast.makeText(this,"Music off", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(this, "Music on", Toast.LENGTH_SHORT).show();
                   mediaPlayer.start();
               }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();


        SharedPreferences.Editor editor =sp.edit();
        //
        editor.putString("user_name",textInputUsername.getEditText().getText().toString());
        editor.putInt("score",0);
        editor.putInt("diff" , diff);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
    //back press on phone exit!!!
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void chooseDiff(int difficulty_dialog){
        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        View diffView = getLayoutInflater().inflate(R.layout.difficulty_dialog , null);
        builder.setView(diffView).setCancelable(false);
        diffDialog = builder.create();
        diffDialog.show();

        Button easyBtn = diffView.findViewById(R.id.easy_btn);
        Button mediumBtn = diffView.findViewById(R.id.medium_btn);
        Button hardBtn = diffView.findViewById(R.id.hard_btn);

        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diff = 1;
                diffDialog.dismiss();
                String easy = getResources().getString(R.string.easy_btn);
                Toast.makeText(StartActivity.this,easy, Toast.LENGTH_SHORT).show();
            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diff = 2;
                diffDialog.dismiss();
                String medium = getResources().getString(R.string.Medium_btn);
                Toast.makeText(StartActivity.this,medium, Toast.LENGTH_SHORT).show();
            }
        });

        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diff = 3;
                diffDialog.dismiss();
                String hard = getResources().getString(R.string.hard_btn);
                Toast.makeText(StartActivity.this,hard, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
