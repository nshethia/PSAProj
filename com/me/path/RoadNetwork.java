package com.me.path;
import java.util.LinkedList;

public class RoadNetwork{
    public LinkedList<Path>[] adj;
    
   public class Path{
       private int dest;
       private int dist;
       private int capacity;
       private int dailyTrafficCount;
       private double maintenanceCost;

       public Path(int dest, int dist, int capacity, int dailyTrafficCount, double maintenanceCost) {
            this.dest = dest;
            this.dist = dist;
            this.capacity = capacity;
            this.dailyTrafficCount = dailyTrafficCount;
            this.maintenanceCost = maintenanceCost;
        }

        public int getDest() {
            return dest;
        }

        public int getDist() {
            return dist;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getDailyTrafficCount() {
            return dailyTrafficCount;
        }

        public void setDest(int dest) {
            this.dest = dest;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        public void setDailyTrafficCount(int dailyTrafficCount) {
            this.dailyTrafficCount = dailyTrafficCount;
        }

        public void setMaintenanceCost(double maintenanceCost) {
            this.maintenanceCost = maintenanceCost;
        }

        public double getMaintenanceCost() {
            return maintenanceCost;
        }
        public void setDist(int newDist) {
            this.dist = newDist;
        }
    }

 
    @SuppressWarnings("unchecked")
    public RoadNetwork(int points){
        adj = (LinkedList<Path>[]) new LinkedList<?>[points]; 
        for(int i=0; i<points; i++){
            adj[i] = new LinkedList<Path>(); 
        }
    }
    
    public void addPath(int start, int end, int dist, int capacity, int dailyTrafficCount, double maintenanceCost) {
        if (start >= adj.length || end >= adj.length || start < 0 || end < 0) {
            throw new IllegalArgumentException("vertex index out of bounds.");
        }
        adj[start].add(new Path(end, dist, capacity, dailyTrafficCount, maintenanceCost)); 
        adj[end].add(new Path(start, dist, capacity, dailyTrafficCount, maintenanceCost));  
    }
}
