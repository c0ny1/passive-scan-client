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

// 插件入口
public class BurpExtender implements IBurpExtender,ITab,IHttpListener {
    public final static String extensionName = "Passive Scan Client";
    public final static String version ="0.4.0";
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;
    public static PrintWriter stderr;
    public static GUI gui;
    public static final List<LogEntry> log = new ArrayList<LogEntry>();
    public static BurpExtender burpExtender;
    private ExecutorService executorService;

    // 通过参数 callbacks 可以获得核心基础库，例如日志、请求、返回值修改等
    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.burpExtender = this;
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        this.stdout = new PrintWriter(callbacks.getStdout(),true);
        this.stderr = new PrintWriter(callbacks.getStderr(),true);

        //  注册菜单拓展
        callbacks.registerContextMenuFactory(new Send2PSCMenu());
        callbacks.setExtensionName(extensionName + " " + version);
        BurpExtender.this.gui = new GUI();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BurpExtender.this.callbacks.addSuiteTab(BurpExtender.this);
                BurpExtender.this.callbacks.registerHttpListener(BurpExtender.this);
                stdout.println(Utils.getBanner());
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

    //
    // 实现ITab
    //

    @Override
    public String getTabCaption() {
        return extensionName;
    }

    @Override
    public Component getUiComponent() {
        return gui.getComponet();
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {
        // 插件开启
        if (Config.IS_RUNNING && !messageIsRequest) {
            // 开启监控 Proxy，并且该消息是 Proxy 模块的
            if(Config.PROXY && toolFlag == IBurpExtenderCallbacks.TOOL_PROXY) {
                IHttpService httpService = messageInfo.getHttpService();
                String host = messageInfo.getHttpService().getHost();
                //stdout.println(Config.DOMAIN_REGX);
                if(Config.DOMAIN_REGX.isEmpty() && !Utils.isMathch(Config.DOMAIN_REGX,host)){
                    return;
                }

                String  url = helpers.analyzeRequest(httpService,messageInfo.getRequest()).getUrl().toString();
                String url2 = url;
                url = url.indexOf("?") > 0 ? url.substring(0, url.indexOf("?")) : url;
                if(!Config.SUFFIX_REGX.isEmpty() && Utils.isMathch(Config.SUFFIX_REGX,url)){
                    return;
                }
                if(!Config.BLACKLIST_REGX.isEmpty() && Utils.isMathch(Config.BLACKLIST_REGX,url2)){
                    return;
                }

                final IHttpRequestResponse resrsp = messageInfo;

                //final LogEntry logEntry = new LogEntry(1,callbacks.saveBuffersToTempFiles(iInterceptedProxyMessage.getMessageInfo()),helpers.analyzeRequest(resrsp).getUrl());

                // create a new log entry with the message details
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        synchronized(log) {
                            int row = log.size();
                            String method = helpers.analyzeRequest(resrsp).getMethod();
                            Map<String, String> mapResult = null;
                            try {
                                mapResult = HttpAndHttpsProxy.Proxy(resrsp);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            log.add(new LogEntry(row,
                                    callbacks.saveBuffersToTempFiles(resrsp), helpers.analyzeRequest(resrsp).getUrl(),
                                    method,
                                    mapResult)
                            );
                            GUI.logTable.getHttpLogTableModel().fireTableRowsInserted(row, row);
                        }
                    }
                });
            }

            else if(Config.REPEATER && toolFlag == IBurpExtenderCallbacks.TOOL_REPEATER) {
                IHttpService httpService = messageInfo.getHttpService();
                String host = messageInfo.getHttpService().getHost();
                //stdout.println(Config.DOMAIN_REGX);
                if(Config.DOMAIN_REGX.isEmpty() && !Utils.isMathch(Config.DOMAIN_REGX,host)){
                    return;
                }

                String  url = helpers.analyzeRequest(httpService,messageInfo.getRequest()).getUrl().toString();
                String url2 = url;
                url = url.indexOf("?") > 0 ? url.substring(0, url.indexOf("?")) : url;
                if(!Config.SUFFIX_REGX.isEmpty() && Utils.isMathch(Config.SUFFIX_REGX,url)){
                    return;
                }
                if(!Config.BLACKLIST_REGX.isEmpty() && Utils.isMathch(Config.BLACKLIST_REGX,url2)){
                    return;
                }

                final IHttpRequestResponse resrsp = messageInfo;

                //final LogEntry logEntry = new LogEntry(1,callbacks.saveBuffersToTempFiles(iInterceptedProxyMessage.getMessageInfo()),helpers.analyzeRequest(resrsp).getUrl());

                // create a new log entry with the message details
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        synchronized(log) {
                            int row = log.size();
                            String method = helpers.analyzeRequest(resrsp).getMethod();
                            Map<String, String> mapResult = null;
                            try {
                                mapResult = HttpAndHttpsProxy.Proxy(resrsp);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            log.add(new LogEntry(row,
                                    callbacks.saveBuffersToTempFiles(resrsp), helpers.analyzeRequest(resrsp).getUrl(),
                                    method,
                                    mapResult)
                            );
                            GUI.logTable.getHttpLogTableModel().fireTableRowsInserted(row, row);
                        }
                    }
                });
            }

            else if(Config.INTRUDER && toolFlag == IBurpExtenderCallbacks.TOOL_INTRUDER) {
                IHttpService httpService = messageInfo.getHttpService();
                String host = messageInfo.getHttpService().getHost();
                //stdout.println(Config.DOMAIN_REGX);
                if(Config.DOMAIN_REGX.isEmpty() && !Utils.isMathch(Config.DOMAIN_REGX,host)){
                    return;
                }

                String  url = helpers.analyzeRequest(httpService,messageInfo.getRequest()).getUrl().toString();
                String url2 = url;
                url = url.indexOf("?") > 0 ? url.substring(0, url.indexOf("?")) : url;
                if(!Config.SUFFIX_REGX.isEmpty() && Utils.isMathch(Config.SUFFIX_REGX,url)){
                    return;
                }
                if(!Config.BLACKLIST_REGX.isEmpty() && Utils.isMathch(Config.BLACKLIST_REGX,url2)){
                    return;
                }

                final IHttpRequestResponse resrsp = messageInfo;

                //final LogEntry logEntry = new LogEntry(1,callbacks.saveBuffersToTempFiles(iInterceptedProxyMessage.getMessageInfo()),helpers.analyzeRequest(resrsp).getUrl());

                // create a new log entry with the message details
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        synchronized(log) {
                            int row = log.size();
                            String method = helpers.analyzeRequest(resrsp).getMethod();
                            Map<String, String> mapResult = null;
                            try {
                                mapResult = HttpAndHttpsProxy.Proxy(resrsp);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            log.add(new LogEntry(row,
                                    callbacks.saveBuffersToTempFiles(resrsp), helpers.analyzeRequest(resrsp).getUrl(),
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

    // 实现右键,需要先注册菜单拓展
    public class Send2PSCMenu implements IContextMenuFactory{
        @Override
        public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
            final IHttpRequestResponse[] messages = invocation.getSelectedMessages();
            JMenuItem i1 = new JMenuItem("Send to Passive Scan Client");
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
                                    byte[] req = message.getRequest();

                                    String req_str = new String(req);
                                    //向代理转发请求
                                    Map<String, String> mapResult = null;
                                    try {
                                        mapResult = HttpAndHttpsProxy.Proxy(message);
                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                    log.add(new LogEntry(row,
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
    }
}