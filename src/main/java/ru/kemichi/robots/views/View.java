package ru.kemichi.robots.views;

import javax.swing.*;
import java.awt.*;

public abstract class View extends JPanel {
    protected void onRedrawEvent() {

    }



    public void doubleBuffered(boolean b) {
        setDoubleBuffered(true);
    }
}
