package syntax.org.il.gameproject;

public class Levels {


    public Levels(int s,int sx,int sy ){
        levels = new Brick[size][];
        this.scale = s;
        this.screenX = sx;
        this.screenY = sy;
    }

    public void setLevel_1() {
        Row = 2;
        Colum = 4;
        Brick[] bricks = new Brick[Row * Colum];
        for (int i = 0; i < Row; i++) {
            heightChange += 30*scale;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick( (screenX/3 + widthChange),  (screenY/4 + heightChange),  (screenX/3 + 50*scale + widthChange),  (screenY/4 + 30*scale + heightChange),1);
                //bricks[index].setRectF();
                index++;
                widthChange += 50*scale;
            }
        }
        heightChange = 0;
        levels[0] = bricks;
        index =0;
    }

    public void setLevel_2() {
        Row = 3;
        Colum = 4;
        Brick[] bricks = new Brick[Row * Colum];
        for (int i = 0; i < Row; i++) {
            heightChange += 30*scale;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick( (screenX/3 + widthChange),  (screenY/4 + heightChange),  (screenX/3 + 50*scale + widthChange),  (screenY/4 + 30*scale + heightChange),1);
                index++;
                widthChange += 50*scale;
            }
        }
        heightChange = 0;
        levels[1] = bricks;
        index = 0;
    }

    public void setLevel_3() {
        Row = 4;
        Colum = 4;
        Brick[] bricks = new Brick[Row * Colum];
        for (int i = 0; i < Row; i++) {
            heightChange += 30*scale;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick( (screenX/3 + widthChange),  (screenY/4 + heightChange),  (screenX/3 + 50*scale + widthChange),  (screenY/4 + 30*scale + heightChange),1);
                index++;
                widthChange += 50*scale;
            }
        }
        heightChange = 0;
        levels[2] = bricks;
        index = 0;
    }

    public void setLevel_4(){
        Row = 4;
        Colum = 4;
        Brick[] bricks = new Brick[Row * Colum];
        for (int i = 0; i < Row; i++) {
            heightChange += 30*scale;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick( (screenX/3 + widthChange),  (screenY/4 + heightChange),  (screenX/3 + 50*scale + widthChange),  (screenY/4 + 30*scale + heightChange),1);
                index++;
                widthChange += 50*scale;
            }
        }
        heightChange = 0;
        levels[3] = bricks;
        index = 0;
    }

    public void setLevel_5(){
        Row = 6;
        Colum = 6;
        Brick[] bricks = new Brick[Row * Colum];
        for (int i = 0; i < Row; i++) {
            heightChange += 30*scale;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick( (screenX/3 + widthChange),  (screenY/4 + heightChange),  (screenX/3 + 50*scale + widthChange),  (screenY/4 + 30*scale + heightChange),1);
                index++;
                widthChange += 50*scale;
            }
        }
        heightChange = 0;
        levels[4] = bricks;
        index = 0;
    }

    public void setLevel_6(){
        Row = 7;
        Colum = 6;
        Brick[] bricks = new Brick[Row * Colum];
        for (int i = 0; i < Row; i++) {
            heightChange += 30*scale;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick( (screenX/3 + widthChange),  (screenY/4 + heightChange),  (screenX/3 + 50*scale + widthChange),  (screenY/4 + 30*scale + heightChange),1);
                index++;
                widthChange += 50*scale;
            }
        }
        heightChange = 0;
        levels[5] = bricks;
        index = 0;
    }



    public Brick[] getLevel_1()
    {
        return  levels[0];
    }

    public Brick[] getLevel_2()
    {
        return  levels[1];
    }

    public Brick[] getLevel_3(){
        return  levels[2];
    }

    public  Brick[] getLevel_4(){
        return levels[3];
    }

    public Brick[] getLevel_5(){
        return levels[4];
    }

    public Brick[] getLevel_6(){
        return levels[5];
    }





    private Brick[][] levels;
    private int size = 10;
    private int Row;
    private int Colum;
    private int index = 0;
    private  int heightChange =0;
    private  int widthChange = 0;
    private int scale;
    private int screenX;
    private int screenY;
}

