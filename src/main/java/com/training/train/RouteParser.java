package com.training.train;

import com.training.train.exception.TownException;
import com.training.train.model.Town;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RouteParser {

    private RouteParser() {
    }

    public static List<Town> parseRoute(List<Town> townList, String route) throws TownException {
        String[] routeArray = route.split("-");

        if (routeArray.length < 2) {
            throw new TownException("Minimum of 2 towns are required in route");
        }

        List<Town> parsedRoute = new ArrayList<>();
        List<String> townNames = townList.stream().map(Town::getTownName).collect(Collectors.toList());
        for (String townName : routeArray) {
            if (townNames.contains(townName)) {
                Town town = townList.get(townNames.indexOf(townName));
                parsedRoute.add(town);
            } else {
                throw new TownException("No such town exist");
            }
        }

        return parsedRoute;
    }
}
