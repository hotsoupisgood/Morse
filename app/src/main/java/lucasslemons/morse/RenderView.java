package lucasslemons.morse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


public class RenderView extends View {
    //-----initialize variables-------//
    //window spec
    ScreenSpec screenSpec;
    //start time of touch
    double startTime = 0;
    //text used
    public String word;         //written word (top middle)
    public String tempLetter;   //guide letter (top left corner)
    //"activated" on CLEAR
    public boolean clearWord;
    //"de-activated" on checkbox unclick
    public boolean stopDrawTree;
    //circles that do stuff...
    public Circle circleSelect;
    public Circle circleStop;
    public Circle boarderCircle;
    public Circle boarderCircle2;
    //text colors
    public Paint textPaint;
    public Paint tempLetterPaint;
    //tree nav
    LetterListMain mainList;

    //constructor - initializes variable function
    public RenderView(Context context) {
        //idk what this does
        super(context);
        //initialize strings
        word = new String();
        tempLetter = new String();
        //screen
        screenSpec = new ScreenSpec(context);
        mainList = new LetterListMain(context);
        //create variables
        createVars();
    }
    public void onDraw(Canvas canvas){
        //draw circles
        boarderCircle2.Draw(canvas);//draws a boarder around circleSelect

        circleSelect.Draw(canvas);//morse code tapper(don't know what it is called)

        boarderCircle.Draw(canvas);//draws a boarder around circleStop

        circleStop.Draw(canvas);//stop chain and display letter button

        //draw all text(exept letters on the morse helper tree)
        canvas.drawText(word, screenSpec.getScreenWidth()/2, screenSpec.getScreenHeight()/10, this.textPaint);//draw text in the top center of screen
        canvas.drawText(tempLetter, circleStop.x - circleStop.radius / 10,
                circleStop.y + circleStop.radius / 2, this.tempLetterPaint);//draw helper letter on top left of screen

        //draws the start point of morse tree (centers it)
        mainList.getNavTree().circle.x = screenSpec.getScreenWidth()/2;
        mainList.getNavTree().circle.y = screenSpec.getScreenHeight()/2 - screenSpec.getScreenHeight()/8;

        //draws direction text
        beginProcedeMorseTree(canvas);

        if(stopDrawTree) {
            //draw morse tree
            for (int i = 1; i <= mainList.getArraI().length - 2; i++) {
                mainList.lookThroughTree(canvas, mainList.getArraI()[i]);
            }
        }
    }
    //touch event listener
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //finger location
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionDown(x, y);
                //render visuals
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                actionUp(x, y);
                //render visuals
                invalidate();
                break;
            default:
        }

        return true;
    }
    //action functions
    private void actionDown(float x, float y){
        startTime = System.currentTimeMillis()/1000;

        //acive colors
        if(circleSelect.checkPressSelectBounds(x, y)){
            circleSelect.setColor(255, 0, 0, 255);}
        if(circleStop.checkPressSelectBounds(x, y)){
            circleStop.setColor(255, 0, 0, 255);}

        if (circleStop.checkPressSelectBounds(x, y)){
            circleStop.isActive = true;
        }
    }
    private void actionUp(float x, float y){
        double endTime = System.currentTimeMillis()/1000 - startTime;

        //deactive colors
        circleSelect.setColor(255, 255, 0, 0);
        circleStop.setColor(255, 255, 0, 0);
        mainList.getNavTree().circle.setColor(255, 25, 40, 255);

        if(circleStop.isActive){

            if(mainList.getNavTree().getUsedLetter() != null) {
                word = word + mainList.getNavTree().getUsedLetter();
                tempLetter = "";
            }
            mainList.reRootNavTree();
            circleStop.isActive = false;
        }

        if (endTime >= .3 && circleSelect.checkPressSelectBounds(x, y)){
            if(mainList.getNavTree().nodeR != null){
                mainList.getNavTree().circle.setColor(255, 25, 40, 255);
                mainList.navNodeR();}

            if(mainList.getNavTree().getUsedLetter() != null) {
                tempLetter = mainList.getNavTree().getUsedLetter();
                mainList.getNavTree().circle.setColor(255, 25, 40, 255);
                invalidate();}
        }

        if(endTime < .3 && circleSelect.checkPressSelectBounds(x, y)){
            if(mainList.getNavTree().nodeL != null){
                mainList.getNavTree().circle.setColor(255, 25, 40, 255);
                mainList.navNodeL();}
            if(mainList.getNavTree().getUsedLetter() != null) {
                tempLetter = mainList.getNavTree().getUsedLetter();
                mainList.getNavTree().circle.setColor(255, 25, 40, 255);
                invalidate();}

        }
    }
    //initalize variables
    private void createVars(){
        //checkable tree
        stopDrawTree = true;

        //create word
        //word = new String();
        textPaint = new Paint();
        textPaint.setARGB(255, 255, 255, 255);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(70);

        //create temp letter
        //tempLetter = new String();
        tempLetterPaint = new Paint();
        tempLetterPaint.setARGB(255, 0, 0, 0);
        tempLetterPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        tempLetterPaint.setTextSize(170);

        //create push button
        circleSelect = new Circle();
        circleSelect.x = screenSpec.getScreenWidth()/2; circleSelect.y = screenSpec.getScreenHeight()/5;
        circleSelect.radius = screenSpec.getScreenWidth()/8;

        //create stop button
        circleStop = new Circle();
        circleSelect.x = screenSpec.getScreenWidth()/2; circleSelect.y = screenSpec.getScreenHeight()/3;
        circleSelect.radius = screenSpec.getScreenWidth()/8;

        //create boarder circles
        boarderCircle = new Circle();
        boarderCircle.x = circleStop.x; boarderCircle.y = circleStop.y;
        boarderCircle.radius = circleStop.radius + screenSpec.getScreenWidth()/50;
        boarderCircle.setColor(175, 255, 0, 10);

        boarderCircle2 = new Circle();
        boarderCircle2.x = circleSelect.x; boarderCircle2.y = circleSelect.y;
        boarderCircle2.radius = circleSelect.radius + screenSpec.getScreenWidth()/70;
        boarderCircle2.setColor(175, 255, 0, 10);

        //set background color
        setBackgroundColor(Color.rgb(0, 0, 0));
    }
    //draws direction text
    private void beginProcedeMorseTree(Canvas canvas){
        Paint textPaint = new Paint();
        textPaint.setARGB(255, 255, 255, 255);
        textPaint.setTextSize(60);
        String shortTxt = "short";
        String longTxt = "long";

        canvas.drawText(shortTxt,
                mainList.getTreeRoot().circle.x - screenSpec.getScreenWidth()/4 - (textPaint.getTextSize() * shortTxt.length()/4),
                mainList.getTreeRoot().circle.y, textPaint);
        canvas.drawText(longTxt,
                mainList.getTreeRoot().circle.x + screenSpec.getScreenWidth()/4 - (textPaint.getTextSize() * shortTxt.length()/4),
                mainList.getTreeRoot().circle.y, textPaint);
    }
    //manipulate drop down(menu)
    public void clearTheWord(){
        word = "";
        clearWord = false;
        invalidate();
    }
    public void checkUncheck(){
        if(stopDrawTree == true)
            stopDrawTree = false;

        else
            stopDrawTree = true;
        invalidate();
    }
}
