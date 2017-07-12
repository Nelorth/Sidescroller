package gui;

import javax.swing.*;

/**
 * Abstrakte Klasse für eine abstrakte Ansicht
 */
public abstract class AbstractView extends JPanel {

    AbstractView() {
    }

    public abstract void refresh();
}
