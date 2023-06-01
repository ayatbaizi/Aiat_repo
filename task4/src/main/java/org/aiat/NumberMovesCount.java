package org.aiat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NumberMovesCount {
    public int movesCounter(ArrayList<Integer> numbers) {
        Collections.sort(numbers); // сортировка списка
        int n = numbers.size();
        int median = numbers.get(n/2); // нахождение медианы
        int moves = 0;
        for (int i = 0; i < n; i++) {
            moves += Math.abs(numbers.get(i) - median); // считаем количество движений
        }
        return moves;
    }
}
