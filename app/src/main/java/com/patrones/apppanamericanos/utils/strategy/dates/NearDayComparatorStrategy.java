package com.patrones.apppanamericanos.utils.strategy.dates;

import com.patrones.apppanamericanos.utils.strategy.dates.design.IDateComparatorStrategy;

import java.util.Date;

public class NearDayComparatorStrategy implements IDateComparatorStrategy {

    private static long MILLISFROMDAY = 86400000;
    private  Date current;
    private int days;
    private long daysToMillis;

    public NearDayComparatorStrategy(Date current, int days) {
        this.current = current;
        this.days = days;
        daysToMillis = MILLISFROMDAY * days;
    }

    @Override
    public boolean comparate(Date secondOne) {
        long currentMillis = current.getTime();
        long startMillis = currentMillis - daysToMillis;
        long endMillis = currentMillis + daysToMillis;
        long secondOneMillis = secondOne.getTime();

        return secondOneMillis >= startMillis && secondOneMillis <= endMillis;
    }
}
