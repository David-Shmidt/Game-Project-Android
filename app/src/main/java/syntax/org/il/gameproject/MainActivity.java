package syntax.org.il.gameproject;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelStoreOwner;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.canvas.CanvasCompat;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    ImageView platform , ball;
    View leftBorder,rightBorder, topBorder;

    float newX ,speed = 1;
    float ballX , ballY,ballZ;

    SensorManager sensorManger;
    private Sensor sensorAccel , sensorGyro;
    float left;
    float right;
    float ballMovementX = 10 , ballMovementY = 10;
    Rect rightBorderRect = new Rect() , leftBorderRect = new Rect() , topBorderRect = new Rect();
    Rect ballRect = new Rect() , platformRectR = new Rect(), platformRectL = new Rect();
    GameView gameView;
    //Rect blockRect = new Rect();
    //Rect block = new Rect();
    Rect[] bricks;
    Rect brick = new Rect();
    int indexOfBrick = 0;

    //temporary ball rectF
    RectF ballRectF = new RectF();








    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        platform = findViewById(R.id.platform_iv);
        leftBorder = findViewById(R.id.left_border_tv);
        rightBorder = findViewById(R.id.right_border_tv);
        topBorder = findViewById(R.id.top_border_tv);
        ball = findViewById(R.id.ball_iv);

       // GameView gameView;
        gameView = findViewById(R.id.game_view);
        //bricks = new Rect[100];
        bricks = gameView.createMatrix(5, 5);
        //block =  gameView.sendParams( 600,600 , 800 , 700);







        //blockRect.set(block.left,block.top,block.right,block.bottom);
        leftBorderRect.set(leftBorder.getLeft() ,leftBorder.getTop() , leftBorder.getRight() , leftBorder.getBottom());
        rightBorderRect.set(rightBorder.getLeft() , rightBorder.getTop() , rightBorder.getRight() , rightBorder.getBottom());
        topBorderRect.set(topBorder.getLeft(), topBorder.getTop(),topBorder.getRight(),topBorder.getBottom());

        platformRectR.set(platform.getLeft()/2 , platform.getTop() , platform.getRight(),platform.getBottom());
        ballRect.set(ball.getLeft() , ball.getTop() , ball.getRight() , ball.getBottom());






        sensorManger = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccel = sensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sensorGyro = sensorManger.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {




        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {


            platformRectR.set(platform.getWidth()/2 , platform.getTop() , platform.getRight(),platform.getBottom());
            platformRectL.set(platform.getLeft(), platform.getTop(),platform.getWidth()/2,platform.getBottom());
            ballRect.set(ball.getLeft() , ball.getTop() , ball.getRight() , ball.getBottom());
            ballRectF.set(ball.getLeft() , ball.getTop() , ball.getRight() , ball.getBottom());


            newX = 5 * event.values[0];

            ball.getHitRect(ballRect);
            platform.getHitRect(platformRectR);
            platform.getHitRect(platformRectL);
            leftBorder.getHitRect( leftBorderRect);
            rightBorder.getHitRect(rightBorderRect);
            topBorder.getHitRect(topBorderRect);
            //  block.getHitRect(blockRect);


            ball.setX(ball.getX() + ballMovementX);
            ball.setY(ball.getY() + ballMovementY);



            //ball movement
            if(Rect.intersects(ballRect , rightBorderRect)){

                ballMovementX = -ballMovementX;
                ball.setX(ball.getX() - 50);
                //ball.setY(ball.getY() - 50);

            }

            else if( Rect.intersects(ballRect,leftBorderRect)){
                ballMovementX = -ballMovementX;
                ball.setX(ball.getX() + 50);
            }
            else if(Rect.intersects(platformRectR,ballRect)){
                ballMovementY = -1 * ballMovementY;
                ball.setY(ball.getY() - 50);
            }
            else if(Rect.intersects(platformRectL,ballRect)){
                ballMovementY = -1 * ballMovementY;
                ball.setY(ball.getY() - 50);
            }

            else if(Rect.intersects(ballRect, topBorderRect)){
                ballMovementY = -1 * ballMovementY;
                ball.setY(ball.getY() + 50);
            }

            else {
                for(Rect brick:bricks){
                    indexOfBrick++;
                    if (Rect.intersects(ballRect, brick)) {
                        if(ballRect.top > brick.top) {
                            ballMovementY = -ballMovementY;
                            ball.setY(ball.getY() - 20);
                        }
                       // else if(ballRect.)
                        gameView.delete(brick ,indexOfBrick);
                        brick.set(0, 0, 0, 0);
                        indexOfBrick = 0;
                    }
                }

            }

          /*  else if(Rect.intersects(ballRect , block)){
                gameView.delete(block);
                block.set(0,0,0,0);
                ballMovementY = -1 * ballMovementY;
                if(ballRect.top > block.top){
                    ball.setY(ball.getY() + 20);
                }
                else if(ballRect.top < block.top){
                    ball.setY(ball.getY() - 20);
                }
                else if(ballRect.left < block.left){
                    ball.setX(ball.getX() + 20);
                }
                //ball.setY(ball.getY() - 20);
            }*/

            //platform movement
            float platformX = platform.getX();


            if( (Rect.intersects(platformRectL,leftBorderRect)) && newX > 0)
                platform.setX( platformX);
            else if(Rect.intersects(platformRectR,rightBorderRect) && newX < 0){
                platform.setX( platformX);
            }

            else
                platform.setX( platformX - newX);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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

}