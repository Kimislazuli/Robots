package ru.kemichi.robots.models;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Robot {
    private final Timer m_timer = initTimer();

    private static Timer initTimer() {
        return new Timer("events generator", true);
    }
    private volatile Point robotPosition = new Point(100, 100);


    private volatile double robotDirection = 0;

    private volatile Point targetPosition = new Point(150, 100);


    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

//    ArrayList<Controller> controllers = new ArrayList<>();
//    ArrayList<View> views = new ArrayList<View>();

    public Robot() {
//        views.add(new GameView());
//        controllers.add(new GameWindowController());

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onModelUpdateEvent();
            }
        }, 0, 10);
    }

    public Point getRobotPosition() {
        return robotPosition;
    }

    public double getRobotDirection() {
        return robotDirection;
    }

    public Point getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Point p) {
        targetPosition = p;
    }

    private static double distance(Point target, Point robot) {
        double diffX = target.x - robot.x;
        double diffY = target.y - robot.y;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(Point target, Point robot) {
        double diffX = target.x - robot.x;
        double diffY = target.y - robot.y;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public void onModelUpdateEvent() {
        double distance = distance(targetPosition, robotPosition);
        if (distance < 0.5) {
            return;
        }
        double angleToTarget = angleTo(targetPosition, robotPosition);
        double angularVelocity = 0;
        if (angleToTarget > robotDirection) {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < robotDirection) {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(maxVelocity, angularVelocity, 10);
    }

    private static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = robotPosition.x + velocity / angularVelocity *
                (Math.sin(robotDirection + angularVelocity * duration) -
                        Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotPosition.x + velocity * duration * Math.cos(robotDirection);
        }
        double newY = robotPosition.y - velocity / angularVelocity *
                (Math.cos(robotDirection + angularVelocity * duration) -
                        Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotPosition.y + velocity * duration * Math.sin(robotDirection);
        }
        robotPosition.setLocation(newX, newY);
//        robotPositionX = newX;
//        robotPositionY = newY;

        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
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

    private static int round(double value) {
        return (int) (value + 0.5);
    }
}
