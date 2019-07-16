package com.patrones.apppanamericanos.utils.criteria.events;

import com.patrones.apppanamericanos.utils.criteria.design.ICriteria;
import com.patrones.apppanamericanos.models.entities.EventPreview;
import com.patrones.apppanamericanos.utils.strategy.dates.design.IDateComparatorStrategy;

import java.util.ArrayList;
import java.util.List;

public class CriteriaEventDate implements ICriteria<EventPreview> {

    IDateComparatorStrategy strategy;

    public CriteriaEventDate(IDateComparatorStrategy strategy){
        this.strategy = strategy;
    }
    @Override
    public List<EventPreview> meetCriteria(List<EventPreview> base) {
        List<EventPreview> returnList = new ArrayList<>();

        for (EventPreview event : base) {
            if (strategy.comparate(event.getDate())) {
                returnList.add(event);
            }
        }
        return returnList;
    }
}
