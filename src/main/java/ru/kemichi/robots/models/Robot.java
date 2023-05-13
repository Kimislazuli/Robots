package ru.kemichi.robots.models;

import ru.kemichi.robots.utility.DoublePoint;

import java.awt.*;
import java.util.Observable;

public class Robot extends Observable {
    private volatile DoublePoint robotPosition = new DoublePoint(100, 100);
    private volatile double robotDirection = 0;


    private volatile Point targetPosition = new Point(150, 100);

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    public DoublePoint getRobotPosition() {
        return robotPosition;
    }

    public double getRobotDirection() {
        return robotDirection;
    }

    public Point getTargetPosition() {
        return targetPosition;
    }

    private static double distance(Point target, DoublePoint robot) {
        double diffX = target.getX() - robot.getX();
        double diffY = target.getY() - robot.getY();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(Point target, DoublePoint robot) {
        double diffX = target.getX() - robot.getX();
        double diffY = target.getY() - robot.getY();

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public void setTargetPosition(Point p) {
        targetPosition = p;
    }

    public void onModelUpdateEvent() {
        double distance = distance(targetPosition, robotPosition);
        if (distance < 0.5) {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(targetPosition, robotPosition);
        double angularVelocity = 0;
        if (angleToTarget > robotDirection) {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < robotDirection) {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10);
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    private static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = robotPosition.getX() + velocity / angularVelocity *
                (Math.sin(robotDirection + angularVelocity * duration) -
                        Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotPosition.getX() + velocity * duration * Math.cos(robotDirection);
        }
        double newY = robotPosition.getY() - velocity / angularVelocity *
                (Math.cos(robotDirection + angularVelocity * duration) -
                        Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotPosition.getY() + velocity * duration * Math.sin(robotDirection);
        }
        robotPosition.setX(newX);
        robotPosition.setY(newY);
        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
    }
}
