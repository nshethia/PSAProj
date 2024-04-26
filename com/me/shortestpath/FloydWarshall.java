// Time complexity : prioritizeRoadsForMaintenance() =  O(V+E+nlogn)
//                   calculateTrafficCongestion() = O(V+E)


package com.me.shortestpath;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.me.path.RoadNetwork;

public class FloydWarshall {
    private RoadNetwork roadNetwork;

    public FloydWarshall(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }
    
    public void calculateTrafficCongestion() {
        int n = roadNetwork.adj.length;
        double[][] congestionLevels = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    congestionLevels[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (RoadNetwork.Path path : roadNetwork.adj[i]) {
                int dest = path.getDest();
                int trafficCount = path.getDailyTrafficCount();
                int capacity = path.getCapacity();

                // Calculate congestion level based on traffic count and capacity
                if (trafficCount > capacity) {
                    congestionLevels[i][dest] = (double) trafficCount / capacity; 
                } else {
                    congestionLevels[i][dest] = 0;
                }
                
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (congestionLevels[i][j] == Double.POSITIVE_INFINITY) {
                    System.out.print("Inf\t");
                } else {
                    System.out.print(String.format("%.2f\t", congestionLevels[i][j]));
                }
            }
            System.out.println();
        }

    }
public void prioritizeRoadsForMaintenance(double threshold) {
    List<RoadPriorityInfo> prioritizedRoads = new ArrayList<>();

    for (int i = 0; i < roadNetwork.adj.length; i++) {
        for (RoadNetwork.Path path : roadNetwork.adj[i]) {
            double maintenanceCost = path.getMaintenanceCost();
            int trafficVolume = path.getDailyTrafficCount();
            CongestionLevel congestionLevel = getCongestionLevel(trafficVolume);

            if (maintenanceCost > threshold || congestionLevel == CongestionLevel.HIGH) {
                prioritizedRoads.add(new RoadPriorityInfo(i, path.getDest(), maintenanceCost, trafficVolume, congestionLevel));
            }
        }
    }

    Collections.sort(prioritizedRoads);
    printPrioritizedRoads(prioritizedRoads);
}

private CongestionLevel getCongestionLevel(int trafficVolume) {
    if (trafficVolume < 2500) {
        return CongestionLevel.LOW;
    } else if (trafficVolume < 5000) {
        return CongestionLevel.MEDIUM;
    } else {
        return CongestionLevel.HIGH;
    }
}

private void printPrioritizedRoads(List<RoadPriorityInfo> prioritizedRoads) {
    for (RoadPriorityInfo road : prioritizedRoads) {
        System.out.println("Road from " + road.start + " to " + road.end + " - Maintenance Cost: " + road.maintenanceCost +
", Traffic Volume: " + road.trafficVolume +  ", Congestion Level: " + road.congestionLevel);
    }
}

public enum CongestionLevel {
    LOW, MEDIUM, HIGH
}

public class RoadPriorityInfo implements Comparable<RoadPriorityInfo> {
    public int start;
    public int end;
    public double maintenanceCost;
    public int trafficVolume;
    public CongestionLevel congestionLevel;

    public RoadPriorityInfo(int start, int end, double maintenanceCost, int trafficVolume, CongestionLevel congestionLevel) {
        this.start = start;
        this.end = end;
        this.maintenanceCost = maintenanceCost;
        this.trafficVolume = trafficVolume;
        this.congestionLevel = congestionLevel;
    }

    @Override
    public int compareTo(RoadPriorityInfo other) {
        //  priority: first by congestion level, then by maintenance cost
        int levelComparison =other.congestionLevel.compareTo(this.congestionLevel);
        if (levelComparison != 0) return levelComparison;
        return Double.compare(this.maintenanceCost, other.maintenanceCost);
    }
}
    }

