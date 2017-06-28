package gui;

import javax.swing.*;
import java.awt.*;

class CreditsView extends AbstractView {
    private static CreditsView instance;

    private CreditsView() {
        super();
        setLayout(new BorderLayout());
        setBackground(GUIConstants.MENU_BACKGROUND_COLOR);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(GUIConstants.MENU_BACKGROUND_COLOR);

        JButton backButton = new JButton("Zurück");
        backButton.setBackground(GUIConstants.BUTTON_COLOR);
        //backButton.setPreferredSize(GUIConstants.defaultButtonSize);
        backButton.addActionListener(a -> MainFrame.getInstance().changeTo(MainMenuView.getInstance()));

        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    public void update() {

    }

    static CreditsView getInstance() {
        if (instance == null)
            instance = new CreditsView();
        return instance;
    }
}
