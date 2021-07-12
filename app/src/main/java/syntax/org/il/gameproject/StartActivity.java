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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.Strings;
import com.google.android.material.textfield.TextInputLayout;


public class StartActivity extends AppCompatActivity {

    //Play Button
    private Button playBtn;
    private TextInputLayout textInputUsername;
    private MediaPlayer mediaPlayer;
    ImageView Title;
    SharedPreferences sp;

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
    //run permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==80){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Download Code", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Download Cancel", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manue,menu);

        MenuItem itemSwitch = menu.findItem(R.id.swichSound);
        itemSwitch.setActionView(R.layout.switch_layout);
        final Switch sw = (Switch)menu.findItem(R.id.swichSound).getActionView().findViewById(R.id.switchAB);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mediaPlayer.pause();
                }else{
                    mediaPlayer.start();
                }
            }
        });
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
                    requestPermissions(permissions,80);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();


        SharedPreferences.Editor editor =sp.edit();
        //
        editor.putString("user_name",textInputUsername.getEditText().getText().toString());
        editor.putInt("score",0);
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
}
