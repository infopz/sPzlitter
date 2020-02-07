package gui.secondaryPannels;

import javax.swing.*;
import java.awt.*;

public class RightRadioPanel extends JPanel {

    private JRadioButton checkParti, checkDim;
    private JTextField partNum, dimNum;
    private ButtonGroup bg;

    public RightRadioPanel(){
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new BorderLayout());

        // Pannello contenete il primo radio button e il textField
        JPanel parPanel = new JPanel();
        parPanel.setLayout(new BoxLayout(parPanel, BoxLayout.X_AXIS));
        checkParti = new JRadioButton("Parti");
        partNum = new JTextField(3);
        partNum.setMaximumSize(partNum.getPreferredSize());
        parPanel.add(checkParti); parPanel.add(partNum);

        // Pannello contenete il secondo radio button e il textfield
        JPanel dimPanel = new JPanel();
        dimPanel.setLayout(new BoxLayout(dimPanel, BoxLayout.X_AXIS));
        checkDim = new JRadioButton("Dimensione");
        dimNum = new JTextField(3);
        dimNum.setMaximumSize(dimNum.getPreferredSize());
        dimPanel.add(checkDim); dimPanel.add(dimNum);

        bg = new ButtonGroup();
        bg.add(checkDim); bg.add(checkParti);

        comboPanel.add(parPanel, BorderLayout.PAGE_START);
        comboPanel.add(dimPanel, BorderLayout.PAGE_END);

        // Pannello contente i radiobutton
        // Contiene la spaziatura a sinista e un altro pannello contenitore
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(Box.createRigidArea(new Dimension(20,0)));
        add(comboPanel);
    }

    public String getValue(){
        if (checkParti.isSelected()) return "0;"+partNum.getText();
        else if (checkDim.isSelected()) return "1;"+dimNum.getText();
        return "";
    }

    public void reset(){
        bg.clearSelection();
        partNum.setText("");
        dimNum.setText("");
    }

}
