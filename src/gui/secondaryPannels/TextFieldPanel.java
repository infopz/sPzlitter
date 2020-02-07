package gui.secondaryPannels;

import javax.swing.*;

public class TextFieldPanel extends JPanel {

    private JTextField textField = new JTextField(15);

    public TextFieldPanel(){
        textField.setEditable(false);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        add(textField);
    }

    public void update(String newText){
        textField.setText(newText);
    }

    public void reset(){
        textField.setText("");
    }

}
