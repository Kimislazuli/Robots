package ru.kemichi.robots.views;

import ru.kemichi.robots.models.Robot;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {
    private Robot robot = new Robot();
    private final Timer m_timer = initTimer();

    private Point robotPosition = robot.getRobotPosition();
    private double robotDirection = robot.getRobotDirection();
    private Point targetPosition = robot.getTargetPosition();

    private static Timer initTimer() {
        Timer timer = new Timer("events generator", true);
        return timer;
    }


    public GameView() {
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onRedrawEvent();
            }
        }, 0, 50);

        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Point robotCurrentPosition = robot.getRobotPosition();
                Point targetCurrentPosition = robot.getTargetPosition();
                if ((robotPosition != robotCurrentPosition) || (targetPosition != targetCurrentPosition)) {
                    onRedrawEvent();
                }
            }
        }, 0, 10);
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                setTargetPosition(e.getPoint());
//                repaint();
//            }
//        });
        setDoubleBuffered(true);
    }
    @Override
    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    private static int round(double value) {
        return (int) (value + 0.5);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, robotPosition, robotDirection);
        drawTarget(g2d, targetPosition);
    }

    private void drawRobot(Graphics2D g, Point position, double direction) {
        int robotCenterX = round(position.x);
        int robotCenterY = round(position.y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + 10, robotCenterY, 5, 5);
    }

    private void drawTarget(Graphics2D g, Point position) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, position.x, position.y, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, position.x, position.y, 5, 5);
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
}
