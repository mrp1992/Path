package com.training.train.Repository;

import com.training.train.exception.RouteException;
import com.training.train.exception.TownException;
import com.training.train.model.Town;

import java.util.List;
import java.util.Map;

import static com.training.train.RouteParser.parseRoute;

public class DistanceAlongRoutes {
    public int calculateDistance(List<Town> townList, String route) throws RouteException, TownException {
        List<Town> routeTowns = parseRoute(townList, route);
        int totalDistance = 0;

        for (int i = 0, j = 1; i < routeTowns.size() && j <= routeTowns.size() - 1; i++, j++) {
            Town sourceTown = routeTowns.get(i);
            Town destinationTown = routeTowns.get(j);
            Map<Town, Integer> outgoingRouteMap = sourceTown.getDesinationRouteMap();
            if (outgoingRouteMap.containsKey(destinationTown)) {
                totalDistance += outgoingRouteMap.get(destinationTown);
            } else {
                throw new RouteException("No such route exist");
            }
        }

        return totalDistance;
    }

}
