package gui;

import core.Job;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class TablePanel extends JPanel {

    private JTable t;
    private JobTableModel tm = new JobTableModel();

    public TablePanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0,5)));

        t = new JTable(tm);

        TableColumnModel cm = t.getColumnModel();
        cm.getColumn(0).setPreferredWidth(325);
        cm.getColumn(1).setPreferredWidth(125);
        cm.getColumn(2).setPreferredWidth(50);

        JScrollPane sp = new JScrollPane(t);
        sp.setPreferredSize(new Dimension(500, 200));
        add(sp);

        add(Box.createRigidArea(new Dimension(0,5)));
    }

    public JobTableModel getModel(){ return tm; }
}
