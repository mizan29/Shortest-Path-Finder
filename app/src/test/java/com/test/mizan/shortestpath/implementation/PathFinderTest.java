package com.test.mizan.shortestpath.implementation;

import com.test.mizan.shortestpath.model.Coordinate;
import com.test.mizan.shortestpath.model.Graph;
import com.test.mizan.shortestpath.model.PathInput;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by mizanurrahmun on 3/28/17.
 */

public class PathFinderTest {


    PathFinder finder = new PathFinder(50);


    @Test
    public void constructor_setsMaxCost_when_valueIsPassed() {
        assertEquals(50, new PathFinder(50).getMaxCost());
    }

    @Test
    public void findShortestPath_given_1x1_graph_returnsCorrectPath() {
        Graph graph = new Graph(singletonList(singletonList(1)));

        List<PathInput> inputs = singletonList(
                new PathInput(new Coordinate(1, 1), 1)
        );

        assertListsAreEqual(finder.findShortestPath(graph), inputs);
    }


    @Test
    public void findShortestPath_given_5x6_graph_returnsCorrectPath() {
        Graph graph = new Graph(
                asList(
                        asList(3, 4, 1, 2, 8, 6),
                        asList(6, 1, 8, 2, 7, 4),
                        asList(5, 9, 3, 9, 9, 5),
                        asList(8, 4, 1, 3, 2, 6),
                        asList(3, 7, 2, 8, 6, 4)
                )
        );

        List<PathInput> inputs = asList(
                new PathInput(new Coordinate(1, 1), 3),
                new PathInput(new Coordinate(2, 2), 1),
                new PathInput(new Coordinate(3, 3), 3),
                new PathInput(new Coordinate(4, 4), 3),
                new PathInput(new Coordinate(5, 4), 2),
                new PathInput(new Coordinate(6, 5), 4)
        );

        assertListsAreEqual(finder.findShortestPath(graph), inputs);
    }



    @Test
    public void findShortestPath_given_3x5_graph_returnsCorrectPath() {
        Graph graph = new Graph(
                asList(
                        asList(19,10,19,10,19),
                        asList(21,23,20,19,12),
                        asList(20,12,20,11,10)
                )
        );

        List<PathInput> inputs = asList(
                new PathInput(new Coordinate(1, 1), 19),
                new PathInput(new Coordinate(2, 1), 10),
                new PathInput(new Coordinate(3, 1), 19)
        );

        assertListsAreEqual(finder.findShortestPath(graph), inputs);
    }

    private void assertListsAreEqual(List<PathInput> inputs1, List<PathInput> inputs2) {
        assertThat(inputs1.size(), Matchers.is(inputs2.size()));

        for (int i = 0; i < inputs1.size(); i++) {
            assertThat(inputs1.get(i), Matchers.is(inputs2.get(i)));
        }
    }

}
