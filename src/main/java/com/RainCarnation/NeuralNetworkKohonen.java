package com.RainCarnation;

import com.RainCarnation.service.Measurable;

import java.io.OutputStream;

public class NeuralNetworkKohonen<T extends Measurable, Cluster extends Measurable> {
    private Cluster[] clusters;
    public void fit(T[] matrix, Cluster... result) throws Exception {
        if (matrix.length == 0 || result.length == 0) {
            throw new NeuralException("Input data is clear");
        }
        clusters = result;
    }


    public Cluster getResult(T input) {
        double distance;
        double distanceMin = Integer.MAX_VALUE;
        Cluster currentCluster = null;
        for (Cluster cluster : clusters) {
           distance = distance(cluster.getX(), cluster.getY(), input.getX(), input.getY());
           if (distance < distanceMin) {
               currentCluster = cluster;
               distanceMin = distance;
           }
        }
        return currentCluster;
    }


    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
