package com.patrones.apppanamericanos.utils.strategy.dates;

import com.patrones.apppanamericanos.utils.strategy.dates.design.IDateComparatorStrategy;

import java.util.Date;

public class AllDaysComparatorStrategy implements IDateComparatorStrategy {
    @Override
    public boolean comparate(Date secondOne) {
        return true;
    }
}
