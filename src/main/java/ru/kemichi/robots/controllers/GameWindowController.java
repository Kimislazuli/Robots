package ru.kemichi.robots.controllers;

import ru.kemichi.robots.models.Robot;
import ru.kemichi.robots.views.GameView;
import ru.kemichi.robots.views.View;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class GameWindowController implements Controller {
    private Robot robot;
    private GameView gameView;

    public GameWindowController(Robot robot, GameView gameView) {
        this.robot = robot;
        this.gameView = gameView;
    }

    public GameWindowController() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                robot.setTargetPosition(e.getPoint());
            }
        });
    }


}