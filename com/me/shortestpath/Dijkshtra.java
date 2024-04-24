package com.me.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import com.me.path.RoadNetwork;

public class Dijkshtra {
    private RoadNetwork roadNetwork;

    public Dijkshtra(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    static class Vertex implements Comparable<Vertex> {
        int vertex;
        int cost;

        Vertex(int vertex, int cost) {
            this.vertex = vertex;
            this.cost = cost;
        }

        @Override
        public int compareTo(Vertex other) {
            return Integer.compare(this.cost, other.cost);
        }
    }
    // Method to modify routes based on traffic conditions
    public int adjustDistanceForTraffic(int originalDistance, int trafficCount, int capacity) {
        if (trafficCount >= capacity) {
            return (int) (originalDistance * 1.2);
        } else {
            return originalDistance;
        }
    }

    public List<Integer> dijkstra(int src, int target) {
        int length=roadNetwork.adj.length;
        PriorityQueue<Vertex> sp = new PriorityQueue<>();
        int[] distances = new int[length];
        int[] previous = new int[length];
        boolean[] visited = new boolean[length];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);

        distances[src] = 0;
        sp.add(new Vertex(src, 0));

        while (!sp.isEmpty()) {
            Vertex current = sp.poll();

            if (current.vertex == target) {
                return getPath(previous, target);
            }

            if (!visited[current.vertex]) {
                visited[current.vertex] = true;

                for (RoadNetwork.Path edge : roadNetwork.adj[current.vertex]) {
                if (!visited[edge.getDest()] && distances[current.vertex] + edge.getDist() < distances[edge.getDest()]) {
                        distances[edge.getDest()] = distances[current.vertex] + edge.getDist();
                        previous[edge.getDest()] = current.vertex;
                        sp.add(new Vertex(edge.getDest(), distances[edge.getDest()]));
                    }
                }
            }
        }

        return new ArrayList<Integer>();
    }


    private List<Integer> getPath(int[] previous, int target) {
        List<Integer> path = new ArrayList<>();
        int at = target;
        while (at != -1) {
            path.add(at);
            at = previous[at];
        }
        Collections.reverse(path);
        return path;
    }

    // Method to calculate the total maintenance cost of the path
    public double calculateMaintenanceCost(List<Integer> path) {
        double totalCost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            for (RoadNetwork.Path edge : roadNetwork.adj[path.get(i)]) {
                if (edge.getDest() == path.get(i + 1)) {
                    System.out.println("Adding maintenance cost for edge: " + path.get(i) + " - " + path.get(i + 1) + " - " + edge.getMaintenanceCost());
                    //System.out.println(path.get(i+1));
                    totalCost += edge.getMaintenanceCost();
                    break;
                }
            }
        }
        return totalCost;
    }
}
