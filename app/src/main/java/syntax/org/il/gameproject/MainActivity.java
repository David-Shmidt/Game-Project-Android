package syntax.org.il.gameproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //ImageView platform , ball;
    //View leftBorder,rightBorder, topBorder,bottomBorder;

    //float newX ,speed = 1;
    //float ballX , ballY,ballZ;

    /*float scale = getResources().getDisplayMetrics().density;*/

    SensorManager sensorManger;
    private Sensor sensorAccel;
    float ballMovementX, ballMovementY;
    int platMovementX;
    int scale;
    Rect rightBorderRect = new Rect() , leftBorderRect = new Rect() , topBorderRect = new Rect(),bottomBorderRect = new Rect();
    Rect ballRect = new Rect();
    Rect platformRectR  =new Rect() , platformRectL = new Rect();
    GameView gameView;
    Brick[] bricks;
    Rect brick = new Rect();
    int indexOfBrick = 0;
    Ball gameBall;
    Brick platform;

    //Delete Later

    TextView ball_x , ball_y;








    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        scale  = (int)getResources().getDisplayMetrics().density;
        ballMovementX =5*scale;
        ballMovementY = 5*scale;





        gameView = (GameView) findViewById(R.id.game_view);
        bricks = gameView.createMatrix(5, 5);
        gameBall = gameView.createCircle(50*scale,500*scale,4*scale);
        platform = gameView.createPlatform(200*scale,360*scale,270*scale,370*scale);
        //borders = gameView.getBorder(borders);
        //brickBoxes = new Rect[bricks.length];





        sensorManger = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccel = sensorManger.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sensorGyro = sensorManger.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        //Platform Movement

        platMovementX = (int) (event.values[0]*scale);
        if(platform.getLeft() > 0 && platMovementX > 0) {
            gameView.movePlatform(platform, platMovementX);
        }

        if(platform.getRight() < 350 *scale && platMovementX  < 0){
            gameView.movePlatform(platform, platMovementX);
        }


        gameView.moveCircle(gameBall, ballMovementX, ballMovementY);

        //Ball Movement
        //Hits left side
        if (gameBall.getCenterX() < 0) {
            ballMovementX = -ballMovementX;
        }
        //Hits top
        if (gameBall.getCenterY() < 0) {
            ballMovementY = -ballMovementY;
        }
        //Hits right side
        if (gameBall.getCenterX() > 350 * scale) {
            ballMovementX = -ballMovementX;
        }

        //Hits Bottom
        if (gameBall.getCenterY() > 560 * scale) {
            ballMovementY = -ballMovementY;
        }

        if(gameBall.hitsBrick(platform)){
            ballMovementX = -ballMovementX;
            ballMovementY = -ballMovementY;
        }

        for(Brick brick:bricks){
            if (gameBall.hitsBrick(brick)){
                ballMovementX = -ballMovementX;
                ballMovementY = -ballMovementY;
                brick.set(0,0,0,0,0);
                bricks = gameView.deleteBrick(bricks , brick);
                //index = 0;
            }
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





//...........................................................................................//

//Old on Create Code
/*platform = findViewById(R.id.platform_iv);
        leftBorder = findViewById(R.id.left_border_tv);
        rightBorder = findViewById(R.id.right_border_tv);
        topBorder = findViewById(R.id.top_border_tv);
        bottomBorder = findViewById(R.id.bottom_brder_tv);
        ball = findViewById(R.id.ball_iv);


       // GameView gameView;
        gameView = findViewById(R.id.game_view);
        //bricks = new Rect[100];
        bricks = gameView.createMatrix(5, 5);






        leftBorderRect.set(leftBorder.getLeft() ,leftBorder.getTop() , leftBorder.getRight() , leftBorder.getBottom());
        rightBorderRect.set(rightBorder.getLeft() , rightBorder.getTop() , rightBorder.getRight() , rightBorder.getBottom());
        topBorderRect.set(topBorder.getLeft(), topBorder.getTop(),topBorder.getRight(),topBorder.getBottom());
        bottomBorderRect.set(bottomBorder.getLeft(),bottomBorder.getTop(),bottomBorder.getRight(),bottomBorder.getBottom());
        platformRectL.set(platform.getLeft() , platform.getTop(),platform.getWidth()/2,platform.getBottom());
        platformRectR.set(platform.getWidth()/2,platform.getTop(),platform.getRight(),platform.getBottom());

        ballRect.set(ball.getLeft() , ball.getTop() , ball.getRight() , ball.getBottom());*/

//..................................................................................................................//


//Old Sensor Change Code
/*{




            ballRect.set(ball.getLeft() , ball.getTop() , ball.getRight() , ball.getBottom());


            newX = 5 * event.values[0];

            ball.getHitRect(ballRect);
            platform.getHitRect(platformRectR);
            platform.getHitRect(platformRectL);
            leftBorder.getHitRect( leftBorderRect);
            rightBorder.getHitRect(rightBorderRect);
            topBorder.getHitRect(topBorderRect);
            bottomBorder.getHitRect(bottomBorderRect);



            ball.setX(ball.getX() + ballMovementX);
            ball.setY(ball.getY() + ballMovementY);



            //ball movement
            if(Rect.intersects(ballRect , rightBorderRect)){

                //ball.setX(ball.getX());
                ballMovementX = -ballMovementX;
                ball.setX(ball.getX() - 4*ballMovementX);
                //ball.setY(ball.getY() - 50);

            }

            if( Rect.intersects(ballRect,leftBorderRect)){
                ballMovementX = -ballMovementX;
                ball.setX(ball.getX() + 4*ballMovementX);
            }


            if(Rect.intersects(platformRectR,ballRect)){
                ballMovementY = -1 * ballMovementY;
                ball.setY(ball.getY() - 50);
                if(ballMovementX < 0) {
                    ballMovementX = -ballMovementX;
                    //ball.setX(ball.getX() + 50);
                }

            }
            if(Rect.intersects(platformRectL,ballRect)){
                ballMovementY = -1 * ballMovementY;
                ball.setY(ball.getY() - 50);
                if(ballMovementX > 0) {
                    ballMovementX = -ballMovementX;
                    //ball.setX(ball.getX() - 50);
                }

            }

            if(Rect.intersects(ballRect, topBorderRect)){
                ballMovementY = -1 * ballMovementY;
                ball.setY(ball.getY() + 50);
            }

            if(Rect.intersects(ballRect,bottomBorderRect)){
                *//*ballMovementY = -1 * ballMovementY;
                ball.setY(ball.getY() - 50);*//*
                gameView.loseLife();
            }

            else {
                for(Rect brick:bricks){
                    indexOfBrick++;
                    if (Rect.intersects(ballRect, brick)) {
                        //If ball is above brick
                        if(ballRect.exactCenterY() > brick.exactCenterY()) {
                            ballMovementY = -ballMovementY;
                            ball.setY(ball.getY() + ballMovementY);
                            //ball.setY(ball.getY());

                        }
                        //If ball is below brick
                        if(ballRect.exactCenterY() < brick.exactCenterY()){
                            ballMovementY = -ballMovementY;
                            ball.setY(ball.getY() + ballMovementY);
                        }

                        //If ball hits brick from the right
                        if(ball.getX() > brick.exactCenterX()){
                            ballMovementX = -ballMovementX;
                            ball.setX(ball.getX() + ballMovementX);
                        }

                        //If ball hits brick from the left
                        if(ballRect.exactCenterX() < brick.exactCenterX()){
                            ballMovementX = -ballMovementX;
                            ball.setX(ball.getX() + ballMovementX);
                        }
                        gameView.delete(brick ,indexOfBrick);
                        brick.set(0, 0, 0, 0);
                        indexOfBrick = 0;
                    }
                }

            }


            //platform movement
            float platformX = platform.getX();


            if( (Rect.intersects(platformRectL,leftBorderRect)) && newX > 0)
                platform.setX( platformX);
            else if(Rect.intersects(platformRectR,rightBorderRect) && newX < 0){
                platform.setX( platformX);
            }

            else
                platform.setX( platformX - newX);
        }*/

//...............................................................................................//