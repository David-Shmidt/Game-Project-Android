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
import android.widget.TextView;


public class GameView extends TextView {

    float StartX ,startY , endX , endY;
    Paint paint;
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
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.teal_200));
        paint.setStyle(Paint.Style.FILL);
        transperent.setColor(Color.TRANSPARENT);
        transperent.setStyle(Paint.Style.FILL);
        bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setStyle(Paint.Style.FILL);
        bluePaint.setColor(Color.BLUE);

        //Borders

        /*borderRight.set(357*scale , 0 , 358*scale , 600*scale);
        borderLeft.set(0 , 0 , 3*scale , 600*scale);
        borderTop.set(0 , 0 , 400*scale , 1*scale);
        borderBottom.set(0*scale , 559*scale , 400*scale , 560*scale);*/

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

        for(int i = 0 ; i<Row * Colum ; i++){

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

        canvas.drawRect(borderRight , paint);
        canvas.drawRect(borderLeft , paint);
        canvas.drawRect(borderTop , paint);
        canvas.drawRect(borderBottom , paint);

        //drawing the hearts

        canvas.drawPath(heart, heartPaint);
        canvas.drawPath(heart2, heartPaint2);
        canvas.drawPath(heart3, heartPaint3);
        canvas.drawPath(heart, stroke);
        canvas.drawPath(heart2, stroke);
        canvas.drawPath(heart3, stroke);



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



    Brick[] createMatrix(int row , int colum){
        //matrix = new Rect[row][colum];
        Row = 5;
        Colum = 5;
        bricks = new Brick[Row*Colum];
        for(int i = 0; i<Row;i++){
            heightCahnge += 30;
            // heightCahnge += 8;
            widthChange = 0;
            for(int j = 0; j<Colum; j++){
                //matrix[i][j] = new Rect();

                //matrix[i][j].set(scale*(50+widthChange) , scale*(30 + heightCahnge ),  scale*(100 + widthChange),  scale*(60 + heightCahnge) );
                bricks[bricksCount] = new Brick(scale*(50+widthChange) , scale*(30 + heightCahnge ),  scale*(100 + widthChange),  scale*(60 + heightCahnge) , 1 );
                // bricks[bricksCount] = new Brick(scale*(10+widthChange) , scale*(6 + heightCahnge ),  scale*(20 + widthChange),  scale*(12 + heightCahnge) );
                bricksCount++;
                //canvas.drawRect(matrix[i][j] , paint);
                widthChange +=50;
                //widthChange +=12;
            }
        }
        invalidate();
        return bricks;
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
        borderRight.set((screenX - 1) * scale, 0, screenX * scale, screenY* scale);
        borderLeft.set(0, 0, 3 * scale, screenY * scale);
        borderTop.set(0, 0, screenX * scale, 1 * scale);
        borderBottom.set(0 * scale, (screenY-1) * scale, screenX * scale, screenY * scale);
    }


}

