package ru.list.surkovr.graphics;

import javax.swing.*;

import static ru.list.surkovr.Constants.*;

public class ApplicationGui extends JFrame
{

    public ApplicationGui(Client client) {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);
        add(new MainForm(client).getMainPanel());
        setVisible(true);
    }
}