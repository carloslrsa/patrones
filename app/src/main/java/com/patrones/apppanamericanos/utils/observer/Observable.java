package com.patrones.apppanamericanos.utils.observer;

import com.patrones.apppanamericanos.utils.observer.design.IObserver;

import java.util.LinkedList;
import java.util.List;

public class Observable<T> {
    private List<IObserver<T>> observers = new LinkedList<>();
    private T data;

    public void addObserver(IObserver<T> observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Intentó agregar un observer nulo.");
        }
        if (observers.contains(observer)) {
            return;
        }
        observers.add(observer);
    }

    public void notifyObservers(T data) {
        this.data = data;
        for (IObserver<T> observer : observers) {
            observer.update(this,data);
        }
    }

    public T getData() {
        return data;
    }
}
