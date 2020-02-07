package gui;

import gui.secondaryPannels.AddToQueuePanel;
import gui.secondaryPannels.ReassembleLabelPanel;
import gui.secondaryPannels.AddFilePanel;
import gui.secondaryPannels.TextFieldPanel;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ReassemblePanel extends JPanel {

    private TextFieldPanel textFieldPanel;
    private File[] selectedFiles;

    public ReassemblePanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0,10)));

        add(new ReassembleLabelPanel());

        // Pannello contenente la TextField per inserire i file
        textFieldPanel = new TextFieldPanel();
        add(textFieldPanel);

        // Pannello contenente il bottone Aggiungi File
        AddFilePanel addFilePanel = new AddFilePanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fc.setDialogTitle("Seleziona File(s)");
                fc.setMultiSelectionEnabled(true);
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFiles = fc.getSelectedFiles();
                    String filenames = "";
                    for(int i=0; i<selectedFiles.length; i++) {filenames = filenames+selectedFiles[i].getName() +", ";}
                    filenames = filenames.substring(0, filenames.length()-2);
                    System.out.println(filenames);
                    textFieldPanel.update(filenames);
                }
            }
        });
        add(addFilePanel);

        AddToQueuePanel addToQueuePanel = new AddToQueuePanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        add(addToQueuePanel);
    }
}
