package com.me.trafficflow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import com.me.path.RoadNetwork;
public class FordFulkerson {
    private int[][] residualGraph;
    private RoadNetwork roadNetwork;
    private int[] parent;
    public FordFulkerson(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    public void MaxFlow(int source, int sink) {
        int n = roadNetwork.adj.length;
        residualGraph = new int[n][n];

        // Initialize residual graph
        for (int i = 0; i < n; i++) {
            for (RoadNetwork.Path path : roadNetwork.adj[i]) {
                int dest = path.getDest();
                int capacity = path.getCapacity();
                int trafficCount = path.getDailyTrafficCount();
                residualGraph[i][dest] = capacity - trafficCount;
            }
        }
        System.out.println("Residual Graph (Before Ford-Fulkerson):");
        printResidualGraph();
        int maxFlow = mFlow(source, sink);
        // System.out.println("Maximum Flowwwww: " + maxFlow);
        System.out.println("Residual Graph (After Ford-Fulkerson):");
        printResidualGraph();
     
    }

    public int mFlow(int source, int sink) {
        int flow = 0;
        System.out.println("Residual Graph (Before Flow Augmentation):");
        printResidualGraph();
        while (bfs(source, sink)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }
            System.out.println("Path Flow: " + pathFlow); 
            if (pathFlow == Integer.MAX_VALUE) {
                break;
            }
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }
            flow += pathFlow;
        System.out.println("Residual Graph (After Flow Augmentation):");
        printResidualGraph();

        
        }

        return flow;
    }
    public boolean bfs(int source, int sink) {
        int n = roadNetwork.adj.length;
        boolean[] visited = new boolean[n];
        parent = new int[n];
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        visited[source] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            // System.out.println("asdcascdasdc"+roadNetwork.adj[u]);
            for (RoadNetwork.Path path : roadNetwork.adj[u]) {
                int v = path.getDest();
    
                if (!visited[v] && residualGraph[u][v] > 0) {
                    parent[v] = u;
                    visited[v] = true;
                    queue.offer(v);
                }
            }
        }
      
        return visited[sink];
    }
    public void printResidualGraph() {
        for (int i = 0; i < residualGraph.length; i++) {
            for (int j = 0; j < residualGraph[i].length; j++) {
                System.out.print(residualGraph[i][j] + "\t");
            }
            System.out.println();
        }
    }
    
  

    // Function to optimize traffic flow based on residual network
    public void optimizeTrafficFlow() {
        for (int i = 0; i < residualGraph.length; i++) {
            for (int j = 0; j < residualGraph[i].length; j++) {
                if (j < roadNetwork.adj[i].size() && residualGraph[i][j] > 0){
                    // Calculate traffic flow
                    int trafficFlow = roadNetwork.adj[i].get(j).getCapacity() - residualGraph[i][j];

                    // optimize traffic flow
                    if (trafficFlow < roadNetwork.adj[i].get(j).getDailyTrafficCount()) {

                    System.out.println("Optimizing traffic flow on road from " + i + " to " + j);
                }
            }
        }
    }
    }
}
