package lucasslemons.morse;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circle{
    private Paint paint;
    private Paint paintOutline;
    public float x, y, radius;


    public boolean isActive;

    public boolean outlineActive;

    public Circle() {
        x = 100;
        y = 100;
        radius = 200;
        paint = new Paint();
        paint.setARGB(255, 255, 0, 10);
        isActive = false;

        paintOutline = new Paint();
        paintOutline.setARGB(150, 255, 0, 10);
        outlineActive = false;
    }

    public Circle(float inputRadius) {
        x = 100;
        y = 100;
        radius = inputRadius;
        paint = new Paint();
        paint.setARGB(255, 255, 0, 10);
        isActive = false;
    }

    //draw plain circle
    public void Draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, this.paint);
    }

    //set color
    public void setColor(int A, int R, int G, int B){
        paint.setARGB(A, R, G, B);
    }

    //returns true if touch is within the bounds of the circle
    public boolean checkPressSelectBounds(float touchX, float touchY){// a^2 + b^2 = c^2
        //calc distance from center
        double distanceFromCenter = Math.abs( Math.sqrt(
                        Math.pow((touchX - x), 2) +
                        Math.pow((touchY - y), 2)));

        if(distanceFromCenter <= radius)
            return true;

        else
            return false;
    }

}