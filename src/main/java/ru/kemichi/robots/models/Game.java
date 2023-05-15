package ru.kemichi.robots.models;

import lombok.Getter;
import lombok.Setter;
import ru.kemichi.robots.utility.data_classes.DoublePoint;
import ru.kemichi.robots.utility.functional_methods.GeometryKeeper;

import java.awt.*;
import java.util.Observable;

@Getter
@Setter
public class Game extends Observable {
    private final DoublePoint robotPosition = new DoublePoint(100, 100);
    private volatile double robotDirection = 0;
    private double angle = 0;
    private volatile Point targetPosition = new Point(150, 100);

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    public void setTargetPosition(Point p) {
        targetPosition = p;
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public void onModelUpdateEvent(Dimension windowDimension) {
        double distance = GeometryKeeper.distance(targetPosition, robotPosition);
        if (distance < 0.5) {
            setChanged();
            notifyObservers();
            clearChanged();
            return;
        }
        double angleToTarget = GeometryKeeper.angleTo(targetPosition, robotPosition);
        angle = GeometryKeeper.asNormalizedRadians(angleToTarget - robotDirection);
        double angularVelocity = GeometryKeeper.calculateAngularVelocity(angleToTarget, robotDirection, maxAngularVelocity);

        moveRobot(maxVelocity, angularVelocity, windowDimension);
    }

    private static double applyLimits(double value, double max) {
        if (value < 0)
            return 0;
        return Math.min(value, max);
    }

    private void moveRobot(double velocity, double angularVelocity, Dimension windowDimension) {
        velocity = applyLimits(velocity, maxVelocity);
        robotDirection = GeometryKeeper.asNormalizedRadians(robotDirection + Math.min(angularVelocity, angle) * 10);
        robotPosition.setX(robotPosition.getX() + velocity * 10 * Math.cos(robotDirection));
        robotPosition.setY(robotPosition.getY() + velocity * 10 * Math.sin(robotDirection));

        if (windowDimension.width != 0) {
            robotPosition.setX(applyLimits(robotPosition.getX(), windowDimension.width));
        }
        if (windowDimension.height != 0) {
            robotPosition.setY(applyLimits(robotPosition.getY(), windowDimension.height));
        }

        if (robotPosition.getX() == 0) {
            robotPosition.setX(windowDimension.width);
        }
        else if (robotPosition.getX()== windowDimension.width) {
            robotPosition.setX(0);
        }

        if (robotPosition.getY() == 0) {
            robotPosition.setY(windowDimension.height);
        }
        else if (robotPosition.getY()== windowDimension.height) {
            robotPosition.setY(0);
        }

        setChanged();
        notifyObservers();
        clearChanged();
    }
}