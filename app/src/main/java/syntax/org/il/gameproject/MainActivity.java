package syntax.org.il.gameproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.Menu;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {


    //Defining all the Variables
    SensorManager sensorManger;
    private Sensor sensorAccel;
    float ballMovementX, ballMovementY, speedX, speedY;
    int platMovementX;
    int scale;
    GameView gameView;
    Brick[] bricks;
    Ball gameBall;
    Brick platform;
    float angleX;
    float angleY;
    float angle;
    boolean up = true;
    boolean right = true;
    boolean startGame = false;
    int screenX, screenY;
    Display display;
    Point size = new Point();
    int bricksDestroyed = 0;
    Handler handler = new Handler();
    /*View dialogView;
    AlertDialog.Builder builder;*/
    AlertDialog lvlComplete;
    AlertDialog gameOver;
    int lives = 3;
    Levels levels;
    int level = 1;
    ImageButton pauseBtn;
    int score = 0;
    //int diff =2 ;
    boolean paused = false;
    int diff;
    private SoundPool soundPool;
    private int sound1,sound2,sound3,sound4,sound5,sound6;





    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("details" , MODE_PRIVATE);
        diff = sp.getInt("diff" , 1);


        //sound effect in game
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }

        sound1 = soundPool.load(this, R.raw.brickhit, 1);
        sound2 = soundPool.load(this, R.raw.mphitplatform, 1);
        sound3 = soundPool.load(this, R.raw.mphitsidewall, 1);
        sound4 = soundPool.load(this, R.raw.mphitbottom, 1);
        sound5 = soundPool.load(this, R.raw.mpgameover, 1);
        sound6 = soundPool.load(this, R.raw.mplevelcomplete, 1);


        scale = (int) getResources().getDisplayMetrics().density;
        angle = (float) Math.PI;
        angleX = (float) (Math.cos(angle / 18));
        angleY = (float) (Math.sin(angle / 18));


        speedX = 9* scale;
        speedY = 9 *scale;

        if(diff>1){
            speedX = 11*scale;
            speedY = 11*scale;
        }


        ballMovementX = 0;
        ballMovementY = speedY;


        gameView = findViewById(R.id.game_view);
        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;

        levels = new Levels(scale,screenX,screenY);
        setLevel(0);

        platform = gameView.createPlatform((screenX / 3), (screenY - 160 * scale), (screenX / 2), (screenY - 150 * scale));


        if(diff==3) {
            platform = gameView.createPlatform((screenX / 2), (screenY - 160 * scale), (screenX / 2 + 50 * scale), (screenY - 150 * scale));
        }

        gameBall = gameView.createCircle(platform.getLeft() + (platform.getRight() - platform.getLeft()) / 2, platform.getTop() - 20 * scale, 4 * scale);
        gameView.setBorders(screenX, screenY);


        sensorManger = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccel = sensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        pauseBtn = findViewById(R.id.pause_btn);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!paused) {
                    pauseBtn.setImageResource(R.drawable.play_v);
                    paused = true;
                } else if (paused) {
                    pauseBtn.setImageResource(R.drawable.pause_v);
                    paused = false;
                }
            }
        });


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
            case R.id.lobbyMenu:
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if(!paused){
            //Platform Movement

            platMovementX = 5 * (int) (event.values[0] * scale);
            if (platform.getLeft() > 0 && platMovementX > 0) {
                gameView.movePlatform(platform, platMovementX);
                if (!startGame) {
                    gameView.moveCircle(gameBall, -platMovementX, 0);
                }
            }

            if (platform.getRight() < screenX && platMovementX < 0) {
                gameView.movePlatform(platform, platMovementX);
                if (!startGame) {
                    gameView.moveCircle(gameBall, -platMovementX, 0);
                }
            }


            if (startGame) {
                gameView.moveCircle(gameBall, ballMovementX, ballMovementY);

                //Ball Movement
                //Hits left side
                if (gameBall.getCenterX() < 0) {
                    soundPool.play(sound3,1,1,0,0,1);
                    right = true;
                    if (up) {
                        setAngles(1);
                        ballMovementY = -1 * speedY * angleY;
                    } else if (!up) {
                        setAngles(3);
                        ballMovementY = -1 * speedY * angleY;
                    }
                    ballMovementX = speedX * angleX;
                }
                //Hits top
                if (gameBall.getCenterY() < 0) {
                    soundPool.play(sound3,1,1,0,0,1);
                    up = false;
                    if (right) {
                        setAngles(3);
                    } else if (!right) {
                        setAngles(4);
                    }
                    ballMovementX = speedX * angleX;
                    ballMovementY = -1 * speedY * angleY;
                }
                //Hits right side
                if (gameBall.getCenterX() > screenX) {
                    soundPool.play(sound3,1,1,0,0,1);
                    right = false;
                    if (up) {
                        setAngles(2);
                        ballMovementY = -1 * speedY * angleY;
                    } else if (!up) {
                        setAngles(4);
                        ballMovementY = -1 * speedY * angleY;
                    }
                    ballMovementX = speedX * angleX;
                    if (ballMovementX > 0) {
                        ballMovementX = -ballMovementX;
                    }
                }

                //Hits Bottom and Loses life
                if (gameBall.getCenterY() > screenY) {
                    soundPool.play(sound4,1,1,0,0,1);
                    lives--;
                    gameView.loseLife();
                    gameBall = gameView.createCircle(platform.getLeft() + (platform.getRight() - platform.getLeft()) / 2, platform.getTop() - 20 * scale, 4 * scale);
                    startGame = false;
                    ballMovementX = 0;
                    ballMovementY = -speedY;
                    if(lives == 0){
                        soundPool.play(sound5,1,1,0,0,1);
                        gameOver();
                    }
                }

                if (gameBall.hitsPlatform(platform) == 1) {
                    soundPool.play(sound2,1,1,0,0,1);
                    up = true;
                    if (right) {
                        setAngles(1);

                    } else if (!right) {
                        setAngles(2);
                    }
                    ballMovementX = speedX * angleX;
                    ballMovementY = -1 * speedY * angleY;
                }

                //Checks if Ball intecects Bricks
                for (Brick brick : bricks) {
                    if (gameBall.hitsBrick(brick)) {
                        soundPool.play(sound1,1,1,0,0,1);
                        scoreControl(100);
                        bricksDestroyed++;
                        up = !up;
                        right = !right;
                        ballMovementX = -ballMovementX;
                        ballMovementY = -ballMovementY;
                        brick.set(0, 0, 0, 0, 0);
                        //brick.setRectF();
                        bricks = gameView.deleteBrick(bricks, brick);
                        break;
                        //index = 0;
                    }
                }
                //If all Bricks are Destroyed pop Alert Dialog
                //Also pause the game
                if (bricks.length == bricksDestroyed) {
                    soundPool.play(sound6, 1, 1, 0, 0, 1);
                    paused = true;
                    levelUp();
                }


            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //The ball wont move until the screen is pressed
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            startGame = true;
        }

        return super.onTouchEvent(event);



    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManger.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(sensorAccel != null){
            sensorManger.registerListener(this , sensorAccel , SensorManager.SENSOR_DELAY_GAME);
        }
    }

    //Setting different angles for different directions
    float upRightAngle(){
        int n =  (int)(Math.random() *3) +1;
        switch (n){
            case 1:
                return (float)Math.PI/6;
            case 2:
                return(float)Math.PI/4;
            case 3:
                return (float)Math.PI/3;
        }
        return 0;
    }

    float upLeftAngle(){
        int n = (int)(Math.random() * 3) +1;
        switch (n){
            case 1:
                return 2 * (float)Math.PI/3;
            case 2:
                return 3* (float)Math.PI/4;
            case 3:
                return 5 * (float)Math.PI/6;
        }
        return 0;
    }

    float downRightAngle(){
        int n = (int)(Math.random()*3) +1;
        switch (n){
            case 1:
                return 5 * (float)Math.PI/3;
            case 2:
                return 7* (float)Math.PI/4;
            case 3:
                return 11 * (float)Math.PI/6;
        }
        return 0;
    }

    float downLeftAngle(){
        int n = (int)(Math.random()*3) +1;
        switch (n){
            case 1:
                return 4 * (float)Math.PI/3;
            case 2:
                return 5* (float)Math.PI/4;
            case 3:
                return 7 * (float)Math.PI/6;
        }
        return 0;
    }



    void setAngles(int type){
        switch (type){
            case 1:
                angle = upRightAngle();
                break;
            case 2:
                angle = upLeftAngle();
                break;
            case 3:
                angle = downRightAngle();
                break;
            case 4:
                angle = downLeftAngle();
                break;
        }
        angleX = (float)(Math.cos(angle));
        angleY = (float)(Math.sin(angle));
    }

    //Finishing Level Successfully and poping Dialog
    void levelUp(){

        handler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.Theme_AppCompat_Dialog_Alert);
                View dialogView = getLayoutInflater().inflate(R.layout.level_complete,null);
                Button nextLvlBtn = dialogView.findViewById(R.id.next_level);
                Button restartLvlBtn = dialogView.findViewById(R.id.restart_level);
                builder.setView(dialogView).setCancelable(false);
                lvlComplete = builder.create();
                lvlComplete.show();


                nextLvlBtn.setOnClickListener(new AlertDialogsOnClickListener());
                restartLvlBtn.setOnClickListener(new AlertDialogsOnClickListener());
            }
        });

    }

    //Dialog for when player loses the game
    void gameOver(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.game_over,null);
                TextView scoreText = dialogView.findViewById(R.id.your_score);
                TextView scoreInt = dialogView.findViewById(R.id.scoreInt);
                scoreInt.setText(""+score);
                Button finishBtn = dialogView.findViewById(R.id.finish_name_btn);
                Button playagainBtn = dialogView.findViewById(R.id.play_again);
                builder.setView(dialogView).setCancelable(false);
                gameOver = builder.create();
                gameOver.show();

                finishBtn.setOnClickListener(new AlertDialogsOnClickListener());
                playagainBtn.setOnClickListener(new AlertDialogsOnClickListener());
            }
        });

    }


    //Click Listener Class for Dialogs Buttons
    public class AlertDialogsOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            gameBall = gameView.createCircle(platform.getLeft() + (platform.getRight() - platform.getLeft()) / 2, platform.getTop() - 20 * scale, 4 * scale);
            paused = false;
            startGame = false;
            bricksDestroyed = 0;

            switch(v.getId()){
                case R.id.next_level:
                    setLevel(1);
                    lvlComplete.dismiss();
                    break;
                case R.id.restart_level:
                    setLevel(0);
                    lvlComplete.dismiss();
                    break;
                case R.id.finish_name_btn:
                    Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                    intent.putExtra("score",score);
                    startActivity(intent);
                    gameOver.dismiss();
                    break;
                case R.id.play_again:
                    level = 0;
                    lives  = 3;
                    gameView.setScore(0);
                    gameView.setLives();
                    setLevel(1);
                    gameOver.dismiss();
                    break;
            }
        }
    }

    void setLevel(int n){
        level += n;
        switch(level){
            case 1:
                levels.setLevel_1();
                bricks = levels.getLevel_1();
                gameView.createMatrix(bricks);
                break;
            case 2:
                levels.setLevel_2();
                bricks = levels.getLevel_2();
                gameView.createMatrix(bricks);
                break;
            case 3:
                levels.setLevel_3();
                bricks = levels.getLevel_3();
                gameView.createMatrix(bricks);
                break;
            case 4:
                levels.setLevel_4();
                bricks = levels.getLevel_4();
                gameView.createMatrix(bricks);
                break;
            case 5:
                levels.setLevel_5();
                bricks = levels.getLevel_5();
                gameView.createMatrix(bricks);
                break;
            case 6:
                levels.setLevel_6();
                bricks = levels.getLevel_6();
                gameView.createMatrix(bricks);
                break;
            case 7:
                levels.setLevel_endless();
                bricks = levels.getLevel_7();
                gameView.createMatrix(bricks);
                break;
        }
    }

    void scoreControl(int n){
        n *= lives;
        score += n;
        gameView.setScore(score);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
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





