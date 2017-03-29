package com.test.mizan.shortestpath.implementation;

import android.util.Log;

import com.test.mizan.shortestpath.model.Coordinate;
import com.test.mizan.shortestpath.model.Graph;
import com.test.mizan.shortestpath.model.PathInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mizanurrahmun on 3/28/17.
 */

public class PathFinder {

    private final int maxCost;


    public PathFinder(int maxCost) {
        this.maxCost = maxCost;
    }

    // This method takes Graph as input and provides shortest path as output
    public List<PathInput> findShortestPath(Graph graph) {
        List<PathInput> bestPath = null;
        for (int i = 0; i < graph.getGraphHeight(); i++) {
            List<PathInput> currentPath = findShortestPath(graph, new Coordinate(1, i + 1), new ArrayList<PathInput>());

            if (bestPath == null || totalCost(currentPath) < totalCost(bestPath))
                bestPath = currentPath;
        }
        return bestPath;
    }

    //Overloading method findShortestPath with input Graph, coordinate and input path
    private List<PathInput> findShortestPath(Graph graph, Coordinate coordinate, List<PathInput> inputs) {
        if (coordinate == null) {
            return inputs;
        }
        List<PathInput> currentPath = new ArrayList<>(inputs);
        int nextCost = graph.getCost(coordinate);
        if (totalCost(currentPath) + nextCost > maxCost || coordinate.getX() > graph.getGraphWidth()) {
            return currentPath;
        }
        currentPath.add(new PathInput(coordinate, nextCost));

        //I started using this upCalculation method to replace the recursion to find out variable up with the following syntax
        // and I verified this work for input 5 8 5 3 5
        // I think if I spend more time on it, I should be able to replace the next two recursion
        // List<PathInput> up =upCalculation(graph, coordinate, currentPath);

        List<PathInput> up = findShortestPath(graph, graph.getDiagonalUpNode(coordinate), currentPath);
        List<PathInput> straight = findShortestPath(graph, graph.getRightNode(coordinate), currentPath);
        List<PathInput> down = findShortestPath(graph, graph.getDiagonalDownNode(coordinate), currentPath);

        List<PathInput> upOrStraight = compareTwoPath(up, straight);

        return compareTwoPath(upOrStraight, down);
    }

    private List<PathInput> upCalculation(Graph graph, Coordinate coordinate, List<PathInput> currentPath){

        List<PathInput> up = findUp(graph, coordinate, currentPath);
        List<PathInput> straight = findStraight(graph, coordinate, currentPath);
        List<PathInput> down = findDown(graph, coordinate, currentPath);
        List<PathInput> upOrStraight = compareTwoPath(up, straight);
        return compareTwoPath(upOrStraight, down);
    }

    private List<PathInput> findUp(Graph graph, Coordinate coordinate, List<PathInput> currentPath){


        List<PathInput> up = null;
        Coordinate newCoordinateUp=graph.getDiagonalUpNode(coordinate);
        boolean isDone=false;
        while(!isDone){
            if (newCoordinateUp == null) {
                up= new ArrayList<>(currentPath);
                isDone=true;
            }
            List<PathInput> currentPathUp = null;
            int nextCostUp = 0;
            if(!isDone) {
                currentPathUp = new ArrayList<>(currentPath);
                nextCostUp = graph.getCost(newCoordinateUp);
                if (totalCost(currentPathUp) + nextCostUp > maxCost || newCoordinateUp.getX() > graph.getGraphWidth()) {
                    up = new ArrayList<>(currentPathUp);
                    isDone = true;
                }
            }
            if(!isDone){
                currentPathUp.add(new PathInput(newCoordinateUp, nextCostUp));
                currentPath=new ArrayList<>(currentPathUp);
                newCoordinateUp=graph.getDiagonalUpNode(newCoordinateUp);
            }

        }
        return up;

    }

    private List<PathInput> findStraight(Graph graph, Coordinate coordinate, List<PathInput> currentPath){

        List<PathInput> straight = null;
        Coordinate newCoordinateUp=graph.getRightNode(coordinate);
        boolean isDone=false;
        while(!isDone){
            if (newCoordinateUp == null) {
                straight= new ArrayList<>(currentPath);
                isDone=true;
            }
            List<PathInput> currentPathUp = null;
            int nextCostUp = 0;
            if(!isDone) {
                currentPathUp = new ArrayList<>(currentPath);
                nextCostUp = graph.getCost(newCoordinateUp);
                if (totalCost(currentPathUp) + nextCostUp > maxCost || newCoordinateUp.getX() > graph.getGraphWidth()) {
                    straight = new ArrayList<>(currentPathUp);
                    isDone = true;
                }
            }
            if(!isDone){
                currentPathUp.add(new PathInput(newCoordinateUp, nextCostUp));
                currentPath=new ArrayList<>(currentPathUp);
                newCoordinateUp=graph.getDiagonalUpNode(newCoordinateUp);
            }

        }

        return straight;

    }

    private List<PathInput> findDown(Graph graph, Coordinate coordinate, List<PathInput> currentPath){


        List<PathInput> down = null;
        Coordinate newCoordinateUp=graph.getDiagonalDownNode(coordinate);
        boolean isDone=false;
        while(!isDone){
            if (newCoordinateUp == null) {
                down= new ArrayList<>(currentPath);
                isDone=true;
            }
            List<PathInput> currentPathUp = null;
            int nextCostUp = 0;
            if(!isDone) {
                currentPathUp = new ArrayList<>(currentPath);
                nextCostUp = graph.getCost(newCoordinateUp);
                if (totalCost(currentPathUp) + nextCostUp > maxCost || newCoordinateUp.getX() > graph.getGraphWidth()) {
                    down = new ArrayList<>(currentPathUp);
                    isDone = true;
                }
            }
            if(!isDone){
                currentPathUp.add(new PathInput(newCoordinateUp, nextCostUp));
                currentPath=new ArrayList<>(currentPathUp);
                newCoordinateUp=graph.getDiagonalUpNode(newCoordinateUp);
            }

        }
        return down;

    }

    //Compare two paths and return the shortest path
    private List<PathInput> compareTwoPath(List<PathInput> path1, List<PathInput> path2) {

        if (path1.size() < path2.size())
            return path2;

        if (path1.size() == path2.size()) {
            if (totalCost(path1) > totalCost(path2))
                return path2;

            return path1;
        }

        return path1;
    }

    // Total cost from an Input Path
    private int totalCost(List<PathInput> paths) {
        int total = 0;
        for (PathInput path : paths) {
            total += path.getValue();
        }
        return total;
    }

    public int getMaxCost() {
        return maxCost;
    }
}
