package lucasslemons.morse;

import java.util.Random;

public class Dictionary {
    private String [] dic;
    private Random randomNum;
    public Dictionary(){
        randomNum = new Random();
        dic = new String[9];
        dic[0] = "CAT";
        dic[1] = "DOG";
        dic[2] = "FISH";
        dic[3] = "APPLE";
        dic[4] = "RAT";
        dic[5] = "PEAR";
        dic[6] = "EAR";
        dic[7] = "NOSE";
        dic[8] = "LAUGH";

    }
    public String getRandomDictionaryWord(){
        return dic[randomNum.nextInt(5)];
    }
}
