package burp;

import javax.swing.*;
import javax.swing.table.TableModel;

public class HttpLogTable extends JTable {
    private HttpLogTableModel httpLogTableModel;

    public HttpLogTableModel getHttpLogTableModel() {
        return httpLogTableModel;
    }


    public HttpLogTable(TableModel tableModel) {
        super(tableModel);
        this.httpLogTableModel = (HttpLogTableModel) tableModel;
    }

    @Override
    public void changeSelection(int row, int col, boolean toggle, boolean extend) {
        super.changeSelection(row, col, toggle, extend);
        // show the log entry for the selected row
        LogEntry logEntry = BurpExtender.log.get(row);
        BurpExtender.requestViewer.setMessage(logEntry.requestResponse.getRequest(), true);
        BurpExtender.responseViewer.setMessage(logEntry.requestResponse.getResponse(), false);
        BurpExtender.proxyRspViewer.setText(logEntry.proxyResponse.getBytes());
        BurpExtender.proxyRspViewer.setEditable(false);
        GUI.currentlyDisplayedItem = logEntry.requestResponse;

    }
}
