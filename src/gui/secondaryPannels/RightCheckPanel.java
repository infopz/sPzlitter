package gui.secondaryPannels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RightCheckPanel extends JPanel implements ItemListener {

    private JCheckBox comprimi, cripta;

    public RightCheckPanel(){
        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new BorderLayout());
        comprimi = new JCheckBox("Comprimi");
        cripta = new JCheckBox("Cripta");
        comprimi.addItemListener(this);
        cripta.addItemListener(this);
        checkPanel.add(comprimi, BorderLayout.PAGE_START);
        checkPanel.add(cripta, BorderLayout.PAGE_END);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(Box.createRigidArea(new Dimension(20,0)));
        add(checkPanel);
    }

    public boolean[] getChecks(){
        boolean[] b = new boolean[2];
        b[0] = comprimi.isSelected();
        b[1] = cripta.isSelected();
        return b;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object caller = e.getItemSelectable();
        if(caller == comprimi){
            if (comprimi.isSelected()) cripta.setEnabled(false);
            else cripta.setEnabled(true);
        }
        else{
            if (cripta.isSelected()) comprimi.setEnabled(false);
            else comprimi.setEnabled(true);
        }

    }

    public void reset(){
        comprimi.setEnabled(true);
        comprimi.setSelected(false);
        cripta.setEnabled(true);
        cripta.setSelected(false);
    }
}
