package gui.secondaryPannels;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AddToQueuePanel extends JPanel {
    public AddToQueuePanel(ActionListener al){
        JButton addToQueue = new JButton("Aggiungi in Coda");
        addToQueue.addActionListener(al);
        addToQueue.setMaximumSize(addToQueue.getPreferredSize());
        addToQueue.setHorizontalAlignment(SwingConstants.CENTER);
        add(addToQueue);
    }
}
