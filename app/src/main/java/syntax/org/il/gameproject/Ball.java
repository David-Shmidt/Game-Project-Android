package syntax.org.il.gameproject;

public class Ball {

   public Ball(){}

   public Ball(float x, float y , float r){
      centerX = x;
      centerY = y;
      radius = r;
   }

   public boolean hitsBrick(Brick b){

      if(this.getCenterX() > b.getLeft() && this.getCenterX() < b.getRight()
              &&this.getCenterY() > b.getTop() && this.getCenterY() < b.getBottom()){
         return true;
      }
      else if(this.getCenterX() == b.getLeft() && this.getCenterY() == b.getBottom()){
         return true;
      }
      else if(this.getCenterX() == b.getLeft() && this.getCenterY() == b.getTop()){
         return true;
      }
      else if(this.getCenterX() == b.getRight() && this.getCenterY() == b.getBottom()){
         return true;
      }
      else if(this.getCenterX() == b.getRight() && this.getCenterY() == b.getTop()){
         return true;
      }

      return false;

   }

   boolean exactCenter(Ball c1 , Ball c2){
      return(c1.getCenterX() == c2.getCenterX() && c1.getCenterY() == c2.getCenterY());
   }

   public float getCenterX() {
      return centerX;
   }

   public float getCenterY() {
      return centerY;
   }

   public float getRadius() {
      return radius;
   }

   public void setCenterX(float centerX) {
      this.centerX = centerX;
   }

   public void setCenterY(float centerY) {
      this.centerY = centerY;
   }

   public void setRadius(float radius) {
      this.radius = radius;
   }

   private float centerX;
   private float centerY;
   private float radius;
}
