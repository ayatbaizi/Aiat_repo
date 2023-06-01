package org.aiat;

public class Main {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        int[] circleArray = new int[n];
        for (int i = 0; i < n; ++i){
            circleArray[i] = i + 1;
        }
        int current = 0;
        do {
            System.out.print(circleArray[current]);
            current = (current + m - 1) % n;
        } while (current != 0);
    }
}