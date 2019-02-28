package com.training.train;

import com.training.train.Repository.DistanceAlongRoutes;
import com.training.train.model.Town;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DistanceAlongRoutesTest {

    @Test
    void shouldReturnDistanceBetween2Stops() throws Exception {
        DistanceAlongRoutes distanceAlongRoutes = new DistanceAlongRoutes();
        Town townA = new Town("A");
        Town townB = new Town("B");

        townA.getDesinationRouteMap().put(townB, 5);

        List<Town> townList = Arrays.asList(townA, townB);
        int acutalDistance = distanceAlongRoutes.calculateDistance(townList, "A-B");

        Assertions.assertEquals(5, acutalDistance);
    }

    @Test
    void shouldReturnDistanceBetween3Stops() throws Exception {
        DistanceAlongRoutes distanceAlongRoutes = new DistanceAlongRoutes();
        Town townA = new Town("A");
        Town townB = new Town("B");
        Town townC = new Town("C");
        Town townD = new Town("D");

        townA.getDesinationRouteMap().put(townB, 5);
        townB.getDesinationRouteMap().put(townC, 3);
        townC.getDesinationRouteMap().put(townD, 4);
        townA.getDesinationRouteMap().put(townD, 6);

        List<Town> townList = Arrays.asList(townA, townB, townC, townD);
        int actualDistance = distanceAlongRoutes.calculateDistance(townList, "A-B-C");

        Assertions.assertEquals(8, actualDistance);
    }


    @Test
    void shouldThrowExceptionWhenInvalidRoute() {
        DistanceAlongRoutes distanceAlongRoutes = new DistanceAlongRoutes();
        Town townA = new Town("A");
        Town townB = new Town("B");
        Town townC = new Town("C");
        Town townD = new Town("D");

        townA.getDesinationRouteMap().put(townB, 5);
        townB.getDesinationRouteMap().put(townC, 3);
        townC.getDesinationRouteMap().put(townD, 4);
        townA.getDesinationRouteMap().put(townD, 6);

        List<Town> townList = Arrays.asList(townA, townB, townC, townD);

        Assertions.assertThrows(Exception.class, () -> distanceAlongRoutes.calculateDistance(townList, "A"));
    }

    @Test
    void shouldThrowExceptionWhenNoRouteExist() {
        DistanceAlongRoutes distanceAlongRoutes = new DistanceAlongRoutes();
        Town townA = new Town("A");
        Town townB = new Town("B");
        Town townC = new Town("C");
        Town townD = new Town("D");

        townA.getDesinationRouteMap().put(townB, 5);
        townB.getDesinationRouteMap().put(townC, 3);
        townC.getDesinationRouteMap().put(townD, 4);
        townA.getDesinationRouteMap().put(townD, 6);

        List<Town> townList = Arrays.asList(townA, townB, townC, townD);

        Assertions.assertThrows(Exception.class, () -> distanceAlongRoutes.calculateDistance(townList, "A-B-D"));
    }

    @Test
    void shouldThrowExceptionWhenNoSuchTownExist() {
        DistanceAlongRoutes distanceAlongRoutes = new DistanceAlongRoutes();
        Town townA = new Town("A");
        Town townB = new Town("B");
        Town townC = new Town("C");
        Town townD = new Town("D");

        townA.getDesinationRouteMap().put(townB, 5);
        townB.getDesinationRouteMap().put(townC, 3);
        townC.getDesinationRouteMap().put(townD, 4);
        townA.getDesinationRouteMap().put(townD, 6);

        List<Town> townList = Arrays.asList(townA, townB, townC, townD);

        Assertions.assertThrows(Exception.class, () -> distanceAlongRoutes.calculateDistance(townList, "A-B-E"));
    }
}
