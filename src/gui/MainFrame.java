package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private TablePanel tabel;
    private LeftPanel leftPanel;
    private SplitterPanel splitterPanel;
    private ProgressBarPanel progressBarPanel;

    public MainFrame(String s) {
        super(s);
        setBounds(500, 200, 500, 300);
        Container c = getContentPane();
        setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

        JPanel MainPanel = new JPanel();
        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.X_AXIS));
        tabel = new TablePanel();
        splitterPanel = new SplitterPanel(tabel);
        MainPanel.add(splitterPanel);


        MainPanel.add(tabel);

        leftPanel = new LeftPanel();
        MainPanel.add(leftPanel);

        c.add(MainPanel);

        c.add(Box.createRigidArea(new Dimension(0,7)));

        progressBarPanel = new ProgressBarPanel();
        c.add(progressBarPanel);

        c.add(Box.createRigidArea(new Dimension(0,7)));
        pack();
        //setResizable(false);
    }

}
