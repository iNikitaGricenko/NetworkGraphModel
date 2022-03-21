package com.wolfhack.networkmodel;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter
public class Vertex {

    private String name; // code (name) of work
    private int duration; // t(i,j) Duration of work

    private int timeReserve; // R(i,j)

    private int code;

    private int earlierStart; // Tрн(i,j)
    private int earlierEnding; // Tро(i,j)
    private int lateStart; // Tпн(i,j)
    private int lateEnding; // Tпо(i,j)

    public Vertex(String name, int duration) {
        this.name = name;
        this.duration = duration;
        earlierEnding = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s{ Tрн=%d, t=%d, Tро=%d, Tпн=%d, R=%d, Tпo=%d }",
                name, earlierStart, duration, earlierEnding, lateStart, timeReserve, lateEnding);
    }
}
