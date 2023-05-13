package ru.kemichi.robots.utility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoublePoint {
    private double x;
    private double y;

    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
