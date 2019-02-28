package com.training.train;

import com.training.train.Repository.TotalRoutesBetweenTown;
import com.training.train.exception.TownException;
import com.training.train.model.Town;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalRoutesBetweenTownTest {
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
    void shouldReturnNumberOfRoutesBetweenTowns() throws Exception {
        TotalRoutesBetweenTown totalRoutesBetweenTown = new TotalRoutesBetweenTown();

        townA.getDesinationRouteMap().put(townB, 5);
        townA.getDesinationRouteMap().put(townC, 2);
        townC.getDesinationRouteMap().put(townB, 3);
        townC.getDesinationRouteMap().put(townD, 4);
        townA.getDesinationRouteMap().put(townD, 6);
        townD.getDesinationRouteMap().put(townA, 3);
        townC.getDesinationRouteMap().put(townA, 5);
        townD.getDesinationRouteMap().put(townB, 5);

        List<Town> townList = Arrays.asList(townA, townB, townC, townD);

        int actualCount = totalRoutesBetweenTown.fetchTotalRoutes(townList, "A-B");

        assertEquals(3, actualCount);
    }

    @Test
    void shouldReturnNumberOfRoutesBetweenTownsWhenSourceAndDestinationIsSame() throws Exception {
        TotalRoutesBetweenTown totalRoutesBetweenTown = new TotalRoutesBetweenTown();

        townA.getDesinationRouteMap().put(townB, 5);
        townA.getDesinationRouteMap().put(townC, 2);
        townC.getDesinationRouteMap().put(townD, 4);
        townA.getDesinationRouteMap().put(townD, 6);
        townD.getDesinationRouteMap().put(townA, 3);
        townC.getDesinationRouteMap().put(townA, 5);
        townD.getDesinationRouteMap().put(townB, 5);
        townB.getDesinationRouteMap().put(townA, 4);
        townE.getDesinationRouteMap().put(townB, 4);
        townC.getDesinationRouteMap().put(townE, 9);

        List<Town> townList = Arrays.asList(townA, townB, townC, townD, townE);

        int actualCount = totalRoutesBetweenTown.fetchTotalRoutes(townList, "A-A");

        assertEquals(3, actualCount);
    }

    @Test
    void shouldNotReturnAnyRoutesWhenNoSuchRoutesExist() throws Exception {
        TotalRoutesBetweenTown totalRoutesBetweenTown = new TotalRoutesBetweenTown();

        townA.getDesinationRouteMap().put(townC, 3);
        townC.getDesinationRouteMap().put(townD, 4);
        townB.getDesinationRouteMap().put(townA, 5);
        townD.getDesinationRouteMap().put(townA, 5);

        int actualCount = totalRoutesBetweenTown.fetchTotalRoutes(Arrays.asList(townA, townB, townC, townD), "A-B");

        assertEquals(0, actualCount);
    }

    @Test
    void shouldThrowExceptionWhenNoSuchTownExist() {
        TotalRoutesBetweenTown totalRoutesBetweenTown = new TotalRoutesBetweenTown();

        townA.getDesinationRouteMap().put(townB, 5);
        townB.getDesinationRouteMap().put(townC, 3);
        townC.getDesinationRouteMap().put(townD, 4);
        townA.getDesinationRouteMap().put(townD, 6);

        Assertions.assertThrows(TownException.class, () -> totalRoutesBetweenTown.fetchTotalRoutes(Arrays.asList(townA, townB, townC, townD), "A-B-E"));
    }

}
