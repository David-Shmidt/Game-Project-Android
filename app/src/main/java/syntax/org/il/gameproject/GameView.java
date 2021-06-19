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
Paint transperent = new Paint();
Rect gameRect = new Rect();
Rect deletRect = new Rect();
boolean toDelete = false;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(30);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas ) {
        super.onDraw(canvas);
        if(!toDelete){
        canvas.drawRect(gameRect,paint);
        }
        if(toDelete){
            canvas.drawRect(deletRect,transperent);
            toDelete = false;
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

    void delete(Rect rect){
        toDelete = true;
        deletRect = rect;
        invalidate();
    }


}

