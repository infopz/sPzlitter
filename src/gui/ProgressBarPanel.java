package gui;

import javax.swing.*;
import java.awt.*;

public class ProgressBarPanel extends JPanel {

    public ProgressBarPanel(){
        super();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(Box.createRigidArea(new Dimension(60,0)));

        JProgressBar pb = new JProgressBar();
        add(pb);

        add(Box.createRigidArea(new Dimension(60,0)));
    }
}
