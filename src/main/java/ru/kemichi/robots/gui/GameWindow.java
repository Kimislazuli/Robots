package ru.kemichi.robots.gui;

import ru.kemichi.robots.visualizers.GameVisualizer;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;
    public GameWindow(ResourceBundle bundle, int width, int height)
    {
        super(bundle.getString("gameWindowHeader"), true, true, true, true);
        m_visualizer = new GameVisualizer(robot);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        super.setSize(width, height);
    }
}
