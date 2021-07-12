package syntax.org.il.gameproject;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;


public class GameView extends View {

    Paint paint;
    Paint textPaint;
    Paint transperent = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint heartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint heartPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint heartPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint bluePaint;
    Rect deletRect = new Rect();
    boolean toDelete = false;
    int space = 0;
    int scale = (int)getResources().getDisplayMetrics().density;
    int lostLives = 0;
    Brick[] bricks;
    int bricksCount = 0;
    Rect borderRight = new Rect() , borderLeft = new Rect() , borderTop = new Rect() , borderBottom = new Rect();
    Brick deleteBr = new Brick(0,0,0,0 , 0);
    Ball ball;
    Brick platform;
    //Levels levels;
    int level = 0;
    int score;
    Display display;
    String scoretxt = getResources().getString(R.string.score_txt);










    int Row, Colum;
    Rect[][] matrix;
    //Rect tempRect = new Rect();
    int widthChange = 0 , heightCahnge = 0;
    Path heart = new Path();
    Path heart2 = new Path();
    Path heart3 = new Path();
    Path cracked = new Path();



    public GameView(Context context, AttributeSet attrs) {


        super(context, attrs);



        //Paints
        heartPaint.setColor(Color.RED);
        heartPaint2.setColor(Color.RED);
        heartPaint3.setColor(Color.RED);
        heartPaint.setStyle(Paint.Style.FILL);
        heartPaint2.setStyle(Paint.Style.FILL);
        heartPaint3.setStyle(Paint.Style.FILL);
        stroke.setColor(Color.BLACK);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(2*scale);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.teal_bright));
        paint.setStyle(Paint.Style.FILL);
        transperent.setColor(Color.TRANSPARENT);
        transperent.setStyle(Paint.Style.FILL);
        bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setStyle(Paint.Style.FILL);
        bluePaint.setColor(Color.BLUE);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(30*scale);
        textPaint.setColor(Color.WHITE);


        //Creating Levels instance
        //levels = new Levels(scale);

        //Life
        heart.moveTo(25f* scale, 5f * scale);
        heart.lineTo(35f * scale, 10f * scale);
        heart.lineTo( 45f * scale, 5f * scale);
        heart.lineTo( 55f* scale, 10f * scale);
        heart.lineTo( 35f * scale, 25f * scale);
        heart.lineTo( 15f * scale, 10f * scale);
        heart.lineTo( 25f * scale, 5f * scale);
        heart.close();






        heart2.moveTo(75f* scale,5f* scale);
        heart2.lineTo(85f* scale,10f* scale);
        heart2.lineTo(95f* scale,5f* scale);
        heart2.lineTo(105f* scale,10f* scale);
        heart2.lineTo(85f* scale,25f* scale);
        heart2.lineTo(65f* scale,10f* scale);
        heart2.lineTo(75f* scale,5f* scale);
        heart2.close();

        heart3.moveTo(125f* scale,5f* scale);
        heart3.lineTo(135f* scale,10f* scale);
        heart3.lineTo(145f* scale,5f* scale);
        heart3.lineTo(155f* scale,10f* scale);
        heart3.lineTo(135f* scale,25f* scale);
        heart3.lineTo(115f* scale,10f* scale);
        heart3.lineTo(125f* scale,5f* scale);
        heart3.close();


    }

    @Override
    protected void onDraw(Canvas canvas ) {
        super.onDraw(canvas);

        for(int i = 0 ; i<bricks.length ; i++){

            if(bricks[i].getHit() == 1 ) {
                canvas.drawRect(bricks[i].getLeft(), bricks[i].getTop(), bricks[i].getRight(), bricks[i].getBottom(), paint);
                canvas.drawRect(bricks[i].getLeft(), bricks[i].getTop(), bricks[i].getRight(), bricks[i].getBottom(), stroke);
            }
            //canvas.drawText("100",bricks[i].getLeft() , bricks[i].getRight() , bluePaint);

        }

        canvas.drawCircle(ball.getCenterX() , ball.getCenterY(), ball.getRadius(),bluePaint);
        canvas.drawRect(platform.getLeft() ,platform.getTop(),platform.getRight(),platform.getBottom(),paint);

        // canvas.drawPath(heart , paint);
        //canvas.drawPath(heart,stroke);

        canvas.drawRect(borderRight , transperent);
        canvas.drawRect(borderLeft , transperent);
        canvas.drawRect(borderTop , transperent);
        canvas.drawRect(borderBottom , transperent);

        //drawing the hearts

        canvas.drawPath(heart, heartPaint);
        canvas.drawPath(heart2, heartPaint2);
        canvas.drawPath(heart3, heartPaint3);
        canvas.drawPath(heart, stroke);
        canvas.drawPath(heart2, stroke);
        canvas.drawPath(heart3, stroke);
        canvas.drawText(scoretxt + score, 190 * scale, 25 * scale, textPaint);




        //Losing lives
        if(lostLives >= 1){
            canvas.drawPath(heart3,transperent);
            //invalidate();
        }
        if(lostLives >= 2){
            canvas.drawPath(heart2 , transperent);
            //canvas.drawPath(heart2 , stroke);
        }
    }



    void createMatrix(Brick[] b) {

        bricks = b;
        invalidate();
    }

    public Brick[] deleteBrick(Brick[] B, Brick b){
        b = deleteBr;
        bricks =B;
        invalidate();
        return bricks;
    }

    Ball createCircle(float x,float y,float r){
        ball = new Ball(x,y ,r);
        invalidate();
        return ball;
    }

    Brick createPlatform(int l, int t , int r , int b){
        platform = new Brick(l,t,r,b);
        invalidate();
        return  platform;
    }

    Ball moveCircle(Ball c,float x, float y){
        c.setCenterX(c.getCenterX()+x);
        c.setCenterY(c.getCenterY() + y);
        ball = c;
        invalidate();
        return ball;
    }

    Brick movePlatform(Brick p , int x){
        p.setRight(p.getRight() - x);
        p.setLeft(p.getLeft() - x);
        platform = p;
        invalidate();
        return platform;
    }

    void loseLife(){
        lostLives++;
        if(lostLives >= 1) {
            heartPaint3.setColor(Color.TRANSPARENT);
        }
        if(lostLives >= 2){
            heartPaint2.setColor(Color.TRANSPARENT);
        }

        if(lostLives == 3){
            heartPaint.setColor(Color.TRANSPARENT);
        }
        invalidate();
    }

    void setBorders(int screenX ,int screenY){
        borderRight.set((screenX - 1), 0, screenX , screenY);
        borderLeft.set(0, 0, 3 , screenY );
        borderTop.set(0, 0, screenX , 1 );
        borderBottom.set(0 , (screenY-1) , screenX , screenY );
    }

    void setLives(){
        lostLives = 0;
        heartPaint.setColor(Color.RED);
        heartPaint2.setColor(Color.RED);
        heartPaint3.setColor(Color.RED);
        invalidate();
    }

    void setScore(int s){
        score = s;
        invalidate();

    }

}

