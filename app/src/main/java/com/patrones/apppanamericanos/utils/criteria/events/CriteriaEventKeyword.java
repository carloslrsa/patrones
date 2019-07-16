package com.patrones.apppanamericanos.utils.criteria.events;

import com.patrones.apppanamericanos.utils.criteria.design.ICriteria;
import com.patrones.apppanamericanos.models.entities.EventPreview;

import java.util.ArrayList;
import java.util.List;

public class CriteriaEventKeyword implements ICriteria<EventPreview> {
    private String keyword;

    public CriteriaEventKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public List<EventPreview> meetCriteria(List<EventPreview> base) {
        if (keyword.isEmpty()) {
            return base;
        }
        List<EventPreview> returnList = new ArrayList<>();
        for (EventPreview event : base) {
            if (event.getTitle().toUpperCase().contains(keyword.toUpperCase())) {
                returnList.add(event);
            }
        }
        return returnList;
    }
}
