package com.patrones.apppanamericanos.utils.observer.design;

import com.patrones.apppanamericanos.utils.observer.Observable;

public interface IObserver<T> {
    public void update(Observable<T> observable, T data);
}
