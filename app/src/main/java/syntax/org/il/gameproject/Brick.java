package syntax.org.il.gameproject;

import android.graphics.Rect;
import android.graphics.RectF;

public class Brick extends RectF {


    public  Brick(){}
    public Brick(int l , int t , int r , int b , int h ){
        left = l;
        top = t;
        right = r;
        bottom = b;
        hit = h;
    }
    public Brick(int l , int t , int r , int b ){
        left = l;
        top = t;
        right = r;
        bottom = b;
    }


    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public int getBottom() {
        return bottom;
    }

    public int getHit() {
        return hit;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void set(int l, int t, int r, int b , int h){
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;
        this.hit = h;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int brickCenterX(){
        return (this.getRight() - this.getLeft())/2;
    }

    public int brickCenterY(){
        return (this.getBottom() - this.getTop())/2;
    }

    public boolean intersectsF(Rect rect,RectF rectF){

        return rect.top == rectF.top ||
                rect.left == rectF.left ||
                rect.right == rectF.right ||
                rect.bottom == rectF.bottom;
    }


    private int left;
    private int top;
    private int right;
    private int bottom;
    private int hit;
}
