package lucasslemons.morse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

class LetterListMain {
    Context context;

    //window spec
    ScreenSpec screenSpec;
    //tree nav
    public LetterList treeRoot;
    public LetterList navTree;
    int []arraI;

    public LetterListMain (Context context){
        this.context = context;

        //tree nav
        LetterListManager treeManager;

        //screen spec
        screenSpec = new ScreenSpec(context);

        //set up tree
        treeManager = new LetterListManager();
        treeRoot = treeManager.getRoot();
        navTree = treeRoot;

        //fake binary number array create
        arraI = new int[30];
        arraI[0] = 16;
        arraI[1] = 8;
        arraI[2] = 4;
        arraI[3] = 2;
        arraI[4] = 1;
        arraI[5] = 3;
        arraI[6] = 6;
        arraI[7] = 5;
        arraI[8] = 12;
        arraI[9] = 10;
        arraI[10] = 9;
        arraI[11] = 14;
        arraI[12] = 13;
        arraI[13] = 15;
        arraI[14] = 28;
        arraI[15] = 22;
        arraI[16] = 19;
        arraI[17] = 17;
        arraI[18] = 21;
        arraI[19] = 26;
        arraI[20] = 24;
        arraI[21] = 27;
        arraI[22] = 40;
        arraI[23] = 34;
        arraI[24] = 31;
        arraI[25] = 38;
        arraI[26] = 42;
    }

    //draws morse tree
    public void lookThroughTree(Canvas canvas, int searchNum){
        LetterList current = navTree, parent = navTree;
        while (true) {

            if (current == null){
                break;
            }

            else if (searchNum < current.getNavNumber()) {
                parent = current;
                current = current.nodeL;
            }

            else if (searchNum > current.getNavNumber()) {
                parent = current;
                current = current.nodeR;
            }

            else {
                Paint tempTextPaint = new Paint();
                tempTextPaint.setARGB(255, 0, 0, 0);
                tempTextPaint.setTextSize(45);

                //draw circle
                cirleChangeDraw(current, parent).Draw(canvas);

                //Draw lines between tree elements
                Paint linePaint = new Paint();
                linePaint.setARGB(255, 255, 255, 255);
                if(parent != treeRoot){
                    float x1;
                    float y1;
                    float x2;
                    float y2;
                    double angle = Math.atan((double)(parent.circle.y - current.circle.y)/(parent.circle.x - current.circle.x));
                    if(parent.nodeL == current){
                        x1 = parent.circle.x - (float)Math.cos(angle) * parent.circle.radius;
                        y1 = parent.circle.y - (float)Math.sin(angle) * parent.circle.radius;
                        x2 = current.circle.x +(float)Math.cos(angle) * current.circle.radius;
                        y2 = current.circle.y +(float)Math.sin(angle) * current.circle.radius;}
                    else{
                        x1 = parent.circle.x + (float)Math.cos(angle) * parent.circle.radius;
                        y1 = parent.circle.y + (float)Math.sin(angle) * parent.circle.radius;
                        x2 = current.circle.x - (float)Math.cos(angle) * current.circle.radius;
                        y2 = current.circle.y - (float)Math.sin(angle) * current.circle.radius;}

                    canvas.drawLine(x1, y1, x2, y2, linePaint);
                }
                float textWidth = tempTextPaint.measureText(current.getUsedLetter());
                //float textHeight = tempTextPaint.measureText(current.getUsedLetter());
                //draw letters on circle
                canvas.drawText(current.getUsedLetter(), current.circle.x - textWidth/2, current.circle.y + current.circle.radius/2, tempTextPaint);
                break;
            }
        }
    }
    //complementary function^^^
    private Circle cirleChangeDraw(LetterList circleNav, LetterList parent){

        circleNav.circle.outlineActive = true;

        circleNav.circle.setColor(255, 255, 25, 25);
        circleNav.circle.radius = 25;

        if(circleNav == parent)
            navTree.circle.setColor(255, 25, 100, 200);
        //move A
        if(circleNav.getUsedLetter() == "A" && parent.getUsedLetter() == "E")
            circleNav.circle.x = parent.circle.x + screenSpec.getScreenWidth()/8;

            //minipulate 3rd up
        else if(parent == treeRoot.nodeL || parent == treeRoot.nodeR){
            if(circleNav == parent.nodeL)
                circleNav.circle.x = parent.circle.x - screenSpec.getScreenWidth()/10;

            else if(circleNav == parent.nodeR)
                circleNav.circle.x = parent.circle.x + screenSpec.getScreenWidth()/6;
        }


        //minipulate top
        else if(parent == treeRoot && parent.nodeL == circleNav){
            circleNav.circle.x = parent.circle.x - screenSpec.getScreenWidth()/4;
        }

        else if(parent == treeRoot && parent.nodeR == circleNav){
            circleNav.circle.x = parent.circle.x + screenSpec.getScreenWidth()/4;
        }

        //minipulate bottom x values
        else if(parent.nodeL == circleNav && (circleNav.nodeL == null && circleNav.nodeR == null)){
            circleNav.circle.x = parent.circle.x - screenSpec.getScreenWidth()/25;
        }

        else if(parent.nodeR == circleNav && (circleNav.nodeL == null && circleNav.nodeR == null)){
            circleNav.circle.x = parent.circle.x + screenSpec.getScreenWidth()/25;
        }

        //remaining x values
        else if(parent.nodeL == circleNav)
            circleNav.circle.x = parent.circle.x - screenSpec.getScreenWidth()/15;

        else if(parent.nodeR == circleNav)
            circleNav.circle.x = parent.circle.x + screenSpec.getScreenWidth()/15;

        //minipulate ys
        if(circleNav.nodeL == null && circleNav.nodeR == null && circleNav.getUsedLetter() != "O"){
            circleNav.circle.y = (float)((double)parent.circle.y + (screenSpec.getScreenHeight()/2)/4);
        }
        else
            circleNav.circle.y = parent.circle.y + (screenSpec.getScreenHeight()/2)/5;

        return circleNav.circle;
    }

    public void navNodeL(){
        navTree = navTree.nodeL;
    }

    public void navNodeR(){
        navTree = navTree.nodeR;
    }

    public void reRootNavTree(){
        navTree = treeRoot;
    }

    public LetterList getTreeRoot() {
        return treeRoot;
    }

    public int[] getArraI() {
        return arraI;
    }

    public LetterList getNavTree() {
        return navTree;
    }
}
public class LetterListManager {
    public LetterList rootMorseTree;
    public LetterList moveMorseTree;

    public LetterListManager(){


        rootMorseTree = new LetterList();
        moveMorseTree = rootMorseTree;
        moveMorseTree.setUsedLetter(" ");
        moveMorseTree.setNavNumber(16);

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("E");
        moveMorseTree.setNavNumber(8);

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("I");
        moveMorseTree.setNavNumber(4);

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("S");
        moveMorseTree.setNavNumber(2);

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("H");
        moveMorseTree.setNavNumber(1);

        moveMorseTree.nodeL = null;
        moveMorseTree.nodeR = null;

        moveMorseTree = rootMorseTree;

        moveMorseTree = moveMorseTree.nodeL;

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree = moveMorseTree.nodeR;
        moveMorseTree.setUsedLetter("A");
        moveMorseTree.setNavNumber(12);

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("R");
        moveMorseTree.setNavNumber(10);

        moveMorseTree.nodeR = null;

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("L");
        moveMorseTree.setNavNumber(9);

        moveMorseTree.nodeL = null;
        moveMorseTree.nodeR = null;

        moveMorseTree = rootMorseTree;

        moveMorseTree = moveMorseTree.nodeL;

        moveMorseTree = moveMorseTree.nodeR;

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree = moveMorseTree.nodeR;
        moveMorseTree.setUsedLetter("W");
        moveMorseTree.setNavNumber(14);

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("P");
        moveMorseTree.setNavNumber(13);

        moveMorseTree.nodeL = null;

        moveMorseTree.nodeR = null;

        moveMorseTree = rootMorseTree;

        moveMorseTree = moveMorseTree.nodeL;

        moveMorseTree = moveMorseTree.nodeR;

        moveMorseTree = moveMorseTree.nodeR;

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree = moveMorseTree.nodeR;
        moveMorseTree.setUsedLetter("J");
        moveMorseTree.setNavNumber(15);

        moveMorseTree.nodeL = null;

        moveMorseTree.nodeR = null;

        moveMorseTree = rootMorseTree;

        moveMorseTree = moveMorseTree.nodeL;

        moveMorseTree = moveMorseTree.nodeL;

        moveMorseTree = moveMorseTree.nodeL;

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree = moveMorseTree.nodeR;
        moveMorseTree.setUsedLetter("V");
        moveMorseTree.setNavNumber(3);

        moveMorseTree.nodeL = null;

        moveMorseTree.nodeR = null;

        moveMorseTree = rootMorseTree;

        moveMorseTree = moveMorseTree.nodeL;

        moveMorseTree = moveMorseTree.nodeL;

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree = moveMorseTree.nodeR;
        moveMorseTree.setUsedLetter("U");
        moveMorseTree.setNavNumber(6);

        moveMorseTree.nodeR = null;

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree.nodeL.setUsedLetter("F");
        moveMorseTree.nodeL.setNavNumber(5);

        moveMorseTree.nodeL.nodeL = null;

        moveMorseTree.nodeL.nodeR = null;

        moveMorseTree = rootMorseTree;

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree = moveMorseTree.nodeR;
        moveMorseTree.setUsedLetter("T");
        moveMorseTree.setNavNumber(28);

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("N");
        moveMorseTree.setNavNumber(22);

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("D");
        moveMorseTree.setNavNumber(19);

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree.nodeR.setUsedLetter("X");
        moveMorseTree.nodeR.setNavNumber(21);

        moveMorseTree.nodeR.nodeR = null;

        moveMorseTree.nodeR.nodeL = null;

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("B");
        moveMorseTree.setNavNumber(17);

        moveMorseTree.nodeL = null;

        moveMorseTree.nodeR = null;

        moveMorseTree = rootMorseTree;

        moveMorseTree = moveMorseTree.nodeR;

        moveMorseTree = moveMorseTree.nodeL;

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree = moveMorseTree.nodeR;
        moveMorseTree.setUsedLetter("K");
        moveMorseTree.setNavNumber(26);

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree.nodeL.setUsedLetter("C");
        moveMorseTree.nodeL.setNavNumber(24);

        moveMorseTree.nodeL.nodeL = null;

        moveMorseTree.nodeL.nodeR = null;

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree = moveMorseTree.nodeR;
        moveMorseTree.setUsedLetter("Y");
        moveMorseTree.setNavNumber(27);

        moveMorseTree.nodeL = null;

        moveMorseTree.nodeR = null;

        moveMorseTree = rootMorseTree;

        moveMorseTree = moveMorseTree.nodeR;

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree = moveMorseTree.nodeR;
        moveMorseTree.setUsedLetter("M");
        moveMorseTree.setNavNumber(40);

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree.nodeR.setUsedLetter("O");
        moveMorseTree.nodeR.setNavNumber(42);

        moveMorseTree.nodeR.nodeL = null;

        moveMorseTree.nodeR.nodeR = null;

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree = moveMorseTree.nodeL;
        moveMorseTree.setUsedLetter("G");
        moveMorseTree.setNavNumber(34);

        moveMorseTree.nodeL = new LetterList();
        moveMorseTree.nodeL.setUsedLetter("Z");
        moveMorseTree.nodeL.setNavNumber(31);

        moveMorseTree.nodeL.nodeL = null;

        moveMorseTree.nodeL.nodeR = null;

        moveMorseTree.nodeR = new LetterList();
        moveMorseTree = moveMorseTree.nodeR;
        moveMorseTree.setUsedLetter("Q");
        moveMorseTree.setNavNumber(38);

        moveMorseTree.nodeL = null;

        moveMorseTree.nodeR = null;

    }

    public LetterList getRoot(){return rootMorseTree;}
}
class LetterList {

    public LetterList nodeL, nodeR;

    public Circle circle;

    private int navNumber;

    public boolean hasDrawn;

    private String usedLetter;

    public LetterList(){
        circle = new Circle(10);
        nodeL = null;
        nodeR = null;
        hasDrawn = false;
    }

    public void setUsedLetter(String letter){ usedLetter = letter;}
    public void setNavNumber(int num){navNumber = num;}

    public String getUsedLetter(){return usedLetter;}
    public int getNavNumber(){return navNumber;}
}
