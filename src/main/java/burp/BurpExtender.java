package burp;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class BurpExtender implements IBurpExtender,ITab,IProxyListener, IContextMenuFactory {
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;
    public static PrintWriter stderr;
    public static GUI gui;
    public static final List<LogEntry> log = new ArrayList<LogEntry>();
    public static BurpExtender burpExtender;
    private ExecutorService executorService;

    public static String extensionName;
    public static String version;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        burpExtender = this;
        BurpExtender.callbacks = callbacks;
        helpers = callbacks.getHelpers();
        stdout = new PrintWriter(callbacks.getStdout(),true);
        stderr = new PrintWriter(callbacks.getStderr(),true);
        callbacks.registerContextMenuFactory(this);//必须注册右键菜单Factory


        //读取配置文件
        Config.EXTENSION_NAME = YamlReader.getInstance(callbacks).getString(Config.EXTENSION_NAME_STR);
        Config.VERSION = YamlReader.getInstance(callbacks).getString(Config.VERSION_STR);

        Config.PROXY_HOST = YamlReader.getInstance(callbacks).getString(Config.PROXY_HOST_STR);
        Config.PROXY_PORT = YamlReader.getInstance(callbacks).getInteger(Config.PROXY_PORT_STR);
        Config.PROXY_USERNAME = YamlReader.getInstance(callbacks).getString(Config.PROXY_USERNAME_STR);
        Config.PROXY_PASSWORD = YamlReader.getInstance(callbacks).getString(Config.PROXY_PASSWORD_STR);

        Config.TARGET_HOST_REGX = YamlReader.getInstance(callbacks).getString(Config.TARGET_HOST_REGX_STR);
        Config.BLACK_URL_REGX = YamlReader.getInstance(callbacks).getString(Config.BLACK_URL_REGX_STR);
        Config.BLACK_SUFFIX_REGX = YamlReader.getInstance(callbacks).getString(Config.BLACK_SUFFIX_REGX_STR);
        Config.AUTH_INFO_REGX = YamlReader.getInstance(callbacks).getString(Config.AUTH_INFO_REGX_STR);
        Config.DEL_STATUS_REGX = YamlReader.getInstance(callbacks).getString(Config.DEL_STATUS_REGX_STR);

        Config.PROXY_TIMEOUT = YamlReader.getInstance(callbacks).getInteger(Config.PROXY_TIMEOUT_STR);
        Config.HASH_MAP_LIMIT = YamlReader.getInstance(callbacks).getInteger(Config.HASH_MAP_LIMIT_STR);
        Config.HASH_SET_LIMIT = YamlReader.getInstance(callbacks).getInteger(Config.HASH_SET_LIMIT_STR);
        Config.INTERVAL_TIME = YamlReader.getInstance(callbacks).getInteger(Config.INTERVAL_TIME_STR);
        Config.DECODE_MAX_TIMES = YamlReader.getInstance(callbacks).getInteger(Config.DECODE_MAX_TIMES_STR);

        Config.SELECTED_HASH = YamlReader.getInstance(callbacks).getBoolean(Config.SELECTED_HASH_STR);
        Config.SELECTED_PARAM = YamlReader.getInstance(callbacks).getBoolean(Config.SELECTED_PARAM_STR);
        Config.SELECTED_SMART = YamlReader.getInstance(callbacks).getBoolean(Config.SELECTED_SMART_STR);
        Config.SELECTED_AUTH = YamlReader.getInstance(callbacks).getBoolean(Config.SELECTED_AUTH_STR);

        //Config.DEL_ERROR_KEY = YamlReader.getInstance(callbacks).getBoolean(Config.DEL_ERROR_KEY_STR);
        Config.SHOW_MSG_LEVEL = YamlReader.getInstance(callbacks).getInteger(Config.SHOW_MSG_LEVEL_STR);


        version = Config.VERSION;
        extensionName= Config.EXTENSION_NAME;
        callbacks.setExtensionName(extensionName + " " + version);
        gui = new GUI();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BurpExtender.callbacks.addSuiteTab(BurpExtender.this);
                BurpExtender.callbacks.registerProxyListener(BurpExtender.this);
                Utils.showStdoutMsg(0, Utils.getBanner());
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.EXTENSION_NAME_STR, Config.EXTENSION_NAME));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.VERSION_STR, Config.VERSION));

                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.PROXY_HOST_STR, Config.PROXY_HOST));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.PROXY_PORT_STR, Config.PROXY_PORT));

                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.PROXY_USERNAME_STR, Config.PROXY_USERNAME));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.PROXY_PASSWORD_STR, Config.PROXY_PASSWORD));

                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.PROXY_TIMEOUT_STR, Config.PROXY_TIMEOUT));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.INTERVAL_TIME_STR, Config.INTERVAL_TIME));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.HASH_MAP_LIMIT_STR, Config.HASH_MAP_LIMIT));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.HASH_SET_LIMIT_STR, Config.HASH_SET_LIMIT));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.DECODE_MAX_TIMES_STR, Config.DECODE_MAX_TIMES));

                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.SELECTED_HASH_STR, Config.SELECTED_HASH));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.SELECTED_PARAM_STR, Config.SELECTED_PARAM));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.SELECTED_SMART_STR, Config.SELECTED_SMART));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.SELECTED_AUTH_STR, Config.SELECTED_AUTH));

                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.TARGET_HOST_REGX_STR, Config.TARGET_HOST_REGX));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.BLACK_URL_REGX_STR, Config.BLACK_URL_REGX));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.BLACK_SUFFIX_REGX_STR, Config.BLACK_SUFFIX_REGX));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.AUTH_INFO_REGX_STR, Config.AUTH_INFO_REGX));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.DEL_STATUS_REGX_STR, Config.DEL_STATUS_REGX));

                //Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.DEL_ERROR_KEY_STR, Config.DEL_ERROR_KEY));
                Utils.showStdoutMsg(1, String.format("[*] INIT %s: %s", Config.SHOW_MSG_LEVEL_STR, Config.SHOW_MSG_LEVEL));
                Utils.showStdoutMsg(1, "[*] ####################################");
            }
        });


        executorService = Executors.newSingleThreadExecutor();
        //必须等插件界面显示完毕，重置JTable列宽才生效
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //按照比例显示列宽
                float[] columnWidthPercentage = {5.0f, 5.0f, 55.0f, 20.0f, 15.0f};
                int tW = GUI.logTable.getWidth();
                TableColumn column;
                TableColumnModel jTableColumnModel = GUI.logTable.getColumnModel();
                int cantCols = jTableColumnModel.getColumnCount();
                for (int i = 0; i < cantCols; i++) {
                    column = jTableColumnModel.getColumn(i);
                    int pWidth = Math.round(columnWidthPercentage[i] * tW);
                    column.setPreferredWidth(pWidth);
                }
            }
        });
    }

    //callbacks.registerContextMenuFactory(this);//必须注册右键菜单Factory
    //实现右键 感谢原作者Conanjun
    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        final IHttpRequestResponse[] messages = invocation.getSelectedMessages();
        JMenuItem i1 = new JMenuItem(String.format("Send to %s", Config.EXTENSION_NAME));
        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (final IHttpRequestResponse message : messages) {
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (log) {
                                int row = log.size();
                                String method = helpers.analyzeRequest(message).getMethod();
                                //byte[] req = message.getRequest();
                                //String req_str = new String(req);
                                //向代理转发请求
                                Map<String, String> mapResult = null;

                                String url = helpers.analyzeRequest(message.getHttpService(),message.getRequest()).getUrl().toString();
                                Utils.showStdoutMsg(0, String.format("[+] Right Click Scanning Url [%s]", url));

                                try {
                                    mapResult = HttpAndHttpsProxy.Proxy(message);
                                } catch (InterruptedException interruptedException) {
                                    interruptedException.printStackTrace();
                                }

                                log.add(new LogEntry(row + 1,
                                        callbacks.saveBuffersToTempFiles(message), helpers.analyzeRequest(message).getUrl(),
                                        method,
                                        mapResult)
                                );
                                GUI.logTable.getHttpLogTableModel().fireTableRowsInserted(row, row);
                            }
                        }
                    });
                }
            }
        });

        return Arrays.asList(i1);
    }


    //
    //实现ITab
    //

    @Override
    public String getTabCaption() {
        return extensionName;
    }

    @Override
    public Component getUiComponent() {
        return gui.getComponent();
    }

    public void processProxyMessage(boolean messageIsRequest, final IInterceptedProxyMessage iInterceptedProxyMessage) {
        if (!messageIsRequest && Config.IS_RUNNING) {
            IHttpRequestResponse rep_rsp = iInterceptedProxyMessage.getMessageInfo();
            IHttpService httpService = rep_rsp.getHttpService();
            String host = rep_rsp.getHttpService().getHost();
            String path = helpers.analyzeRequest(httpService,rep_rsp.getRequest()).getUrl().getPath();
            String url = helpers.analyzeRequest(httpService,rep_rsp.getRequest()).getUrl().toString();

            //白名单域名匹配
            if(!Utils.isMatchTargetHost(Config.TARGET_HOST_REGX, host, true)){
                return;
            }

            //黑名单URL单词匹配
            if(Utils.isMatchKeywords(Config.BLACK_URL_REGX, url, false)){
                return;
            }

            //黑名单后缀匹配
            if(Utils.isMatchBlackSuffix(Config.BLACK_SUFFIX_REGX, path, false)){
                return;
            }

            Utils.showStdoutMsg(0, String.format("[+] Passive Scanning Url [%s]", url));

            final IHttpRequestResponse req_resp = iInterceptedProxyMessage.getMessageInfo();

            //final LogEntry logEntry = new LogEntry(1,callbacks.saveBuffersToTempFiles(iInterceptedProxyMessage.getMessageInfo()),helpers.analyzeRequest(resrsp).getUrl());

            //create a new log entry with the message details
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    synchronized(log) {
                        int row = log.size();
                        String method = helpers.analyzeRequest(req_resp).getMethod();
                        Map<String, String> mapResult = null;
                        try {
                            mapResult = HttpAndHttpsProxy.Proxy(req_resp);
                        } catch (InterruptedException e) {
                            //TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        //log.add(new LogEntry(iInterceptedProxyMessage.getMessageReference(),
                        log.add(new LogEntry(row + 1,
                                callbacks.saveBuffersToTempFiles(req_resp), helpers.analyzeRequest(req_resp).getUrl(),
                                method,
                                mapResult)
                        );
                        GUI.logTable.getHttpLogTableModel().fireTableRowsInserted(row, row);
                    }
                }
            });
        }
    }
}