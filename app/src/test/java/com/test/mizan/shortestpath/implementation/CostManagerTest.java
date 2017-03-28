package com.test.mizan.shortestpath.implementation;

import com.test.mizan.shortestpath.model.Coordinate;
import com.test.mizan.shortestpath.model.Graph;
import com.test.mizan.shortestpath.model.Output;
import com.test.mizan.shortestpath.model.PathInput;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.is;

/**
 * Created by mizanurrahmun on 3/28/17.
 */

public class CostManagerTest {

    private PathFinder pathFinder = Mockito.mock(PathFinder.class);
    private CostManager manager = new CostManager(pathFinder);

    @Test
    public void findPath_to_pathFinder_and_creates_correctResult_to_right_Path() {
        Graph graph = mock(Graph.class);
        doReturn(5).when(graph).getGraphWidth();

        List<PathInput> path = asList(
                new PathInput(new Coordinate(1, 1), 1),
                new PathInput(new Coordinate(2, 1), 2),
                new PathInput(new Coordinate(3, 1), 3),
                new PathInput(new Coordinate(4, 1), 4),
                new PathInput(new Coordinate(5, 1), 5)
        );

        doReturn(path).when(pathFinder).findShortestPath(graph);

        Output result = manager.result(graph);

        assertTrue(result.isCompleted());
        assertThat(result.getTotalCost(), is(15));
        assertThat(result.getPath(), is(asList(1,1,1,1,1)));
    }

    @Test
    public void findPath_to_False_when_widthOf_Path_not_equal_to_Graph() {
        Graph graph = mock(Graph.class);
        doReturn(5).when(graph).getGraphWidth();

        List<PathInput> path = asList(
                new PathInput(new Coordinate(1, 1), 1),
                new PathInput(new Coordinate(2, 1), 2),
                new PathInput(new Coordinate(3, 1), 3)
        );

        doReturn(path).when(pathFinder).findShortestPath(graph);

        Output result = manager.result(graph);

        assertFalse(result.isCompleted());
    }
}
