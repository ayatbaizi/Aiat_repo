package org.aiat;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        File file = new File(args[0]);
        //String file = new String("author.txt");

        NumberMovesCount numberMovesCount = new NumberMovesCount();
        ArrayNumbersReader arrayNumbersReader = new ArrayNumbersReader();
        ArrayList<Integer> num = arrayNumbersReader.numbersReader(String.valueOf(file));
        int moves = numberMovesCount.movesCounter(num);
        System.out.println(moves);
    }
}