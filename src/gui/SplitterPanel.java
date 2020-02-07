package gui;

import core.CompressSplitter;
import core.CypterSplitter;
import core.Job;
import core.Splitter;
import gui.secondaryPannels.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SplitterPanel extends JPanel{

    private TextFieldPanel textFieldPanel = new TextFieldPanel();
    private RightRadioPanel rightRadioPanel = new RightRadioPanel();
    private RightCheckPanel rightCheckPanel = new RightCheckPanel();

    private TablePanel tablePanel;

    private File[] selectedFiles;

    public SplitterPanel(TablePanel table) {
        super();
        tablePanel = table;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createRigidArea(new Dimension(0,10)));

        // Pannello contenete la label in alto
        add(new SplitterLabelPanel());

        // Pannello contenente la TextField per inserire i file
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
                    textFieldPanel.update(filenames);
                }
            }
        });
        add(addFilePanel);

        add(Box.createRigidArea(new Dimension(0,20)));

        // Pannello contenete la label Dividi Per
        add(new DividiPerLabelPanel());

        // Pannello contenete i due radio button
        add(rightRadioPanel);

        add(Box.createRigidArea(new Dimension(0,20)));

        // Label Opzioni aggiuntive
        add(new OpzioniLabelPanel());

        // Pannello contente i due CheckBox
        add(rightCheckPanel);

        add(Box.createRigidArea(new Dimension(0,20)));

        // Pannello contenete l
        AddToQueuePanel addToQueuePanel = new AddToQueuePanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JobTableModel tm = tablePanel.getModel();
                Job[] jobs = createJobs();
                if (jobs == null) return;
                for (int i=0; i<jobs.length; i++) {tm.addJob(jobs[i]);}
            }
        });
        add(addToQueuePanel);

    }

    private Job[] createJobs(){
        if (selectedFiles == null ){
            JOptionPane.showMessageDialog(null, "Non hai selezionato nessun file");
            return null;
        }
        Job[] jobs = new Job[selectedFiles.length];
        boolean[] opzioni = rightCheckPanel.getChecks();
        String divisione = rightRadioPanel.getValue();
        if (divisione.equals("")) {
            JOptionPane.showMessageDialog(null, "Seleziona una opzione di divisione prima di aggiungere il/i files(s) in coda");
            return null;
        }
        String[] divisioneCampi = divisione.split(";");
        if (divisioneCampi.length == 1) {
            JOptionPane.showMessageDialog(null, "Oltre a selzionare il tipo di divisione devi " +
                    "inserire anche il numero di byte per parte o il numero di parti");
            return null;
        }
        int tipo = Integer.parseInt(divisioneCampi[0]);
        int val = Integer.parseInt(divisioneCampi[1]);
        for (int i=0; i<selectedFiles.length; i++) {
            Splitter s;
            System.out.println("" + opzioni[0] + opzioni[1]);
            if (!opzioni[0] && !opzioni[1]) s = new Splitter(selectedFiles[i]);
            else if (opzioni[0]) s = new CompressSplitter(selectedFiles[i]);
            else s = new CypterSplitter(selectedFiles[i]);

            if (tipo==0) s.setnParts(val);
            else s.setDim(val);

            Job j = new Job(s);
            jobs[i] = j;
        }

        textFieldPanel.reset();
        rightRadioPanel.reset();
        rightCheckPanel.reset();

        return jobs;
    }

}
