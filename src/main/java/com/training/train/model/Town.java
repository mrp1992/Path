package com.training.train.model;

import java.util.HashMap;
import java.util.Map;

public class Town {

    private final String townName;
    Map<Town, Integer> desinationRouteMap;

    public Town(String townName) {
        this.townName = townName;
        this.desinationRouteMap = new HashMap<>();
    }

    public String getTownName() {
        return townName;
    }

    public Map<Town, Integer> getDesinationRouteMap() {
        return desinationRouteMap;
    }

    @Override
    public String toString() {
        return  townName + " ";
    }
}
