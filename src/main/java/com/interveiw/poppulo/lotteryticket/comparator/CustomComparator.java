package com.interveiw.poppulo.lotteryticket.comparator;

import com.interveiw.poppulo.lotteryticket.data.model.Line;

import java.util.Comparator;

public class CustomComparator implements Comparator<Line> {
    @Override
    public int compare(Line o1, Line o2) {
        return o1.getResult().compareTo(o2.getResult());
    }
}
