package burp;


import sun.tools.jconsole.inspector.TableSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.transform.sax.SAXTransformerFactory;

public class GUI implements IMessageEditorController {
    private JPanel contentPane;
    private JLabel lbHost;
    private JTextField tfHost;
    private JLabel lbPort;
    private JTextField tfPort;
    private JLabel lbTimeout;
    private JTextField tfTimeout;
    private JLabel lbUsername;
    private JTextField tfUsername;
    private JLabel lbPassword;
    private JTextField tfPassword;
    private JTextField tfDomain;
    private JTextField tfExcludeSuffix;
    private JToggleButton btnConn;
    private JButton btnClear;
    private JSplitPane splitPane;
    public static HttpLogTable logTable;
    public static IHttpRequestResponse currentlyDisplayedItem;
    public static JLabel lbRequestCount;
    public static JLabel lbSuccesCount;
    public static JLabel lbFailCount;




    public GUI() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout(0, 0));
        contentPane.add(panel, BorderLayout.NORTH);

        JPanel OptionPanel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) OptionPanel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        //OptionPanel.setBorder(BorderFactory.createTitledBorder("Config"));

        lbHost = new JLabel("Host:");
        OptionPanel.add(lbHost);
        tfHost = new JTextField();
        tfHost.setColumns(10);
        tfHost.setText("127.0.0.1");
        OptionPanel.add(tfHost);

        lbPort = new JLabel("Port:");
        OptionPanel.add(lbPort);
        tfPort = new JTextField();
        tfPort.setText("1664");
        tfPort.setColumns(10);
        OptionPanel.add(tfPort);

        lbUsername = new JLabel("Username:");
        OptionPanel.add(lbUsername);
        tfUsername = new JTextField();
        tfUsername.setText("");
        tfUsername.setColumns(10);
        OptionPanel.add(tfUsername);


        lbPassword = new JLabel("Password:");
        OptionPanel.add(lbPassword);
        tfPassword = new JTextField();
        tfPassword.setText("");
        tfPassword.setColumns(10);
        OptionPanel.add(tfPassword);


        lbTimeout = new JLabel("Timeout:");
        OptionPanel.add(lbTimeout);
        tfTimeout = new JTextField();
        tfTimeout.setText("5000");
        tfTimeout.setColumns(5);
        OptionPanel.add(tfTimeout);



        btnConn = new JToggleButton("Run");
        btnConn.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                boolean isSelected = btnConn.isSelected();

                if(isSelected){
                    btnConn.setText("Stop");
                    Config.IS_RUNNING = true;
                    Config.PROXY_HOST = tfHost.getText();
                    Config.PROXY_PORT = Integer.valueOf(tfPort.getText());
                    Config.PROXY_TIMEOUT = Integer.valueOf(tfTimeout.getText());
                    Config.PROXY_USERNAME = tfUsername.getText();
                    Config.PROXY_PASSWORD = tfPassword.getText();
                    Config.DOMAIN_REGX = tfDomain.getText();
                    Config.SUFFIX_REGX = tfExcludeSuffix.getText();
                    setAllEnabled(false);
                }else{
                    btnConn.setText("Run");
                    Config.IS_RUNNING = false;
                    setAllEnabled(true);
                }
                btnConn.setSelected(isSelected);

            }
        });
        OptionPanel.add(btnConn);

        btnClear = new JButton("Clear");
        btnClear.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the data？", "Passvie Scan Client prompt", JOptionPane.YES_NO_OPTION);
                if(n == 0) {
                    Config.REQUEST_TOTAL = 0;
                    lbRequestCount.setText("0");
                    Config.SUCCESS_TOTAL = 0;
                    lbSuccesCount.setText("0");
                    Config.FAIL_TOTAL = 0;
                    lbFailCount.setText("0");
                    BurpExtender.log.clear();
                    logTable.getHttpLogTableModel().fireTableDataChanged();//通知模型更新
                    logTable.updateUI();//刷新表格
                }
            }
        });
        OptionPanel.add(btnClear);

        panel.add(OptionPanel, BorderLayout.NORTH);

        JPanel ActOnPanel = new JPanel();
        FlowLayout actFlowLayout = (FlowLayout) ActOnPanel.getLayout();
        actFlowLayout.setAlignment(FlowLayout.LEFT);
        //ActOnPanel.setBorder(BorderFactory.createTitledBorder("Tools"));

        JLabel lbDomain = new JLabel("Domain:");
        tfDomain = new JTextField(20);
        tfDomain.setText("");
        ActOnPanel.add(lbDomain);
        ActOnPanel.add(tfDomain);

        JLabel lbExcludeSuffix = new JLabel("Exclude suffix:");
        tfExcludeSuffix = new JTextField(35);
        tfExcludeSuffix.setText("js|css|jpeg|gif|jpg|png|pdf|rar|zip|docx|doc");
        ActOnPanel.add(lbExcludeSuffix);
        ActOnPanel.add(tfExcludeSuffix);

        ActOnPanel.add(Box.createVerticalBox());
        JLabel lbRequest = new JLabel("Total:");
        lbRequestCount = new JLabel("0");
        lbRequestCount.setForeground(new Color(0,0,255));
        ActOnPanel.add(lbRequest);
        ActOnPanel.add(lbRequestCount);
        ActOnPanel.add(Box.createVerticalBox());
        JLabel lbSucces = new JLabel("Success:");
        lbSuccesCount = new JLabel("0");
        lbSuccesCount.setForeground(new Color(0, 255, 0));
        ActOnPanel.add(lbSucces);
        ActOnPanel.add(lbSuccesCount);
        ActOnPanel.add(Box.createVerticalBox());
        JLabel lbFail = new JLabel("Fail:");
        lbFailCount = new JLabel("0");
        lbFailCount.setForeground(new Color(255, 0, 0));
        ActOnPanel.add(lbFail);
        ActOnPanel.add(lbFailCount);

        panel.add(ActOnPanel,BorderLayout.SOUTH);

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(0.5);
        contentPane.add(splitPane, BorderLayout.CENTER);

        HttpLogTableModel model = new HttpLogTableModel();
        logTable = new HttpLogTable(model);
        //JTable表头排序
        //TableRowSorter<HttpLogTableModel> tableRowSorter=new TableRowSorter<HttpLogTableModel>(model);
        //logTable.setRowSorter(tableRowSorter);

        JScrollPane jspLogTable = new JScrollPane(logTable);
        splitPane.setTopComponent(jspLogTable);


        JTabbedPane tabs = new JTabbedPane();
        BurpExtender.requestViewer = BurpExtender.callbacks.createMessageEditor(this, false);
        BurpExtender.responseViewer = BurpExtender.callbacks.createMessageEditor(this, false);
        BurpExtender.proxyRspViewer = BurpExtender.callbacks.createTextEditor();

        tabs.addTab("Request", BurpExtender.requestViewer.getComponent());
        tabs.addTab("Original response", BurpExtender.responseViewer.getComponent());
        tabs.addTab("Proxy response",BurpExtender.proxyRspViewer.getComponent());
        splitPane.setBottomComponent(tabs);

        BurpExtender.callbacks.customizeUiComponent(panel);
        BurpExtender.callbacks.customizeUiComponent(btnConn);
        BurpExtender.callbacks.customizeUiComponent(splitPane);
        BurpExtender.callbacks.customizeUiComponent(contentPane);
    }

    public Component getComponet(){
        return contentPane;
    }

    public IHttpService getHttpService() {
        return currentlyDisplayedItem.getHttpService();
    }

    public byte[] getRequest() {
        return currentlyDisplayedItem.getRequest();
    }

    public byte[] getResponse() {
        return currentlyDisplayedItem.getResponse();
    }

    public void setAllEnabled(boolean is){
        //lbHost.setEnabled(is);
        tfHost.setEnabled(is);
        //lbPort.setEnabled(is);
        tfPort.setEnabled(is);
        //lbUsername.setEnabled(is);
        tfUsername.setEnabled(is);
        //lbPassword.setEnabled(is);
        tfPassword.setEnabled(is);
        //lbTimeout.setEnabled(is);
        tfTimeout.setEnabled(is);
        tfDomain.setEnabled(is);
        tfExcludeSuffix.setEnabled(is);
    }
}