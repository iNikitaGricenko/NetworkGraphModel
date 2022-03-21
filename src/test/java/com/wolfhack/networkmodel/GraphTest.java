package com.wolfhack.networkmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GraphTest {

    private final Graph graph = new Graph();

    private final Vertex start = new Vertex("start", 0);
    private final Vertex workF = new Vertex("F", 4);
    private final Vertex workB = new Vertex("B", 3);
    private final Vertex workE = new Vertex("E", 8);
    private final Vertex workA = new Vertex("A", 9);
    private final Vertex workD = new Vertex("D", 6);
    private final Vertex workC = new Vertex("C", 12);
    private final Vertex workG = new Vertex("G", 7);
    private final Vertex workJ = new Vertex("J", 12);
    private final Vertex workH = new Vertex("H", 10);
    private final Vertex workI = new Vertex("I", 7);
    private final Vertex end = new Vertex("end", 0);

    @Before
    public void setUp() throws Exception {
        graph.addVertex(start);
        graph.addVertex(workF);
        graph.addVertex(workB);
        graph.addVertex(workE);
        graph.addVertex(workA);
        graph.addVertex(workD);
        graph.addVertex(workC);
        graph.addVertex(workG);
        graph.addVertex(workJ);
        graph.addVertex(workH);
        graph.addVertex(workI);
        graph.addVertex(end);

        graph.addEdge(workF, workE);
        graph.addEdge(workB, workA);
        graph.addEdge(workE, workD);
        graph.addEdge(workE, workJ);
        graph.addEdge(workA, workG);
        graph.addEdge(workD, workH);
        graph.addEdge(workC, workI);
        graph.addEdge(workJ, workI);
        graph.addEdge(workG, workI);
        graph.addEdge(workI, end);
        graph.addEdge(workH, end);

        graph.calculate();
    }

    @Test
    public void checkForCorrectEdgeI() {

        Vertex end = new Vertex("end", 0);
        Vertex g = new Vertex("G", 7);
        Vertex j = new Vertex("J", 12);
        Vertex c = new Vertex("C", 12);

        end.setEarlierStart(31); // Tрн (і,j)
        end.setEarlierEnding(31); // Tро (і,j)
        end.setLateStart(31); // Tпн (і,j)

        g.setEarlierStart(12); // Tрн (і,j)
        g.setEarlierEnding(19); // Tро (і,j)
        g.setLateStart(17); // Tпн (і,j)
        g.setLateEnding(24); // Tпо (і,j)
        g.setTimeReserve(24); // R (і,j)

        j.setEarlierStart(12); // Tрн (і,j)
        j.setEarlierEnding(24); // Tро (і,j)
        j.setLateStart(12); // Tпн (і,j)
        j.setLateEnding(24); // Tпo (і,j)
        j.setTimeReserve(0); // R (і,j)

        c.setEarlierStart(0); // Tрн (і,j)
        c.setEarlierEnding(12); // Tро (і,j)
        c.setLateStart(12); // Tпн (і,j)
        c.setLateEnding(24); // Tпo (і,j)
        c.setTimeReserve(12); // R (і,j)

        List<Vertex> vertices = List.of(c, j, g, end);

        assertEquals(graph.getEdge("I"), vertices);
    }

    @Test
    public void checkForCorrectEdgeE() {

        Vertex f = new Vertex("F", 4);
        Vertex d = new Vertex("D", 6);
        Vertex j = new Vertex("J", 12);


        f.setEarlierStart(0); // Tрн (і,j)
        f.setEarlierEnding(4); // Tро (і,j)
        f.setLateStart(3); // Tпн (і,j)
        f.setLateEnding(7); // Tпo (і,j)
        f.setTimeReserve(3); // R (і,j)

        d.setEarlierStart(12); // Tрн (і,j)
        d.setEarlierEnding(18); // Tро (і,j)
        d.setLateStart(15); // Tпн (і,j)
        d.setLateEnding(21); // Tпo (і,j)
        d.setTimeReserve(3); // R (і,j)

        j.setEarlierStart(12); // Tрн (і,j)
        j.setEarlierEnding(24); // Tро (і,j)
        j.setLateStart(12); // Tпн (і,j)
        j.setLateEnding(24); // Tпo (і,j)
        j.setTimeReserve(0); // R (і,j)

        List<Vertex> vertices = List.of(f, d, j);

        assertEquals(graph.getEdge("E"), vertices);
    }

    @Test
    public void checkForCorrectEdgeA() {

        Vertex g = new Vertex("G", 4);
        Vertex b = new Vertex("B", 6);

        b.setEarlierStart(0); // Tрн (і,j)
        b.setEarlierEnding(3); // Tро (і,j)
        b.setLateStart(5); // Tпн (і,j)
        b.setLateEnding(8); // Tпo (і,j)
        b.setTimeReserve(5); // R (і,j)

        g.setEarlierStart(12); // Tрн (і,j)
        g.setEarlierEnding(19); // Tро (і,j)
        g.setLateStart(17); // Tпн (і,j)
        g.setLateEnding(5); // Tпo (і,j)
        g.setTimeReserve(24); // R (і,j)

        List<Vertex> vertices = List.of(b, g);

        assertEquals(graph.getEdge("A"), vertices);
    }

    @After
    public void print() {
        System.out.println(graph);
    }
}