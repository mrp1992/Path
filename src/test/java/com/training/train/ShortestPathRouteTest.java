package com.training.train;

import com.training.train.Repository.ShortestPathRoute;
import com.training.train.Repository.TotalRoutesBetweenTown;
import com.training.train.exception.RouteException;
import com.training.train.exception.TownException;
import com.training.train.model.Town;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShortestPathRouteTest {

    private Town townA;
    private Town townB;
    private Town townC;
    private Town townD;
    private Town townE;

    @BeforeEach
    void init() {
        townA = new Town("A");
        townB = new Town("B");
        townC = new Town("C");
        townD = new Town("D");
        townE = new Town("E");
    }

    @Test
    void shouldFetchShortestPath() throws Exception{
        ShortestPathRoute shortestPathRoute = new ShortestPathRoute();

        townA.getDesinationRouteMap().put(townB, 5);
        townB.getDesinationRouteMap().put(townC, 4);
        townC.getDesinationRouteMap().put(townD, 8);
        townD.getDesinationRouteMap().put(townC, 8);
        townD.getDesinationRouteMap().put(townE, 6);
        townA.getDesinationRouteMap().put(townD, 5);
        townC.getDesinationRouteMap().put(townE, 2);
        townE.getDesinationRouteMap().put(townB, 3);
        townA.getDesinationRouteMap().put(townE, 7);

        int currentShortestPath = shortestPathRoute.findShortestPath(Arrays.asList(townA, townB, townC, townD, townE), "B-B");

        Assertions.assertEquals(9, currentShortestPath);
    }

    @Test
    void shouldThrowExceptionWhenNoSuchTownExist() {
        ShortestPathRoute shortestPathRoute = new ShortestPathRoute();

        townA.getDesinationRouteMap().put(townB, 5);
        townB.getDesinationRouteMap().put(townC, 3);
        townC.getDesinationRouteMap().put(townD, 4);
        townA.getDesinationRouteMap().put(townD, 6);

        assertThrows(TownException.class,
                () -> shortestPathRoute.findShortestPath(Arrays.asList(townA, townB, townC, townD), "A-F"));
    }

    @Test
    void shouldThrowExceptionWhenMoreThanTwoTownsProvided() {
        ShortestPathRoute shortestPathRoute = new ShortestPathRoute();

        townA.getDesinationRouteMap().put(townB, 5);
        townB.getDesinationRouteMap().put(townC, 3);
        townC.getDesinationRouteMap().put(townD, 4);
        townA.getDesinationRouteMap().put(townD, 6);

        assertThrows(TownException.class,
                () -> shortestPathRoute.findShortestPath(Arrays.asList(townA, townB, townC, townD), "A-C-B"));
    }

    @Test
    void shouldNotReturnAnyRoutesWhenNoSuchRoutesExist() {
        ShortestPathRoute shortestPathRoute = new ShortestPathRoute();

        townA.getDesinationRouteMap().put(townC, 3);
        townC.getDesinationRouteMap().put(townD, 4);
        townB.getDesinationRouteMap().put(townA, 5);
        townD.getDesinationRouteMap().put(townA, 5);

        assertThrows(RouteException.class, () ->
                shortestPathRoute.findShortestPath(Arrays.asList(townA, townB, townC, townD), "A-B"));
    }

}
