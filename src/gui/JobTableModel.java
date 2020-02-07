package gui;

import javax.swing.table.AbstractTableModel;
import core.*;


public class JobTableModel extends AbstractTableModel {

    private JobList jl = new JobList();

    public JobTableModel(){ super(); }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public int getRowCount() {
        return jl.getData().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Job j = jl.getData().get(rowIndex);
        if (columnIndex == 0) {
            return j.getFile();

        } else if (columnIndex == 1) {
            int type = j.getType();
            if (type == 0) return "Split";
            else if (type == 1) return "Split & Compress";
            else if (type == 2) return "Split & Crypt";
            else if (type >= 3) return "Reassemble";

        } else if (columnIndex == 2) {
            return j.getStatus() + "%";
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) { return "File"; }
        else if (column == 1) { return "Tipo";}
        else if (column == 2) { return "Stato";}
        return null;
    }

    public void addJob(Job j){
        jl.addJob(j);
        fireTableDataChanged();
    }

    public void removeJob(int index){
        jl.removeJob(index);
        fireTableDataChanged();
    }
}
