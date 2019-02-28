package com.training.train.Repository;

import com.training.train.RouteParser;
import com.training.train.exception.RouteException;
import com.training.train.exception.TownException;
import com.training.train.model.Town;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestPathRoute {

    private Map<Town, Boolean> visitedMap;
    private Map<String, Integer> shortestPathMap;
    private String currentShortestPath = null;

    public ShortestPathRoute() {
        visitedMap = new HashMap<>();
        shortestPathMap = new HashMap<>();
    }

    public int findShortestPath(List<Town> townList, String route) throws TownException, RouteException {
        List<Town> towns = RouteParser.parseRoute(townList, route);
        if(towns.size() >= 3) {
            throw new TownException("Please provide exactly 2 towns");
        }
        Town sourceTown = towns.get(0);
        Town destinationTown = towns.get(1);
        StringBuilder routeBuilder = new StringBuilder(sourceTown.getTownName());

        currentShortestPath = sourceTown.getTownName();
        shortestPathMap.put(currentShortestPath, Integer.MAX_VALUE);
        townList.forEach(town -> visitedMap.put(town, false));
        shortestPath(sourceTown, destinationTown, routeBuilder, 0);

        if(shortestPathMap.get(currentShortestPath).equals(Integer.MAX_VALUE)) {
            throw new RouteException("No such route exist");
        }

        return shortestPathMap.get(currentShortestPath);
    }

    private void shortestPath(Town currentTown, Town destinationTown, StringBuilder routeBuilder, int routBuilderValue) {

        for(Map.Entry<Town, Integer> townKeySet: currentTown.getDesinationRouteMap().entrySet()) {
            Town town = townKeySet.getKey();
            if(town.equals(destinationTown)) {
                routBuilderValue += currentTown.getDesinationRouteMap().get(town);
                if(shortestPathMap.get(currentShortestPath) > routBuilderValue) {
                    shortestPathMap.remove(currentShortestPath);
                    routeBuilder.append(town.getTownName());
                    currentShortestPath = routeBuilder.toString();
                    shortestPathMap.put(currentShortestPath, routBuilderValue);
                    routeBuilder.deleteCharAt(routeBuilder.length() - 1);
                    routBuilderValue -= currentTown.getDesinationRouteMap().get(town);
                    break;
                }
            }
        }

        for (Map.Entry<Town, Integer> townKeySet : currentTown.getDesinationRouteMap().entrySet()) {
            Town town = townKeySet.getKey();

            if (visitedMap.get(town) || town.equals(destinationTown) || routeBuilder.toString().contains(town.getTownName())) {
                continue;
            }
            visitedMap.put(town, true);
            routeBuilder.append(town.getTownName());
            routBuilderValue +=  currentTown.getDesinationRouteMap().get(town);
            shortestPath(town, destinationTown, routeBuilder, routBuilderValue);
            routBuilderValue -= currentTown.getDesinationRouteMap().get(town);
            routeBuilder.deleteCharAt(routeBuilder.length() - 1);
        }
    }
}
