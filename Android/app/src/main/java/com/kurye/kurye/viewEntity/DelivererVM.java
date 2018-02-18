package com.kurye.kurye.viewEntity;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.kurye.kurye.entity.response.RouteEntity;

import java.util.List;

/**
 * Created by ahmet on 2/18/2018
 */

public class DelivererVM extends ViewModel {
    private ObservableField<String> name = new ObservableField<>();
    private ObservableArrayList<RouteEntity> routes = new ObservableArrayList<>();
    private ObservableField<String> id = new ObservableField<>();

    public DelivererVM(String name, List<RouteEntity> routes, String id) {
        this.name.set(name);
        this.routes.addAll(routes);
        this.id.set(id);
    }

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableArrayList<RouteEntity> getRoutes() {
        return routes;
    }

    public ObservableField<String> getId() {
        return id;
    }

    public ObservableField<String> getTime() {
        return name; // TODO: 2/18/2018 change these
    }

    public ObservableField<String> getDescription() {
        return name;
    }

    public ObservableField<String> getCountry() {
        return name;
    }
}
