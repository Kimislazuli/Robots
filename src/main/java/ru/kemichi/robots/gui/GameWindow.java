package ru.kemichi.robots.gui;

import ru.kemichi.robots.controllers.GameWindowController;
import ru.kemichi.robots.views.GameView;
import ru.kemichi.robots.views.GameVisualizer;
import ru.kemichi.robots.views.View;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private GameView visualizer;
    private GameWindowController controller;

//    private final GameVisualizer m_visualizer;
    public GameWindow(ResourceBundle bundle, int width, int height)
    {
        super(bundle.getString("gameWindowHeader"), true, true, true, true);
//        m_visualizer = new GameVisualizer();
        visualizer = new GameView();
        controller = new GameWindowController();
        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(m_visualizer, BorderLayout.CENTER);
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        super.setSize(width, height);
    }
}
