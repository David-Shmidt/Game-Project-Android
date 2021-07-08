package syntax.org.il.gameproject;

public class Levels {


    public Levels(int s){
        levels = new Brick[size][];
        this.scale = s;
    }

    public void setLevel_1() {
        Row = 1;
        Colum = 4;
        Brick[] bricks = new Brick[Row * Colum];
        for (int i = 0; i < Row; i++) {
            heightChange += 30;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick(scale * (50 + widthChange), scale * (60 + heightChange), scale * (100 + widthChange), scale * (90 + heightChange),1);
                //bricks[index].setRectF();
                index++;
                widthChange += 50;
            }
        }
        heightChange = 0;
        levels[0] = bricks;
        index =0;
    }

    public void setLevel_2() {
        Row = 2;
        Colum = 4;
        Brick[] bricks = new Brick[Row * Colum];
        for (int i = 0; i < Row; i++) {
            heightChange += 30;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick(scale * (50 + widthChange), scale * (60 + heightChange), scale * (100 + widthChange), scale * (90 + heightChange), 1);
                //bricks[index].setRectF();
                index++;
                widthChange += 50;
            }
        }
        heightChange = 0;
        levels[1] = bricks;
        index = 0;
    }

    public void setLevel_3() {
        Row = 3;
        Colum = 4;
        Brick[] bricks = new Brick[Row * Colum];
        for (int i = 0; i < Row; i++) {
            heightChange += 30;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick(scale * (50 + widthChange), scale * (60 + heightChange), scale * (100 + widthChange), scale * (90 + heightChange), 1);
                //bricks[index].setRectF();
                index++;
                widthChange += 50;
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
            heightChange += 30;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick(scale * (50 + widthChange), scale * (i*10+40 + heightChange), scale * (100 + widthChange), scale * (i*10+70 + heightChange), 1);
                //bricks[index].setRectF();
                index++;
                widthChange += 50;
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
            heightChange += 30;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick(scale * (50 + widthChange), scale * (60 + heightChange), scale * (100 + widthChange), scale * (90 + heightChange), 1);
                //bricks[index].setRectF();
                index++;
                widthChange += 50;
            }
        }
        heightChange = 0;
        levels[4] = bricks;
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





    private Brick[][] levels;
    private int size = 10;
    private int Row;
    private int Colum;
    private int index = 0;
    private  int heightChange =0;
    private  int widthChange = 0;
    private int scale;
}

