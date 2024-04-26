package com.me.main;

import java.util.List;

import com.me.path.RoadNetwork;
import com.me.shortestpath.Dijkshtra;
import com.me.shortestpath.FloydWarshall;

public class Main{
    public static void main(String[] args){
        RoadNetwork road = new RoadNetwork(9);
        road.addPath(0, 1, 14, 2000, 2500, 1000.00);
        road.addPath(0, 7, 18, 2500, 1800, 1500.00);
        road.addPath(1, 2, 55, 3000, 5400, 3000.00);
        road.addPath(1, 7, 59, 3800, 3000, 2700.00);
        road.addPath(2, 3, 72, 5200, 2600, 3500.00);
        road.addPath(2, 8, 20, 7000, 7900, 1200.00);
        road.addPath(2, 5, 44, 6100, 9000, 2000.00);
        road.addPath(3, 4, 50, 9300, 9500, 2500.00);
        road.addPath(3, 5, 30, 3400, 9700, 2200.00);
        road.addPath(4, 5, 50, 3500, 3900, 2600.00);
        road.addPath(5, 6, 75, 3600, 3000, 2800.00);
        road.addPath(6, 7, 8, 3000, 2100, 2900.00);
        road.addPath(6, 8, 60, 3000, 3200, 3000.00);
        road.addPath(7, 8, 9, 3900, 4300, 3100.00);
        
        
System.out.println("---------------------");    
System.out.println("Scenario 1: Traffic flow exceed capacity");
Dijkshtra dijkshtra = new Dijkshtra(road);
System.out.println("take another route : " + dijkshtra.adjustDistanceForTraffic(14, 3500, 2000));


System.out.println("---------------------");
System.out.println("Scenario 2: Traffic flow doesn't exceed capacity");
RoadNetwork road2 = new RoadNetwork(9);
road2.addPath(0, 1, 14, 2000, 1500, 1000.00);
road2.addPath(0, 7, 18, 2500, 1800, 1500.00);
Dijkshtra dijkshtra2 = new Dijkshtra(road2);
List<Integer> adjustedPath2 = dijkshtra2.dijkstra(1, 7);
System.out.println("Adjusted Shortest Path (Scenario 2): " + adjustedPath2);
double maintenanceCost2 = dijkshtra2.calculateMaintenanceCost(adjustedPath2);
System.out.println("Total Maintenance Cost for the Path (Scenario 2): " + maintenanceCost2);
System.out.println("---------------------");

FloydWarshall floydWarshall = new FloydWarshall(road);
 // Calculate traffic congestion levels
 System.out.println("Calculating traffic congestion levels:");
 floydWarshall.calculateTrafficCongestion();

 //priortizing roads based on maintenance cost
System.out.println("priortizing roads based on maintenance cost");
floydWarshall.prioritizeRoadsForMaintenance(1000);

}
 }
