package gui;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {

    private ReassemblePanel reassemblePanel;
    private QueueActionsPanel queueActionsPanel;

    public LeftPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        reassemblePanel = new ReassemblePanel();
        queueActionsPanel = new QueueActionsPanel();
        add(reassemblePanel);
        add(Box.createRigidArea(new Dimension(0,100)));
        add(queueActionsPanel);
    }
}
