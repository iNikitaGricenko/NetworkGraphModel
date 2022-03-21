package com.wolfhack.networkmodel;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class Graph {

    private Map<Vertex, List<Vertex>> adjVertices = new LinkedHashMap<>();
    private static int count = 1;

    void addVertex(Vertex vertex) {
        vertex.setCode(count++);
        adjVertices.putIfAbsent(vertex, new LinkedList<>());
    }

    void addVertices(List<Vertex> vertices) {
        vertices.forEach(this::addVertex);
    }

    void removeVertex(String name) {
        Vertex vertex = new Vertex(name, 0);
        adjVertices.values().stream().forEach(vertices -> vertices.remove(vertex));
        adjVertices.remove(new Vertex(name, 0));
    }

    void removeVertices(List<Vertex> vertices) {
        vertices.forEach(vertex -> removeVertex(vertex.getName()));
    }

    void addEdge(Vertex vertex1, Vertex vertex2) {
        if (vertex1.getEarlierStart() >= vertex2.getEarlierStart()) {
            vertex2.setEarlierStart(vertex1.getEarlierEnding());
        }
        vertex2.setEarlierEnding(vertex2.getDuration() + vertex2.getEarlierStart());
        adjVertices.get(vertex1).add(vertex2);
        adjVertices.get(vertex2).add(vertex1);
    }

    void removeEdge(Vertex vertex1, Vertex vertex2) {
        List<Vertex> vertices1 = adjVertices.get(vertex1);
        List<Vertex> vertices2 = adjVertices.get(vertex2);
        if (vertices1 != null) {
            vertices1.remove(vertex2);
        }
        if (vertices2 != null) {
            vertices2.remove(vertex1);
        }
    }

    List<Vertex> getEdge(String name) {
        return adjVertices.get(new Vertex(name, 0));
    }

    public void calculate() {
        Set<Vertex> vertexSet = adjVertices.keySet();
        Object[] vertexArray = vertexSet.toArray();
        for (int i = vertexArray.length-1; i >= 0; i--) {
            Vertex vertex = (Vertex) vertexArray[i];
            calculateBottomRow(vertex);
        }
    }

    private void calculateBottomRow(Vertex lastVertex) {
        if (lastVertex.getName().equals("end")) {
            lastVertex.setLateStart(lastVertex.getEarlierEnding() - lastVertex.getDuration());
        }
        adjVertices.get(lastVertex).stream()
                .filter(vertex -> vertex.getCode() < lastVertex.getCode())
                .peek(vertex -> calculateTpo(vertex, lastVertex))
                .peek(this::calculateTpn)
                .forEach(this::calculateR);
    }

    private void calculateR(Vertex vertex) {
        vertex.setTimeReserve(vertex.getLateStart() - vertex.getEarlierStart());
    }

    private void calculateTpn(Vertex vertex) {
        vertex.setLateStart(vertex.getLateEnding() - vertex.getDuration());
    }

    private void calculateTpo(Vertex vertex, Vertex lastVertex) {
        if (lastVertex.getLateStart() < vertex.getLateEnding() || vertex.getLateEnding() == 0) {
            vertex.setLateEnding(lastVertex.getLateStart());
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Graph\n");
        adjVertices.entrySet()
                .stream()
                .forEach(vertexListEntry -> {
                    stringBuilder.append("Vertex = " + vertexListEntry.getKey() + "=>");
                    stringBuilder.append(vertexListEntry.getValue() + ";\n");
                });
        return stringBuilder.toString();
    }
}
