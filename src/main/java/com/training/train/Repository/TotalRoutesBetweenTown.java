package com.training.train.Repository;

import com.training.train.exception.TownException;
import com.training.train.model.Town;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.training.train.RouteParser.parseRoute;

public class TotalRoutesBetweenTown {

    private static Map<Town, Boolean> visitedMap;
    private static List<String> availableRoutes;

    public TotalRoutesBetweenTown() {
        visitedMap = new HashMap<>();
        availableRoutes = new ArrayList<>();
    }

    public int fetchTotalRoutes(List<Town> townList, String route) throws TownException {
        List<Town> routeTowns = parseRoute(townList, route);
        Town sourceTown = routeTowns.get(0);
        Town destinationTown = routeTowns.get(1);
        StringBuilder routeBuilder = new StringBuilder(sourceTown.getTownName());

        townList.forEach(town ->
                visitedMap.put(town, false)
        );

        depthFirst(sourceTown, destinationTown, routeBuilder);

        return availableRoutes.size();
    }

    private void depthFirst(Town town, Town destinationTown, StringBuilder routeBuilder) {
        for (Map.Entry<Town, Integer> townKeySet : town.getDesinationRouteMap().entrySet()) {
            Town currentTown = townKeySet.getKey();
            if (visitedMap.get(currentTown)) {
                continue;
            }
            if (currentTown.equals(destinationTown)) {
                visitedMap.put(destinationTown, true);
                routeBuilder.append(currentTown.getTownName());
                availableRoutes.add(routeBuilder.toString());
                routeBuilder.deleteCharAt(routeBuilder.length() - 1);
                visitedMap.put(destinationTown, false);
                break;
            }
        }

        for (Map.Entry<Town, Integer> townKeySet : town.getDesinationRouteMap().entrySet()) {
            Town currentTown = townKeySet.getKey();

            if (visitedMap.get(currentTown) || currentTown.equals(destinationTown) || routeBuilder.toString().contains(currentTown.getTownName())) {
                continue;
            }
            visitedMap.put(currentTown, true);
            routeBuilder.append(currentTown.getTownName());
            depthFirst(currentTown, destinationTown, routeBuilder);
            routeBuilder.deleteCharAt(routeBuilder.length() - 1);
        }

    }

}
