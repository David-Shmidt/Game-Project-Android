package syntax.org.il.gameproject;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

public class ballObject extends View {

   private int width;
   private int height;

   public ballObject(Context context , int X , int Y) {
      super(context);

   }

   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
   }
}
