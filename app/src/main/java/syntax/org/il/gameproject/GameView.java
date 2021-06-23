package syntax.org.il.gameproject;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class GameView extends TextView {

    float StartX ,startY , endX , endY;
    Paint paint;
    Paint transperent = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint heartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint heartStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
    Rect gameRect = new Rect();
    Rect deletRect = new Rect();
    RectF deletRectF  =new RectF();
    boolean toDelete = false;
    boolean toDeleteF = false;

    int scale = (int)getResources().getDisplayMetrics().density;
    int lostLives = 0;


    int Row, Colum;
    Rect[][] matrix;
    Rect tempRect = new Rect();
    int widthChange = 0 , heightCahnge = 0;
    Rect[] bricks;
    int bricksCount = 0;
    Path heart = new Path();
    Path heart2 = new Path();
    Path heart3 = new Path();



    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        heartPaint.setColor(Color.RED);
        heartPaint.setStyle(Paint.Style.FILL);
        heartStroke.setColor(Color.BLACK);
        heartStroke.setStyle(Paint.Style.STROKE);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.teal_200));
        paint.setStyle(Paint.Style.FILL);
        transperent.setColor(Color.TRANSPARENT);
        transperent.setStyle(Paint.Style.FILL);

        //Life
        heart.moveTo(25f * scale,5f* scale);
        heart.lineTo(35f* scale,10f* scale);
        heart.lineTo(45f* scale,5f* scale);
        heart.lineTo(55f* scale,10f* scale);
        heart.lineTo(35f* scale,25f* scale);
        heart.lineTo(15f* scale,10f* scale);
        heart.lineTo(25f* scale,5f* scale);
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

        if(!toDelete){
            //drawing the blocks
            for(int i = 0; i<Row ; i++){
                for(int j=0;j<Colum;j++){
                    if(matrix[i][j] != deletRect) {
                        canvas.drawRect(matrix[i][j], paint);
                    }
                }
            }
        }
        if(toDelete){
            canvas.drawRect(deletRect,transperent);
            toDelete = false;
            invalidate();
        }

        //drawing the hearts

    canvas.drawPath(heart, heartPaint);
    canvas.drawPath(heart2, heartPaint);
    canvas.drawPath(heart3, heartPaint);
    canvas.drawPath(heart, heartStroke);
    canvas.drawPath(heart2, heartStroke);
    canvas.drawPath(heart3, heartStroke);



        //Losing lives
        if(lostLives == 1){
            canvas.drawPath(heart3,transperent);
            //invalidate();
        }
        else if(lostLives == 2){
            canvas.drawPath(heart2 , transperent);
            canvas.drawPath(heart2 , heartStroke);
        }
    }



    Rect[] createMatrix(int row , int colum){
        matrix = new Rect[row][colum];
        Row = row;
        Colum = colum;
        bricks = new Rect[Row*Colum];
        for(int i = 0; i<Row;i++){
            heightCahnge += 35;
            widthChange = 0;
            for(int j = 0; j<Colum; j++){
                matrix[i][j] = new Rect();
                matrix[i][j].set(scale*(50+widthChange) , scale*(30 + heightCahnge ),  scale*(100 + widthChange),  scale*(60 + heightCahnge) );
                bricks[bricksCount] = matrix[i][j];
                bricksCount++;
                //bricks[bricksCount] = new Rect(200+widthChange ,100 + heightCahnge ,400 + widthChange, 200 + heightCahnge);
                //canvas.drawRect(matrix[i][j] , paint);
                widthChange +=55;
            }
        }
        invalidate();
        return bricks;
    }

    void delete(Rect rect , int index){
        toDelete = true;
        deletRect = rect;
        invalidate();
    }

    void loseLife(){
        lostLives=1;
       // heartPaint.setColor(Color.BLACK);
        invalidate();
    }

    /* Rect sendParams(int sx, int sy , int ex , int ey){
     *//* StartX = sx;
        startY = sy;
        endX = ex;
        endY = ey;*//*

        gameRect.set(sx,sy,ex,ey);
        invalidate();
        return gameRect;

    }*/

}

