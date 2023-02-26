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
    private JTextField tfTargetHost;
    private JTextField tfBlackUrl;
    private JTextField tfBlackSuffix;
    private JToggleButton btnConn;
    private JToggleButton btnHash;
    private JToggleButton btnParam;
    private JToggleButton btnSmart;
    private JToggleButton btnAuth;
    private JButton btnClear;
    private JSplitPane splitPane;
    public static HttpLogTable logTable;
    public static IHttpRequestResponse currentlyDisplayedItem;
    public static JLabel lbRequestCount;
    public static JLabel lbSuccessCount;
    public static JLabel lbFailCount;

    public static IMessageEditor requestViewer;
    public static IMessageEditor responseViewer;
    public static ITextEditor proxyRspViewer;


    public GUI() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));

        ////////////////////////////////////////////////////////////////////
        //topPanel start
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
        gbl_panel.columnWeights = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D,0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D,0.0D, Double.MIN_VALUE };
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
        tfHost.setText(Config.PROXY_HOST);
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
        tfPort.setText(String.valueOf(Config.PROXY_PORT));
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
        tfTimeout.setText(String.valueOf(Config.PROXY_TIMEOUT));
        tfTimeout.setColumns(5);
        GridBagConstraints gbc_tfTimeout = new GridBagConstraints();
        gbc_tfTimeout.fill = 2;
        gbc_tfTimeout.insets = new Insets(0, 0, 0, 5);
        gbc_tfTimeout.gridx = 9;
        gbc_tfTimeout.gridy = 0;
        ConfigPanel.add(tfTimeout, gbc_tfTimeout);

        //增加间隔时间
        lbIntervalTime = new JLabel("Interval lTime:");
        GridBagConstraints gbc_lbIntervalTime = new GridBagConstraints();
        gbc_lbIntervalTime.fill = 2;
        gbc_lbIntervalTime.gridx = 10;
        gbc_lbIntervalTime.gridy = 0;
        ConfigPanel.add(lbIntervalTime, gbc_lbIntervalTime);

        tfIntervalTime = new JTextField();
        tfIntervalTime.setText(String.valueOf(Config.INTERVAL_TIME));
        tfIntervalTime.setColumns(5);
        GridBagConstraints gbc_tfIntervalTime = new GridBagConstraints();
        gbc_tfIntervalTime.fill = 2;
        gbc_tfIntervalTime.insets = new Insets(0, 0, 0, 5);
        gbc_tfIntervalTime.gridx = 11;
        gbc_tfIntervalTime.gridy = 0;
        ConfigPanel.add(tfIntervalTime, gbc_tfIntervalTime);

        //增加URL去重开关
        btnHash = new JToggleButton("HASH");
        btnHash.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                boolean isSelected = btnHash.isSelected();
                boolean oldStatus = Config.REQ_HASH;

                if(isSelected){
                    Config.REQ_HASH = true;
                    btnHash.setText("HASH");
                }else{
                    Config.REQ_HASH = false;
                    btnHash.setText("HASH");
                }
                btnHash.setSelected(isSelected);

                boolean newStatus = Config.REQ_HASH;
                //判断状态是否改变,改变了就输出
                if(oldStatus != newStatus){
                    Utils.showStdoutMsg(1, String.format("[*] Click Button [%s]: %s --> %s", "HASH", oldStatus, newStatus));
                }
            }
        });

        //根据配置文件设置HASH按钮的默认选择行为
        if(Config.SELECTED_HASH){
            btnHash.setSelected(true);
        }

        GridBagConstraints gbc_btnHash = new GridBagConstraints();
        gbc_btnHash.fill = 2;
        gbc_btnHash.insets = new Insets(0, 0, 0, 5);
        gbc_btnHash.gridx = 12;
        gbc_btnHash.gridy = 0;
        ConfigPanel.add(btnHash, gbc_btnHash);


        //增加无参数URL去除开关
        btnParam = new JToggleButton("PARAM");
        btnParam.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                boolean isSelected = btnParam.isSelected();
                boolean oldStatus = Config.REQ_PARAM;
                if(isSelected){
                    Config.REQ_PARAM = true;
                    btnParam.setText("PARAM");
                }else{
                    Config.REQ_PARAM = false;
                    btnParam.setText("PARAM");
                }
                btnParam.setSelected(isSelected);
                //判断状态是否改变,改变了就输出
                boolean newStatus = Config.REQ_PARAM;
                if(oldStatus != newStatus) {
                    Utils.showStdoutMsg(1, String.format("[*] Click Button [%s]: %s --> %s", "PARAM", oldStatus, newStatus));
                }
            }
        });

        //根据配置文件设置PARAM按钮的默认选择行为
        if(Config.SELECTED_PARAM){
            btnParam.setSelected(true);
        }

        GridBagConstraints gbc_btnParam = new GridBagConstraints();
        gbc_btnParam.fill = 2;
        gbc_btnParam.insets = new Insets(0, 0, 0, 5);
        gbc_btnParam.gridx = 13;
        gbc_btnParam.gridy = 0;
        ConfigPanel.add(btnParam, gbc_btnParam);

        //增加重复参数URL去除开关
        btnSmart = new JToggleButton("SMART");
        btnSmart.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                boolean isSelected = btnSmart.isSelected();
                boolean oldStatus = Config.REQ_SMART;
                if(isSelected){
                    Config.REQ_SMART = true;
                    btnSmart.setText("SMART");
                }else{
                    Config.REQ_SMART = false;
                    btnSmart.setText("SMART");
                }
                btnSmart.setSelected(isSelected);
                boolean newStatus = Config.REQ_SMART;
                //判断状态是否改变,改变了就输出
                if(oldStatus != newStatus){
                    Utils.showStdoutMsg(1, String.format("[*] Click Button [%s]: %s --> %s", "SMART", oldStatus, newStatus));
                }
            }
        });

        //根据配置文件设置SMART按钮的默认选择行为
        if(Config.SELECTED_SMART){
            btnSmart.setSelected(true);
        }

        GridBagConstraints gbc_btnSmart = new GridBagConstraints();
        gbc_btnSmart.fill = 2;
        gbc_btnSmart.insets = new Insets(0, 0, 0, 5);
        gbc_btnSmart.gridx = 14;
        gbc_btnSmart.gridy = 0;
        ConfigPanel.add(btnSmart, gbc_btnSmart);

        //增加去重时关注认证信息的开关
        btnAuth = new JToggleButton("AUTH");
        btnAuth.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                boolean isSelected = btnAuth.isSelected();
                boolean oldStatus = Config.REQ_AUTH;
                if(isSelected){
                    Config.REQ_AUTH = true;
                    btnAuth.setText("AUTH");
                }else{
                    Config.REQ_AUTH = false;
                    btnAuth.setText("AUTH");
                }
                btnAuth.setSelected(isSelected);
                boolean newStatus = Config.REQ_AUTH;
                //判断状态是否改变,改变了就输出
                if(oldStatus != newStatus){
                    Utils.showStdoutMsg(1, String.format("[*] Click Button [%s]: %s --> %s", "AUTH", oldStatus, newStatus));
                }
            }
        });

        //根据配置文件设置AUTH按钮的默认选择行为
        if(Config.SELECTED_AUTH){
            btnAuth.setSelected(true);
        }

        GridBagConstraints gbc_btnAuth = new GridBagConstraints();
        gbc_btnAuth.fill = 2;
        gbc_btnAuth.insets = new Insets(0, 0, 0, 5);
        gbc_btnAuth.gridx = 15;
        gbc_btnAuth.gridy = 0;
        ConfigPanel.add(btnAuth, gbc_btnAuth);
        ///////////////////////////////
        GridBagConstraints gbc_lb1 = new GridBagConstraints();
        gbc_lb1.anchor = 15;
        gbc_lb1.insets = new Insets(0, 0, 0, 5);
        gbc_lb1.gridx = 16;
        gbc_lb1.gridy = 0;
        ConfigPanel.add(new JLabel(""), gbc_lb1);
        ///////////////////////////////
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
                    Config.TARGET_HOST_REGX = tfTargetHost.getText();
                    Config.BLACK_URL_REGX = tfBlackUrl.getText();
                    Config.BLACK_SUFFIX_REGX = tfBlackSuffix.getText();
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
        gbc_btnConn.gridx = 17;
        gbc_btnConn.gridy = 0;
        ConfigPanel.add(btnConn, gbc_btnConn);

        btnClear = new JButton("Clear");
        btnClear.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the data？", "Passive Scan Client prompt", JOptionPane.YES_NO_OPTION);
                if(n == 0) {
                    Config.REQUEST_TOTAL = 0;
                    lbRequestCount.setText("0");
                    Config.SUCCESS_TOTAL = 0;
                    lbSuccessCount.setText("0");
                    Config.FAIL_TOTAL = 0;
                    lbFailCount.setText("0");
                    BurpExtender.log.clear();
                    logTable.getHttpLogTableModel().fireTableDataChanged();//通知模型更新
                    logTable.updateUI();//刷新表格
                    requestViewer.setMessage("".getBytes(),true);
                    responseViewer.setMessage("".getBytes(),false);
                    proxyRspViewer.setText("".getBytes());
                    clearHashSet(true);  //新增URL去重
                }
            }
        });
        GridBagConstraints gbc_btnClear = new GridBagConstraints();
        gbc_btnClear.fill = 2;
        gbc_btnClear.insets = new Insets(0, 0, 0, 5);
        gbc_btnClear.gridx = 19;
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
        gbl_panel_1.columnWeights = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 0.0D,0.0D,1.0D, 0.0D, 0.0D,0.0D,0.0D,0.0D,0.0D,0.0D,Double.MIN_VALUE };
        gbl_panel_1.rowWeights = new double[] { 0.0D, Double.MIN_VALUE };
        FilterPanel.setLayout(gbl_panel_1);

        JLabel lbTargetHost = new JLabel("TargetHost:");
        GridBagConstraints gbc_lbTargetHost = new GridBagConstraints();
        gbc_lbTargetHost.insets = new Insets(0, 0, 0, 5);
        gbc_lbTargetHost.anchor = 15;
        gbc_lbTargetHost.gridx = 0;
        gbc_lbTargetHost.gridy = 0;
        FilterPanel.add(lbTargetHost, gbc_lbTargetHost);

        tfTargetHost = new JTextField(30);
        tfTargetHost.setText(Config.TARGET_HOST_REGX);
        GridBagConstraints gbc_tfTargetHost = new GridBagConstraints();
        gbc_tfTargetHost.insets = new Insets(0, 0, 0, 5);
        gbc_tfTargetHost.fill = 2;
        gbc_tfTargetHost.gridx = 1;
        gbc_tfTargetHost.gridy = 0;
        FilterPanel.add(tfTargetHost, gbc_tfTargetHost);

        //新增黑名单主机控制
        JLabel lbBlackUrl = new JLabel("BlackUrl:");
        GridBagConstraints gbc_lbBlackUrl = new GridBagConstraints();
        gbc_lbBlackUrl.insets = new Insets(0, 0, 0, 5);
        gbc_lbBlackUrl.anchor = 15;
        gbc_lbBlackUrl.fill = 2;
        gbc_lbBlackUrl.gridx = 2;
        gbc_lbBlackUrl.gridy = 0;
        FilterPanel.add(lbBlackUrl, gbc_lbBlackUrl);

        tfBlackUrl = new JTextField(30);
        tfBlackUrl.setText(Config.BLACK_URL_REGX);
        GridBagConstraints gbc_tfBlackUrl = new GridBagConstraints();
        gbc_tfBlackUrl.insets = new Insets(0, 0, 0, 5);
        gbc_tfBlackUrl.fill = 2;
        gbc_tfBlackUrl.gridx = 3;
        gbc_tfBlackUrl.gridy = 0;
        FilterPanel.add(tfBlackUrl, gbc_tfBlackUrl);
        //新增黑名单主机控制

        JLabel lbBlackSuffix = new JLabel("BlackSuffix:");
        GridBagConstraints gbc_lbBlackSuffix = new GridBagConstraints();
        gbc_lbBlackSuffix.insets = new Insets(0, 0, 0, 5);
        gbc_lbBlackSuffix.anchor = 15;
        gbc_lbBlackSuffix.fill = 2;
        gbc_lbBlackSuffix.gridx = 4;
        gbc_lbBlackSuffix.gridy = 0;
        FilterPanel.add(lbBlackSuffix, gbc_lbBlackSuffix);

        tfBlackSuffix = new JTextField(30);
        tfBlackSuffix.setText(Config.BLACK_SUFFIX_REGX);
        GridBagConstraints gbc_tfBlackSuffix = new GridBagConstraints();
        gbc_tfBlackSuffix.insets = new Insets(0, 0, 0, 5);
        gbc_tfBlackSuffix.fill = 2;
        gbc_tfBlackSuffix.gridx = 5;
        gbc_tfBlackSuffix.gridy = 0;
        FilterPanel.add(tfBlackSuffix, gbc_tfBlackSuffix);

        GridBagConstraints gbc_vb = new GridBagConstraints();
        gbc_vb.insets = new Insets(0, 0, 0, 5);
        gbc_vb.fill = 2;
        gbc_vb.gridx = 6;
        gbc_vb.gridy = 0;
        FilterPanel.add(Box.createVerticalBox(), gbc_vb);

        JLabel lbRequest = new JLabel("Total:");
        GridBagConstraints gbc_lbRequest = new GridBagConstraints();
        gbc_lbRequest.insets = new Insets(0, 0, 0, 5);
        gbc_lbRequest.fill = 2;
        gbc_lbRequest.gridx = 7;
        gbc_lbRequest.gridy = 0;
        FilterPanel.add(lbRequest, gbc_lbRequest);


        lbRequestCount = new JLabel("0");
        lbRequestCount.setForeground(new Color(0,0,255));
        GridBagConstraints gbc_lbRequestCount = new GridBagConstraints();
        gbc_lbRequestCount.insets = new Insets(0, 0, 0, 5);
        gbc_lbRequestCount.fill = 2;
        gbc_lbRequestCount.gridx = 8;
        gbc_lbRequestCount.gridy = 0;
        FilterPanel.add(lbRequestCount, gbc_lbRequestCount);

        GridBagConstraints gbc_vb2 = new GridBagConstraints();
        gbc_vb2.insets = new Insets(0, 0, 0, 5);
        gbc_vb2.fill = 2;
        gbc_vb2.gridx = 9;
        gbc_vb2.gridy = 0;
        FilterPanel.add(Box.createVerticalBox(), gbc_vb);

        JLabel lbSuccess = new JLabel("Success:");
        GridBagConstraints gbc_lbSuccess = new GridBagConstraints();
        gbc_lbSuccess.insets = new Insets(0, 0, 0, 5);
        gbc_lbSuccess.fill = 2;
        gbc_lbSuccess.gridx = 10;
        gbc_lbSuccess.gridy = 0;
        FilterPanel.add(lbSuccess, gbc_lbSuccess);

        lbSuccessCount = new JLabel("0");
        lbSuccessCount.setForeground(new Color(0, 255, 0));
        GridBagConstraints gbc_lbSuccessCount = new GridBagConstraints();
        gbc_lbSuccessCount.insets = new Insets(0, 0, 0, 5);
        gbc_lbSuccessCount.fill = 2;
        gbc_lbSuccessCount.gridx = 11;
        gbc_lbSuccessCount.gridy = 0;
        FilterPanel.add(lbSuccessCount, gbc_lbSuccessCount);

        GridBagConstraints gbc_vb3 = new GridBagConstraints();
        gbc_vb3.insets = new Insets(0, 0, 0, 5);
        gbc_vb3.fill = 2;
        gbc_vb3.gridx = 12;
        gbc_vb3.gridy = 0;
        FilterPanel.add(Box.createVerticalBox(), gbc_vb3);

        JLabel lbFail = new JLabel("Fail:");
        GridBagConstraints gbc_lbFail = new GridBagConstraints();
        gbc_lbFail.insets = new Insets(0, 0, 0, 5);
        gbc_lbFail.fill = 2;
        gbc_lbFail.gridx = 13;
        gbc_lbFail.gridy = 0;
        FilterPanel.add(lbFail, gbc_lbFail);

        lbFailCount = new JLabel("0");
        lbFailCount.setForeground(new Color(255, 0, 0));
        GridBagConstraints gbc_lbFailCount = new GridBagConstraints();
        gbc_lbFailCount.insets = new Insets(0, 0, 0, 5);
        gbc_lbFailCount.fill = 2;
        gbc_lbFailCount.gridx = 14;
        gbc_lbFailCount.gridy = 0;
        FilterPanel.add(lbFailCount, gbc_lbFailCount);

        contentPane.add(topPanel,BorderLayout.NORTH);
        ////////////////////////////////////////////////////////////////////
        //topPanel end
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

    public Component getComponent(){
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
        tfTargetHost.setEnabled(is);
        tfBlackUrl.setEnabled(is);
        tfBlackSuffix.setEnabled(is);
        tfIntervalTime.setEnabled(is);
    }

    //新增URL去重
    public void clearHashSet(boolean bool){
        if(bool){
            int HashSetSizeBefore = Config.reqInfoHashSet.size();
            Config.reqInfoHashSet.clear();
            int HashSetSizeAfter = Config.reqInfoHashSet.size();
            Utils.showStdoutMsg(0, String.format("[*] Clear HashSet By Button, HashSet Size %s --> %s.",HashSetSizeBefore, HashSetSizeAfter));

            int HashMapSizeBefore = Config.reqInfoHashMap.size();
            Config.reqInfoHashMap.clear();
            int HashMapSizeAfter = Config.reqInfoHashMap.size();
            Utils.showStdoutMsg(0, String.format("[*] Clear HashSet By Button, HashMap Size %s --> %s.",HashMapSizeBefore, HashMapSizeAfter));
        }
    }
}