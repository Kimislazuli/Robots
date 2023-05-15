package ru.kemichi.robots.utility.functional_methods;

import ru.kemichi.robots.utility.data_classes.DoublePoint;

import java.awt.*;

public class GeometryKeeper {
    public static double distance(Point target, DoublePoint robot) {
        double diffX = target.getX() - robot.getX();
        double diffY = target.getY() - robot.getY();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public static double angleTo(Point target, DoublePoint robot) {
        double diffX = target.getX() - robot.getX();
        double diffY = target.getY() - robot.getY();

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public static double calculateAngularVelocity(double angle, double robotDirection, double maxAngularVelocity) {
        double diff = angle - robotDirection;
        if (robotDirection >= Math.PI) {
            if (diff < Math.PI & angle < robotDirection) {
                return -maxAngularVelocity;
            }
            return maxAngularVelocity;
        }
        else if (0 >= Math.abs(diff)) {
            return 0;
        }
        else {
            if (robotDirection < angle & diff > -Math.PI) {
                return maxAngularVelocity;
            }
            return -maxAngularVelocity;
        }
    }

    public static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }
}
