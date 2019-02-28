package com.training.train;


import com.training.train.Repository.DistanceAlongRoutes;
import com.training.train.Repository.ShortestPathRoute;
import com.training.train.Repository.TotalRoutesBetweenTown;
import com.training.train.exception.CustomException;
import com.training.train.exception.RouteException;
import com.training.train.exception.TownException;
import com.training.train.model.Town;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings("ALL")
public class Train {
    public static void main(String args[]) {

        Train train = new Train();
        List<Town> townList;

        try {
            townList = train.fileToTowns();
            townList.sort((o1, o2) -> {
                if (o1.getTownName().equalsIgnoreCase(o2.getTownName())) {
                    return 0;
                } else if (o1.getTownName().compareTo(o2.getTownName()) > 0) {
                    return 1;
                } else {
                    return -1;
                }
            });
            interactiveScreen(townList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void interactiveScreen(List<Town> townList) {
        System.out.println("Welcome to Trains \nTowns available are as follows:");
        townList.forEach(System.out::print);
        System.out.println();

        while (true) {
            System.out.println("Please select an option id below");
            System.out.println("1. Distance between 2 towns");
            System.out.println("2. Total routes between 2 towns");
            System.out.println("3. Shortest path distance between town towns");
            System.out.println("4. To quit");

            Scanner scanner = new Scanner(System.in);

            switch (scanner.nextInt()) {
                case 1:
                    System.out.println(
                            "Please provide the towns for which you need to know the distance in \"A-B\" format"
                    );
                    DistanceAlongRoutes distanceAlongRoutes = new DistanceAlongRoutes();
                    try {
                        System.out.println(distanceAlongRoutes.calculateDistance(townList, scanner.next()));
                    } catch (RouteException e) {
                        System.out.println(e.getMessage());
                    } catch (TownException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println(
                            "Please provide the towns for which you need to know the distance in \"A-B\" format"
                    );
                    TotalRoutesBetweenTown totalRoutesBetweenTown = new TotalRoutesBetweenTown();
                    try {
                        System.out.println(totalRoutesBetweenTown.fetchTotalRoutes(townList, scanner.next()));
                    } catch (TownException e) {
                        e.getMessage();
                    }
                    break;
                case 3:
                    System.out.println(
                            "Please provide the towns for which you need to know the distance in \"A-B\" format"
                    );
                    ShortestPathRoute shortestPathRoute = new ShortestPathRoute();
                    try {
                        System.out.println(shortestPathRoute.findShortestPath(townList, scanner.next()));
                    } catch (TownException | RouteException e) {
                        e.getMessage();
                    }
                    break;
                case 4:
                    return;

                default:
                    break;
            }
        }

    }

    private List<Town> fileToTowns() throws IOException, CustomException {
        File file;
        try {
            file = new File(
                    getClass().getClassLoader().getResource(
                            "input/TownsWithDistance"
                    )
                            .getFile()
            );
        } catch(Exception e) {
            throw new CustomException("File Not found" + e.getMessage());
        }

        List<String> lines = Files.readAllLines(
                Paths.get(file.getAbsolutePath())
        );

        Map<String, Town> townMap = new HashMap<>();
        List<Town> townList = new ArrayList<>();

        for (String line : lines) {
            Town townS;
            Town townD;
            String[] townsWithDistanceArray = line.split("");
            String source = townsWithDistanceArray[0];
            String destination = townsWithDistanceArray[1];

            if (!townMap.containsKey(source)) {
                townS = new Town(source);
                townMap.put(source, townS);
                townList.add(townS);
            } else {
                townS = townMap.get(source);
            }
            if (!townMap.containsKey(destination)) {
                townD = new Town(destination);
                townMap.put(destination, townD);
                townList.add(townD);
            } else {
                townD = townMap.get(destination);
            }

            townS.getDesinationRouteMap().put(townD, Integer.valueOf(townsWithDistanceArray[2]));
        }

        return townList;
    }

}
