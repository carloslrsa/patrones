package com.patrones.apppanamericanos.utils.callbacks;

public interface SimpleCallback<T> {
    public void OnResult(T result);
    public void OnFailure(String message);
}