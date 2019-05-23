package com.pao.project.connection.talkUI.panel;

import com.pao.project.connection.talkUI.Message;

import javax.swing.*;
import java.awt.*;

public class MessPanel implements ListCellRenderer<Message> {
    private JPanel mainPanel;
    private JLabel userLabel;
    private JLabel messageLabel;

    public MessPanel() {
        mainPanel.setSize(480, 100);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> jList, Message message, int i, boolean b, boolean b1) {
        if(message.isMyMessage()) {
            userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            messageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        } else {
            userLabel.setHorizontalAlignment(SwingConstants.LEFT);
            messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        }

        userLabel.setText(message.getUser());
        messageLabel.setText(message.getMessage());

        return mainPanel;
    }
}
