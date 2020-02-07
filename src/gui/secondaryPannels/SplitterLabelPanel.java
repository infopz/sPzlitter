package gui.secondaryPannels;

import javax.swing.*;

public class SplitterLabelPanel extends JPanel {
    public SplitterLabelPanel(){
        JLabel title = new JLabel("Splitter");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);
    }
}
