package com.patrones.apppanamericanos.models.session.commands.design;

import com.patrones.apppanamericanos.models.entities.User;

public interface ISessionCommand<T> {
    T execute();
}
