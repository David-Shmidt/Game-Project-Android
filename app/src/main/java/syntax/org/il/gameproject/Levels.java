package syntax.org.il.gameproject;

public class Levels {


    public Levels(int s){
        levels = new Brick[size][];
        this.scale = s;
    }

    public void setLevel_1() {
        Row = 1;
        Colum = 1;
        Brick[] bricks = new Brick[Row * Colum];
        for (int i = 0; i < Row; i++) {
            heightChange += 30;
            widthChange = 0;
            for (int j = 0; j < Colum; j++) {
                bricks[index] = new Brick(scale * (50 + widthChange), scale * (30 + heightChange), scale * (100 + widthChange), scale * (60 + heightChange), 1);
                //bricks[index].setRectF();
                index++;
                widthChange += 50;
            }
        }
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
                bricks[index] = new Brick(scale * (50 + widthChange), scale * (30 + heightChange), scale * (100 + widthChange), scale * (60 + heightChange), 1);
                //bricks[index].setRectF();
                index++;
                widthChange += 50;
            }
        }
        levels[1] = bricks;
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




    private Brick[][] levels;
    private int size = 10;
    private int Row;
    private int Colum;
    private int index = 0;
    private  int heightChange =0;
    private  int widthChange = 0;
    private int scale;
}

