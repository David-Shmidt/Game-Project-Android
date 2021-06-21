package syntax.org.il.gameproject;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
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
Rect gameRect = new Rect();
Rect deletRect = new Rect();
RectF deletRectF  =new RectF();
boolean toDelete = false;
boolean toDeleteF = false;

    int Row, Colum;
    Rect[][] matrix;
    Rect tempRect = new Rect();
    int widthChange = 0 , heightCahnge = 0;
    Rect[] bricks;
    int bricksCount = 0;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
      //  paint.setStrokeWidth(30);
        paint.setStyle(Paint.Style.FILL);
        transperent.setColor(Color.TRANSPARENT);
        transperent.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas ) {
        super.onDraw(canvas);
        if(!toDelete){
        //canvas.drawRect(gameRect,paint);
            for(int i = 0; i<Row ; i++){
                for(int j=0;j<Colum;j++){
                    canvas.drawRect(matrix[i][j] , paint);
                }
            }
        }
        if(toDelete){
            canvas.drawRect(deletRect,transperent);
            toDelete = false;
        }
        if(toDeleteF){
            canvas.drawRect(deletRectF,transperent);
            toDeleteF = false;
        }
    }

    Rect sendParams(int sx, int sy , int ex , int ey){
       /* StartX = sx;
        startY = sy;
        endX = ex;
        endY = ey;*/

        gameRect.set(sx,sy,ex,ey);
        invalidate();
        return gameRect;

    }

    Rect[] createMatrix(int row , int colum){
        matrix = new Rect[row][colum];
        Row = row;
        Colum = colum;
        bricks = new Rect[Row*Colum];
        for(int i = 0; i<Row;i++){
            heightCahnge += 110;
            widthChange = 0;
            for(int j = 0; j<Colum; j++){
                matrix[i][j] = new Rect();
                matrix[i][j].set(200+widthChange ,100 + heightCahnge ,400 + widthChange, 200 + heightCahnge );
                bricks[bricksCount] = matrix[i][j];
                bricksCount++;
                //bricks[bricksCount] = new Rect(200+widthChange ,100 + heightCahnge ,400 + widthChange, 200 + heightCahnge);
                //canvas.drawRect(matrix[i][j] , paint);
                widthChange +=210;
            }
        }
        invalidate();
        return bricks;
    }

    void delete(Rect rect){
        toDelete = true;
        deletRect = rect;
        invalidate();
    }

    void deleteF(RectF rectF){
        toDeleteF = true;
        deletRectF = rectF;
        invalidate();
    }


}

