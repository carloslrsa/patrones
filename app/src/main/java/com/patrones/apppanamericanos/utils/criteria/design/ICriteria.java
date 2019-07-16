package com.patrones.apppanamericanos.utils.criteria.design;

import java.util.List;

public interface ICriteria<T> {
    List<T> meetCriteria(List<T> base);
}
