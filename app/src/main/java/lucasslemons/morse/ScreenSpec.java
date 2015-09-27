package lucasslemons.morse;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;


public class ScreenSpec {
    //window dimentions
    private WindowManager wm;
    private Display display;
    private Point screenDim; private int screenWidth, screenHeight;
    private Context context;

    public ScreenSpec(Context context){
        this.context = context;
        //get screen width and height(x & y)
        wm = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
        display = wm.getDefaultDisplay();
        screenDim = new Point();
        display.getSize(screenDim);
        screenWidth = screenDim.x;
        //screenHeight = screenDim.y;
        screenHeight = screenDim.y - screenDim.y/10;//make room for ad
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }
}
