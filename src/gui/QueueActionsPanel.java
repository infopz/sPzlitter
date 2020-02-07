package gui;

import javax.swing.*;

public class QueueActionsPanel extends JPanel {

    public QueueActionsPanel(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel runAllPanel = new JPanel();
        JButton runAllButton = new JButton("Esegui Tutti");
        runAllButton.setMaximumSize(runAllButton.getPreferredSize());
        runAllButton.setHorizontalAlignment(SwingConstants.CENTER);
        runAllPanel.add(runAllButton);
        add(runAllPanel);

        JPanel runOnePanel = new JPanel();
        JButton runOneButton = new JButton("Esegui Selezionato");
        runOneButton.setMaximumSize(runOneButton.getPreferredSize());
        runOneButton.setHorizontalAlignment(SwingConstants.CENTER);
        runOnePanel.add(runOneButton);
        add(runOnePanel);

        JPanel removePanel = new JPanel();
        JButton removeButton = new JButton("Rimuovi Selezionato");
        removeButton.setMaximumSize(removeButton.getPreferredSize());
        removeButton.setHorizontalAlignment(SwingConstants.CENTER);
        removePanel.add(removeButton);
        add(removePanel);

    }
}
