package syntax.org.il.gameproject;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

public class BreaksObject extends View {

    public BreaksObject(Context context) {
        super(context);
    }

    public static class Brick {

        private RectF rect;

        private boolean isVisible;

        public Brick(int row, int column, float width, float height){

            isVisible = true;

            int padding = 1;

            rect = new RectF(column * width + padding,
                    row * height + padding,
                    column * width + width - padding,
                    row * height + height - padding);
        }

        public RectF getRect(){
            return this.rect;
        }

        public void setInvisible(){
            isVisible = false;
        }

        public boolean getVisibility(){
            return isVisible;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}
