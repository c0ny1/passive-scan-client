package burp;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI implements IMessageEditorController {
    private JPanel contentPane;
    private JLabel lbHost;
    private JTextField tfHost;
    private JLabel lbPort;
    private JTextField tfPort;
    private JLabel lbTimeout;
    private JTextField tfTimeout;
    private JLabel lbIntervalTime;
    private JTextField tfIntervalTime;
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

    public static IMessageEditor requestViewer;
    public static IMessageEditor responseViewer;
    public static ITextEditor proxyRspViewer;


    public GUI() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        ////////////////////////////////////////////////////////////////////
        // topPanel start
        ////////////////////////////////////////////////////////////////////
        JPanel topPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0 };
        gridBagLayout.rowHeights = new int[] { 40, 32, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0D, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0D, 0.0D, 1.0D, Double.MIN_VALUE };
        topPanel.setLayout(gridBagLayout);

        JPanel ConfigPanel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(5, 5, 5, 5);
        gbc_panel.fill = 2;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        topPanel.add(ConfigPanel, gbc_panel);

        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 40, 100, 0, 39, 33, 25, 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D,0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D,0.0D, Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0D, Double.MIN_VALUE };
        ConfigPanel.setLayout(gbl_panel);

        lbHost = new JLabel("Host:");
        GridBagConstraints gbc_lbHost = new GridBagConstraints();
        gbc_lbHost.fill = 2;
        gbc_lbHost.insets = new Insets(0, 0, 0, 5);
        gbc_lbHost.gridx = 0;
        gbc_lbHost.gridy = 0;
        ConfigPanel.add(lbHost, gbc_lbHost);

        tfHost = new JTextField();
        tfHost.setColumns(10);
        tfHost.setText("127.0.0.1");
        GridBagConstraints gbc_tfHost = new GridBagConstraints();
        gbc_tfHost.fill = 2;
        gbc_tfHost.insets = new Insets(0, 0, 0, 5);
        gbc_tfHost.gridx = 1;
        gbc_tfHost.gridy = 0;
        ConfigPanel.add(tfHost, gbc_tfHost);

        lbPort = new JLabel("Port:");
        GridBagConstraints gbc_lbPort = new GridBagConstraints();
        gbc_lbPort.fill = 2;
        gbc_lbPort.insets = new Insets(0, 0, 0, 5);
        gbc_lbPort.gridx = 2;
        gbc_lbPort.gridy = 0;
        ConfigPanel.add(lbPort, gbc_lbPort);

        tfPort = new JTextField();
        tfPort.setText("9898");
        tfPort.setColumns(10);
        GridBagConstraints gbc_tfPort = new GridBagConstraints();
        gbc_tfPort.fill = 2;
        gbc_tfPort.insets = new Insets(0, 0, 0, 5);
        gbc_tfPort.gridx = 3;
        gbc_tfPort.gridy = 0;
        ConfigPanel.add(tfPort, gbc_tfPort);

        lbUsername = new JLabel("Username:");
        GridBagConstraints gbc_lbUsername = new GridBagConstraints();
        gbc_lbUsername.fill = 2;
        gbc_lbUsername.insets = new Insets(0, 0, 0, 5);
        gbc_lbUsername.gridx = 4;
        gbc_lbUsername.gridy = 0;
        ConfigPanel.add(lbUsername, gbc_lbUsername);

        tfUsername = new JTextField();
        tfUsername.setText("");
        tfUsername.setColumns(10);
        GridBagConstraints gbc_tfUsername = new GridBagConstraints();
        gbc_tfUsername.fill = 2;
        gbc_tfUsername.insets = new Insets(0, 0, 0, 5);
        gbc_tfUsername.gridx = 5;
        gbc_tfUsername.gridy = 0;
        ConfigPanel.add(tfUsername, gbc_tfUsername);

        lbPassword = new JLabel("Password:");
        GridBagConstraints gbc_lbPassword = new GridBagConstraints();
        gbc_lbPassword.fill = 2;
        gbc_lbPassword.insets = new Insets(0, 0, 0, 5);
        gbc_lbPassword.gridx = 6;
        gbc_lbPassword.gridy = 0;
        ConfigPanel.add(lbPassword, gbc_lbPassword);

        tfPassword = new JTextField();
        tfPassword.setText("");
        tfPassword.setColumns(10);
        GridBagConstraints gbc_tfPassword = new GridBagConstraints();
        gbc_tfPassword.fill = 2;
        gbc_tfPassword.insets = new Insets(0, 0, 0, 5);
        gbc_tfPassword.gridx = 7;
        gbc_tfPassword.gridy = 0;
        ConfigPanel.add(tfPassword, gbc_tfPassword);

        lbTimeout = new JLabel("Timeout:");
        GridBagConstraints gbc_lbTimeout = new GridBagConstraints();
        gbc_lbTimeout.fill = 2;
        gbc_lbTimeout.gridx = 8;
        gbc_lbTimeout.gridy = 0;
        ConfigPanel.add(lbTimeout, gbc_lbTimeout);

        tfTimeout = new JTextField();
        tfTimeout.setText("5000");
        tfTimeout.setColumns(5);
        GridBagConstraints gbc_tfTimeout = new GridBagConstraints();
        gbc_tfTimeout.fill = 2;
        gbc_tfTimeout.insets = new Insets(0, 0, 0, 5);
        gbc_tfTimeout.gridx = 9;
        gbc_tfTimeout.gridy = 0;
        ConfigPanel.add(tfTimeout, gbc_tfTimeout);

        // 增加间隔时间
        lbIntervalTime = new JLabel("Interva lTime:");
        GridBagConstraints gbc_lbIntervalTime = new GridBagConstraints();
        gbc_lbIntervalTime.fill = 2;
        gbc_lbIntervalTime.gridx = 10;
        gbc_lbIntervalTime.gridy = 0;
        ConfigPanel.add(lbIntervalTime, gbc_lbIntervalTime);

        tfIntervalTime = new JTextField();
        tfIntervalTime.setText("5000");
        tfIntervalTime.setColumns(5);
        GridBagConstraints gbc_tfIntervalTime = new GridBagConstraints();
        gbc_tfIntervalTime.fill = 2;
        gbc_tfIntervalTime.insets = new Insets(0, 0, 0, 5);
        gbc_tfIntervalTime.gridx = 11;
        gbc_tfIntervalTime.gridy = 0;
        ConfigPanel.add(tfIntervalTime, gbc_tfIntervalTime);

        
        GridBagConstraints gbc_lb1 = new GridBagConstraints();
        gbc_lb1.anchor = 15;
        gbc_lb1.insets = new Insets(0, 0, 0, 5);
        gbc_lb1.gridx = 12;
        gbc_lb1.gridy = 0;
        ConfigPanel.add(new JLabel(""), gbc_lb1);

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
                    Config.INTERVAL_TIME = Integer.valueOf(tfIntervalTime.getText());
                    setAllEnabled(false);
                }else{
                    btnConn.setText("Run");
                    Config.IS_RUNNING = false;
                    setAllEnabled(true);
                }
                btnConn.setSelected(isSelected);

            }
        });
        GridBagConstraints gbc_btnConn = new GridBagConstraints();
        gbc_btnConn.fill = 2;
        gbc_btnConn.insets = new Insets(0, 0, 0, 5);
        gbc_btnConn.gridx = 13;
        gbc_btnConn.gridy = 0;
        ConfigPanel.add(btnConn, gbc_btnConn);

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
                    requestViewer.setMessage("".getBytes(),true);
                    responseViewer.setMessage("".getBytes(),false);
                    proxyRspViewer.setText("".getBytes());
                }
            }
        });
        GridBagConstraints gbc_btnClear = new GridBagConstraints();
        gbc_btnClear.fill = 2;
        gbc_btnClear.insets = new Insets(0, 0, 0, 5);
        gbc_btnClear.gridx = 14;
        gbc_btnClear.gridy = 0;
        ConfigPanel.add(btnClear, gbc_btnClear);
        ////////////////////////////////////////////////////////////////////

        JPanel FilterPanel = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.insets = new Insets(0, 5, 5, 5);
        gbc_panel_1.fill = 2;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 1;
        topPanel.add(FilterPanel, gbc_panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[] { 40, 225, 0, 0, 0 };
        gbl_panel_1.rowHeights = new int[] { 0, 0 };
        gbl_panel_1.columnWeights = new double[] { 0.0D, 0.0D, 0.0D,0.0D,1.0D, 0.0D, 0.0D,0.0D,0.0D,0.0D,0.0D,0.0D,Double.MIN_VALUE };
        gbl_panel_1.rowWeights = new double[] { 0.0D, Double.MIN_VALUE };
        FilterPanel.setLayout(gbl_panel_1);

        JLabel lbDomain = new JLabel("Domain:");
        GridBagConstraints gbc_lblDomain = new GridBagConstraints();
        gbc_lblDomain.insets = new Insets(0, 0, 0, 5);
        gbc_lblDomain.anchor = 13;
        gbc_lblDomain.gridx = 0;
        gbc_lblDomain.gridy = 0;
        FilterPanel.add(lbDomain, gbc_lblDomain);


        tfDomain = new JTextField(20);
        tfDomain.setText("");
        GridBagConstraints gbc_tfDomain = new GridBagConstraints();
        gbc_tfDomain.insets = new Insets(0, 0, 0, 5);
        gbc_tfDomain.fill = 2;
        gbc_tfDomain.gridx = 1;
        gbc_tfDomain.gridy = 0;
        FilterPanel.add(tfDomain, gbc_tfDomain);


        JLabel lbExcludeSuffix = new JLabel("Exclude suffix:");
        GridBagConstraints gbc_lbExcludeSuffix = new GridBagConstraints();
        gbc_lbExcludeSuffix.insets = new Insets(0, 0, 0, 5);
        gbc_lbExcludeSuffix.anchor = 13;
        gbc_lbExcludeSuffix.fill = 2;
        gbc_lbExcludeSuffix.gridx = 2;
        gbc_lbExcludeSuffix.gridy = 0;
        FilterPanel.add(lbExcludeSuffix, gbc_lbExcludeSuffix);

        tfExcludeSuffix = new JTextField(35);
        tfExcludeSuffix.setText("js|css|jpeg|gif|jpg|png|pdf|rar|zip|docx|doc|svg|jpeg|ico|woff|woff2|ttf|otf");
        GridBagConstraints gbc_tfExcludeSuffix = new GridBagConstraints();
        gbc_tfExcludeSuffix.insets = new Insets(0, 0, 0, 5);
        gbc_tfExcludeSuffix.fill = 2;
        gbc_tfExcludeSuffix.gridx = 3;
        gbc_tfExcludeSuffix.gridy = 0;
        FilterPanel.add(tfExcludeSuffix, gbc_tfExcludeSuffix);


        GridBagConstraints gbc_vb = new GridBagConstraints();
        gbc_vb.insets = new Insets(0, 0, 0, 5);
        gbc_vb.fill = 2;
        gbc_vb.gridx = 4;
        gbc_vb.gridy = 0;
        FilterPanel.add(Box.createVerticalBox(), gbc_vb);

        JLabel lbRequest = new JLabel("Total:");
        GridBagConstraints gbc_lbRequest = new GridBagConstraints();
        gbc_lbRequest.insets = new Insets(0, 0, 0, 5);
        gbc_lbRequest.fill = 2;
        gbc_lbRequest.gridx = 5;
        gbc_lbRequest.gridy = 0;
        FilterPanel.add(lbRequest, gbc_lbRequest);


        lbRequestCount = new JLabel("0");
        lbRequestCount.setForeground(new Color(0,0,255));
        GridBagConstraints gbc_lbRequestCount = new GridBagConstraints();
        gbc_lbRequestCount.insets = new Insets(0, 0, 0, 5);
        gbc_lbRequestCount.fill = 2;
        gbc_lbRequestCount.gridx = 6;
        gbc_lbRequestCount.gridy = 0;
        FilterPanel.add(lbRequestCount, gbc_lbRequestCount);

        GridBagConstraints gbc_vb2 = new GridBagConstraints();
        gbc_vb2.insets = new Insets(0, 0, 0, 5);
        gbc_vb2.fill = 2;
        gbc_vb2.gridx = 7;
        gbc_vb2.gridy = 0;
        FilterPanel.add(Box.createVerticalBox(), gbc_vb);

        JLabel lbSucces = new JLabel("Success:");
        GridBagConstraints gbc_lbSucces = new GridBagConstraints();
        gbc_lbSucces.insets = new Insets(0, 0, 0, 5);
        gbc_lbSucces.fill = 2;
        gbc_lbSucces.gridx = 8;
        gbc_lbSucces.gridy = 0;
        FilterPanel.add(lbSucces, gbc_lbSucces);

        lbSuccesCount = new JLabel("0");
        lbSuccesCount.setForeground(new Color(0, 255, 0));
        GridBagConstraints gbc_lbSuccesCount = new GridBagConstraints();
        gbc_lbSuccesCount.insets = new Insets(0, 0, 0, 5);
        gbc_lbSuccesCount.fill = 2;
        gbc_lbSuccesCount.gridx = 9;
        gbc_lbSuccesCount.gridy = 0;
        FilterPanel.add(lbSuccesCount, gbc_lbSuccesCount);

        GridBagConstraints gbc_vb3 = new GridBagConstraints();
        gbc_vb3.insets = new Insets(0, 0, 0, 5);
        gbc_vb3.fill = 2;
        gbc_vb3.gridx = 10;
        gbc_vb3.gridy = 0;
        FilterPanel.add(Box.createVerticalBox(), gbc_vb3);

        JLabel lbFail = new JLabel("Fail:");
        GridBagConstraints gbc_lbFail = new GridBagConstraints();
        gbc_lbFail.insets = new Insets(0, 0, 0, 5);
        gbc_lbFail.fill = 2;
        gbc_lbFail.gridx = 11;
        gbc_lbFail.gridy = 0;
        FilterPanel.add(lbFail, gbc_lbFail);

        lbFailCount = new JLabel("0");
        lbFailCount.setForeground(new Color(255, 0, 0));
        GridBagConstraints gbc_lbFailCount = new GridBagConstraints();
        gbc_lbFailCount.insets = new Insets(0, 0, 0, 5);
        gbc_lbFailCount.fill = 2;
        gbc_lbFailCount.gridx = 12;
        gbc_lbFailCount.gridy = 0;
        FilterPanel.add(lbFailCount, gbc_lbFailCount);

        contentPane.add(topPanel,BorderLayout.NORTH);
        ////////////////////////////////////////////////////////////////////
        // topPanl end
        ////////////////////////////////////////////////////////////////////

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(0.5);
        contentPane.add(splitPane, BorderLayout.CENTER);

        HttpLogTableModel model = new HttpLogTableModel();
        logTable = new HttpLogTable(model);
        //JTable表头排序,以下两种方法均存在问题，导致界面混乱。
        //方式一
        //TableRowSorter<HttpLogTableModel> tableRowSorter=new TableRowSorter<HttpLogTableModel>(model);
        //logTable.setRowSorter(tableRowSorter);
        //方式二
        //logTable.setAutoCreateRowSorter(true);

        JScrollPane jspLogTable = new JScrollPane(logTable);
        splitPane.setTopComponent(jspLogTable);


        JTabbedPane tabs = new JTabbedPane();
        requestViewer = BurpExtender.callbacks.createMessageEditor(this, false);
        responseViewer = BurpExtender.callbacks.createMessageEditor(this, false);
        proxyRspViewer = BurpExtender.callbacks.createTextEditor();

        tabs.addTab("Request", requestViewer.getComponent());
        tabs.addTab("Original response", responseViewer.getComponent());
        tabs.addTab("Proxy response",proxyRspViewer.getComponent());
        splitPane.setBottomComponent(tabs);

        BurpExtender.callbacks.customizeUiComponent(topPanel);
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
        tfHost.setEnabled(is);
        tfPort.setEnabled(is);
        tfUsername.setEnabled(is);
        tfPassword.setEnabled(is);
        tfTimeout.setEnabled(is);
        tfDomain.setEnabled(is);
        tfExcludeSuffix.setEnabled(is);
        tfIntervalTime.setEnabled(is);
    }
}