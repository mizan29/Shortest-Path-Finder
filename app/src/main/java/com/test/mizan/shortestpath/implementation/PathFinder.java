package com.test.mizan.shortestpath.implementation;

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

    public List<PathInput> findShortestPath(Graph graph) {
        List<PathInput> bestPath = null;
        for (int i = 0; i < graph.getGraphHeight(); i++) {
            List<PathInput> currentPath = findShortestPath(graph, new Coordinate(1, i + 1), new ArrayList<PathInput>());
            if (bestPath == null || totalCost(currentPath) < totalCost(bestPath))
                bestPath = currentPath;
        }
        return bestPath;
    }

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

        List<PathInput> up = findShortestPath(graph, graph.getDiagonalUpNode(coordinate), currentPath);
        List<PathInput> straight = findShortestPath(graph, graph.getRightNode(coordinate), currentPath);
        List<PathInput> down = findShortestPath(graph, graph.getDiagonalDownNode(coordinate), currentPath);

        List<PathInput> upOrStraight = compareTwoPath(up, straight);

        return compareTwoPath(upOrStraight, down);
    }

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
