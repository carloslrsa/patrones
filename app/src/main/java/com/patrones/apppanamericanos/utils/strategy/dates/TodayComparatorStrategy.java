package com.patrones.apppanamericanos.utils.strategy.dates;

import com.patrones.apppanamericanos.utils.strategy.dates.design.IDateComparatorStrategy;

import java.util.Calendar;
import java.util.Date;

public class TodayComparatorStrategy implements IDateComparatorStrategy {

    private Date current;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;

    public TodayComparatorStrategy(Date current) {
        this.current = current;
        calendar = Calendar.getInstance();
        calendar.setTime(current);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public boolean comparate(Date secondOne) {
        calendar.setTime(secondOne);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);

        return year == year2 && month == month2 && day == day2;
    }
}
