package burp;

import java.awt.Component;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class BurpExtender implements IBurpExtender,ITab,IProxyListener {
    public final static String extensionName = "Passive Scan Client";
    public final static String version ="0.1";
    public static IBurpExtenderCallbacks callbacks;
    public static IExtensionHelpers helpers;
    public static PrintWriter stdout;
    public static PrintWriter stderr;
    public static GUI gui;
    public static final List<LogEntry> log = new ArrayList<LogEntry>();
    public static BurpExtender burpExtender;
    private ExecutorService executorService;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
        this.burpExtender = this;
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        this.stdout = new PrintWriter(callbacks.getStdout(),true);
        this.stderr = new PrintWriter(callbacks.getStderr(),true);

        callbacks.setExtensionName(extensionName + " " + version);
        BurpExtender.this.gui = new GUI();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BurpExtender.this.callbacks.addSuiteTab(BurpExtender.this);
                BurpExtender.this.callbacks.registerProxyListener(BurpExtender.this);
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

    public void processProxyMessage(boolean messageIsRequest, final IInterceptedProxyMessage iInterceptedProxyMessage) {
        if (!messageIsRequest && Config.IS_RUNNING) {
            IHttpRequestResponse reprsp = iInterceptedProxyMessage.getMessageInfo();
            IHttpService httpService = reprsp.getHttpService();
            String host = reprsp.getHttpService().getHost();
            //stdout.println(Config.DOMAIN_REGX);
            if(!Utils.isMathch(Config.DOMAIN_REGX,host)){
                return;
            }

            String  url = helpers.analyzeRequest(httpService,reprsp.getRequest()).getUrl().toString();
            url = url.indexOf("?") > 0 ? url.substring(0, url.indexOf("?")) : url;
            if(Utils.isMathch(Config.SUFFIX_REGX,url)){
                return;
            }

            final IHttpRequestResponse resrsp = iInterceptedProxyMessage.getMessageInfo();

            //final LogEntry logEntry = new LogEntry(1,callbacks.saveBuffersToTempFiles(iInterceptedProxyMessage.getMessageInfo()),helpers.analyzeRequest(resrsp).getUrl());

            // create a new log entry with the message details
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    synchronized(log) {
                        int row = log.size();
                        String method = helpers.analyzeRequest(resrsp).getMethod();
                        Map<String,String> mapResult = HttpAndHttpsProxy.Proxy(resrsp);

                        log.add(new LogEntry(iInterceptedProxyMessage.getMessageReference(),
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