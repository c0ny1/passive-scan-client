package burp;

import java.util.HashMap;
import java.util.HashSet;

public class Config {

    public static HashMap<String, String> reqInfoHashMap = new HashMap();
    public static HashSet<String> reqInfoHashSet = new HashSet<>();

    public static Integer REQUEST_TOTAL = 0;
    public static Integer SUCCESS_TOTAL = 0;
    public static Integer FAIL_TOTAL = 0;

    public static boolean IS_RUNNING = false;
    public static boolean REQ_PARAM = false; //无参过滤模式
    public static boolean REQ_AUTH = false; //关注认证信息

    public static boolean REQ_HASH = false; //HASH去重模式
    public static String REQ_HASH_STR = "REQ_HASH"; //给内部代码使用的字符串
    public static boolean REQ_SMART = false; //参数去重模式
    public static String REQ_SMART_STR = "REQ_SMART"; //给内部代码使用的字符串

    public static String EXTENSION_NAME; //从配置文件获取
    public static String EXTENSION_NAME_STR = "EXTENSION_NAME";
    public static String VERSION; //从配置文件获取
    public static String VERSION_STR = "VERSION";

    public static String PROXY_HOST; //从配置文件获取
    public static String PROXY_HOST_STR = "PROXY_HOST";
    public static Integer PROXY_PORT; //从配置文件获取
    public static String PROXY_PORT_STR = "PROXY_PORT";
    public static String PROXY_USERNAME; //从配置文件获取
    public static String PROXY_USERNAME_STR = "PROXY_USERNAME";
    public static String PROXY_PASSWORD; //从配置文件获取
    public static String PROXY_PASSWORD_STR = "PROXY_PASSWORD";

    public static Integer PROXY_TIMEOUT; //从配置文件获取
    public static String PROXY_TIMEOUT_STR = "PROXY_TIMEOUT";
    public static Integer INTERVAL_TIME; //从配置文件获取
    public static String INTERVAL_TIME_STR = "INTERVAL_TIME";
    public static Integer HASH_MAP_LIMIT; //限制reqInfoHashMap中最大记录的请求数量,
    public static String HASH_MAP_LIMIT_STR = "HASH_MAP_LIMIT";
    public static Integer HASH_SET_LIMIT; //限制reqInfoHashSet中最大记录的请求数量
    public static String HASH_SET_LIMIT_STR = "HASH_SET_LIMIT";

    public static String TARGET_HOST_REGX; //从配置文件获取
    public static String TARGET_HOST_REGX_STR = "TARGET_HOST_REGX";
    public static String BLACK_URL_REGX; //从配置文件获取
    public static String BLACK_URL_REGX_STR = "BLACK_URL_REGX";
    public static String BLACK_SUFFIX_REGX; //从配置文件获取
    public static String BLACK_SUFFIX_REGX_STR = "BLACK_SUFFIX_REGX";
    public static String AUTH_INFO_REGX; //从配置文件获取,去重时应该关注认证头信息字符串
    public static String AUTH_INFO_REGX_STR = "AUTH_INFO_REGX";
    public static String DEL_STATUS_REGX; //记录需要删除记录的的响应码
    public static String DEL_STATUS_REGX_STR = "DEL_STATUS_REGX";

    public static Boolean SELECTED_HASH; //从配置文件获取,HASH去重模式 按钮的默认设置 注：按钮变量可合并到参数
    public static String SELECTED_HASH_STR = "SELECTED_HASH";
    public static Boolean SELECTED_PARAM; //从配置文件获取,过滤无参数 按钮的默认设置 注：按钮变量可合并到参数
    public static String SELECTED_PARAM_STR = "SELECTED_PARAM";
    public static Boolean SELECTED_SMART; //从配置文件获取,参数去重模式 按钮的默认设置 注：按钮变量可合并到参数
    public static String SELECTED_SMART_STR = "SELECTED_SMART";
    public static Boolean SELECTED_AUTH; //从配置文件获取,去重是否关注认证头信息 按钮的默认设置 注：按钮变量可合并到参数
    public static String SELECTED_AUTH_STR = "SELECTED_AUTH";

    public static Boolean DEL_ERROR_KEY = true;
    //public static String DEL_ERROR_KEY_STR = "DEL_ERROR_KEY";

    public static Integer SHOW_MSG_LEVEL; //从配置文件获取,显示输出信息的级别
    public static String SHOW_MSG_LEVEL_STR = "SHOW_MSG_LEVEL";

    public static Integer DECODE_MAX_TIMES; //从配置文件获取,
    public static String DECODE_MAX_TIMES_STR = "DECODE_MAX_TIMES"; //从配置文件获取

}
