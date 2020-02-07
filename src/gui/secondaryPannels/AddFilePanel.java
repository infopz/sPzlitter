package gui.secondaryPannels;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AddFilePanel extends JPanel {

    private JButton b;

    public AddFilePanel(ActionListener al){
        b = new JButton("Aggiungi File");
        b.setMaximumSize(b.getPreferredSize());
        b.setHorizontalAlignment(SwingConstants.CENTER);
        b.addActionListener(al);
        add(b);
    }
}
